package com.example.GuideExpert.presentation.ProfileScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.GuideExpert.R
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.remote.services.ExcursionService
import com.example.GuideExpert.presentation.UserViewModel
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : ComponentActivity() {
    @Inject
    lateinit var sessionManager: SessionManager
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
               // sessionManager.saveAuthToken(result.token.value)
              //  excursionService.loginYandex(result.token.value)
                viewmodel.loginYandex(result.token.value)
                finish()
            }//onSuccessAuth(result.token)
            is YandexAuthResult.Failure -> { Log.d("YANDEX","Failure")
                finish()}//onProccessError(result.exception)
            YandexAuthResult.Cancelled -> { Log.d("YANDEX","Cancelled")
                finish()}//onCancelled()
        }
    }
    companion object {
        fun newIntent(context: Context) =
            context.startActivity(Intent(context, ProfileActivity::class.java))

    }
}