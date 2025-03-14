package com.example.GuideExpert.presentation.ProfileScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.remote.services.ExcursionService
import com.example.GuideExpert.presentation.UserViewModel
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileYandexActivity : ComponentActivity() {
    @Inject
    lateinit var sessionManager: SessionManager
    @Inject
    lateinit var excursionService: ExcursionService
    val viewmodel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sdk = YandexAuthSdk.create(YandexAuthOptions(applicationContext))
        val launcher = registerForActivityResult(sdk.contract) { result -> handleResult(result) }
        val loginOptions = YandexAuthLoginOptions()
        launcher.launch(loginOptions)
    }

    private fun handleResult(result: YandexAuthResult) {
        when (result) {
            is YandexAuthResult.Success -> {
                Log.d("YANDEX",result.token.value)
                lifecycleScope.launch {
                    viewmodel.sendEffectFlow("OK", null)
                }
                viewmodel.loginYandex(result.token.value)
                Log.d("YANDEXhash",viewmodel.hashCode().toString())
                finish()
            }
            is YandexAuthResult.Failure -> { Log.d("YANDEX","Failure")
                lifecycleScope.launch {
                    viewmodel.sendEffectFlow(result.exception.message.toString(), null)
                }
                finish()}//onProccessError(result.exception)
            YandexAuthResult.Cancelled -> {
                lifecycleScope.launch {
                    viewmodel.sendEffectFlow("Cancelled", null)
                }
                Log.d("YANDEX","Cancelled")
                finish()
            }//onCancelled()
        }
    }
    companion object {
        fun newIntent(context: Context) =
            context.startActivity(Intent(context, ProfileYandexActivity::class.java))

    }
}