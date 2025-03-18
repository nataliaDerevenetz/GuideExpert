package com.example.GuideExpert.presentation.ProfileScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.remote.services.ExcursionService
import com.example.GuideExpert.presentation.UserViewModel
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import javax.inject.Inject

@AndroidEntryPoint
class ProfileYandexActivity : ComponentActivity() {
  // @Inject
  // lateinit var sessionManager: SessionManager
   // @Inject
   // lateinit var excursionService: ExcursionService
    val viewmodel: ProfileYandexViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sdk = YandexAuthSdk.create(YandexAuthOptions(applicationContext))
        val launcher =
            registerForActivityResult(sdk.contract) { result -> handleResult(result) }
        val loginOptions = YandexAuthLoginOptions()
        launcher.launch(loginOptions)
        lifecycleScope.launch {
            viewmodel.isClosed.collectLatest {
                if (it) {
                    Log.d("YANDEX", "FINISH")
                    Log.d("ACTIVITY", "CLOSED")
                    finish()
                }
            }
        }

        setContent {
            Box (contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator()
            }
        }

    }

    private  fun handleResult(result: YandexAuthResult) {
        when (result) {
            is YandexAuthResult.Success -> {
                Log.d("YANDEX",result.token.value)
                viewmodel.loginYandex(result.token.value)
            }
            is YandexAuthResult.Failure -> { Log.d("YANDEX","Failure")

                finish()}//onProccessError(result.exception)
            YandexAuthResult.Cancelled -> {

                Log.d("YANDEX","Cancelled")
                finish()
            }//onCancelled()
        }
    }
}