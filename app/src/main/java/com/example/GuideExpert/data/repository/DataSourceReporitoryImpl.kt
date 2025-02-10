package com.example.GuideExpert.data.repository

import com.example.GuideExpert.data.local.DBStorage
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.repository.DataSourceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
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
): DataSourceRepository {

    override fun getUserInfo(userId:String): Flow<UserInfo> {
        return flow{
            withContext(Dispatchers.IO) {
                delay(5000)
            }
            emit(UserInfo(userId,"Trump",88,"men"))
        }
    }


    override fun getExcursionInfo(excursionId:Int): Flow<ExcursionData> {
        return dbStorage.getExcursionInfo(excursionId)

    }
}