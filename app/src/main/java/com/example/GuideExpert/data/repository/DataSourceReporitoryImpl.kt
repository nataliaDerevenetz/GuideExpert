package com.example.GuideExpert.data.repository

import android.util.Log
import androidx.paging.RemoteMediator.MediatorResult
import com.example.GuideExpert.data.local.DBStorage
import com.example.GuideExpert.data.mappers.toConfig
import com.example.GuideExpert.data.mappers.toExcursionData
import com.example.GuideExpert.data.remote.services.ExcursionService
import com.example.GuideExpert.domain.models.Config
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.domain.repository.DataSourceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.io.IOException
import javax.inject.Inject

@Serializable
class UserInfo(
    val userId: String = "32134",
    val name: String ="Tom",
    val age: Int = 10,
    val sex:String = "men"
)

sealed class UIResources<out T> {
    data class Success<out T>(val data: T) : UIResources<T>()
    data class Error(val message: String) : UIResources<Nothing>()
    data object Loading : UIResources<Nothing>()
}

class DataSourceRepositoryImpl @Inject constructor(
    private val dbStorage : DBStorage,
    private val excursionService: ExcursionService,
): DataSourceRepository {

    override fun getUserInfo(userId:String): Flow<UserInfo> {
        return flow{
            withContext(Dispatchers.IO) {
                delay(5000)
            }
            emit(UserInfo(userId,"Trump",88,"men"))
        }
    }



    override suspend fun getExcursionInfo(excursionId:Int): Flow<UIResources<ExcursionData>> = flow {
       try {
           emit(UIResources.Loading)
           val result = excursionService.getExcursionData(excursionId)
           val data = result.body()?.toExcursionData() ?: ExcursionData()
           Log.d("TAG", "DATA:::  ${data.title}")
           emit(UIResources.Success(data))
           dbStorage.insertExcursionInfo(data, data.images)
       } catch (e: IOException) {
           emit(UIResources.Error(e.message.toString()))
       }
    }

    override suspend fun getConfigInfo(): Flow<UIResources<Config>> = flow<UIResources<Config>> {
        emit(UIResources.Loading)
        val result = excursionService.getConfig()
        if (result.isSuccessful) {
            val config = result.body()?.toConfig() ?: Config()
            emit(UIResources.Success(config))
        }
    }.catch { e->
        emit(UIResources.Error(e.localizedMessage ?: "Unknown error"))
    }

    override fun getExcursionData(excursionId:Int): Flow<ExcursionData?> {
        return dbStorage.getExcursionData(excursionId).flowOn(Dispatchers.IO)
    }

    override fun getImagesExcursion(excursionId:Int): Flow<List<Image>> {
        return dbStorage.getImagesExcursion(excursionId).flowOn(Dispatchers.IO)
    }
}