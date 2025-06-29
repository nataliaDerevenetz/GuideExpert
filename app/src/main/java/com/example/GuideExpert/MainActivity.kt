package com.example.GuideExpert

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.GuideExpert.presentation.AppState
import com.example.GuideExpert.presentation.MainScreen
import com.example.GuideExpert.presentation.rememberAppState
import com.example.core.design.theme.GuideExpertTheme
import com.example.notifications.Notifier
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var appState: AppState

    @Inject
    lateinit var notifier: Notifier

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
         //   val msg = getString("aaa", token)
            Log.d("TOKEN", token)
            Toast.makeText(baseContext, "msg", Toast.LENGTH_SHORT).show()
        })

        val time: String? = intent?.extras?.getString("time")
        intent?.extras?.let {
            //notifier.createNotificationFromBundle(it)
        }

      //  intent.extras?.clear()
        Log.d("PUSH", "on create time : $time")
        enableEdgeToEdge()
        setContent {
            appState = rememberAppState(time = time)
            GuideExpertTheme(isLightStatusBar = appState.isLightStatusBar.value) {
                MainScreen(appState)
            }

            OnLifecycleEvent { owner, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        Log.d("YYY","RESUME")
                  //     appState.navigateToTest()
                    }
                    else -> { }
                }
            }
        }
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
      ///  navController.handleDeepLink(intent)
        Log.d("push notification ", " on new intent extras? : ${intent?.extras}")

        // notification coming when app in inactive/background, data included in intent extra
        val time: String? = intent?.extras?.getString("time")
        time?.let {
            appState.navigateToTest()
            Log.d("push notification ", " on new intent count : $time")
        }

        }


}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

