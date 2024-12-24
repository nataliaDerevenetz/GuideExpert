package com.example.GuideExpert.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.GuideExpert.data.ExcursionSearchRemoteMediator
import com.example.GuideExpert.data.local.DBStorage
import com.example.GuideExpert.data.local.db.ExcursionsRoomDatabase
import com.example.GuideExpert.data.mappers.toExcursion
import com.example.GuideExpert.data.remote.services.ExcursionService
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.FilterQuery
import com.example.GuideExpert.domain.repository.DataSourceRepository
import com.example.GuideExpert.utils.Constant.PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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
    private val excursionService: ExcursionService,
    private val excursionsRoomDatabase: ExcursionsRoomDatabase,
): DataSourceRepository {

    override fun getAllExcursionFlow(): Flow<UIResources<List<Excursion>>> = flow {
        emit(UIResources.Loading)
        dbStorage.getAllExcursionFlow().collect { excursions ->
            emit(UIResources.Success(excursions))
        }
    }.catch { e ->
        emit(UIResources.Error(e.localizedMessage ?: "Unknown error occurred"))
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getGetExcursionByQueryFlow(filterQuery:FilterQuery): Flow<PagingData<Excursion>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, maxSize = 30),
            remoteMediator = ExcursionSearchRemoteMediator(
                excursionsRoomDatabase = excursionsRoomDatabase,
                excursionService = excursionService,
                filterQuery = filterQuery
            ),
            pagingSourceFactory = {
                excursionsRoomDatabase.excursionDao().pagingSource()
            },
        ).flow.map { pagingData -> pagingData.map { it.toExcursion() } }
            .flowOn(Dispatchers.IO)

    }


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