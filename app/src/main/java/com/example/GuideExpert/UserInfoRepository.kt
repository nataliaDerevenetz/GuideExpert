package com.example.GuideExpert

import android.util.Log
import com.example.GuideExpert.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

@Serializable
class UserInfo(
    val userId: String = "32134",
    val name: String ="Tom",
    val age: Int = 10,
    val sex:String = "men"
)

@Serializable
class ExcursionData(
    val excursionId: String = "8886",
    val title: String ="Супер экскурсия!!",
    val company: String = "Лучшая компания",
    val photo: Int = R.drawable.excurs1

)


class UserInfoRepository {
     fun getUserInfo(userId:String): Flow<UserInfo> {
        return flow{
            withContext(Dispatchers.IO) {
                delay(5000)
            }
            emit(UserInfo(userId,"Trump",88,"men"))
        }
    }


    fun getExcursionInfo(excursionId:Int): Flow<ExcursionData> {
        Log.d("TAG","excursionId::   ${excursionId.toString()}")
        return flow{
         /*   withContext(Dispatchers.IO) {
                delay(5000)
            }

          */
            emit(ExcursionData(excursionId = excursionId.toString()))
        }
    }
}