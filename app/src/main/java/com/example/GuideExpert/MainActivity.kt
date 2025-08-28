package com.example.GuideExpert

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
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
            //  Toast.makeText(baseContext, "msg", Toast.LENGTH_SHORT).show()
        })

        val time: String? = intent?.extras?.getString("time")
      /*  intent?.extras?.let {
            val notification = notifier.createNotificationFromBundle(it)
            Log.d("NOTIFICATION",notification.type.toString() )
        }*/


        Log.d("intent ", "oncreate : ${intent.data}")

        val notification = with(intent?.extras){
            this?.let {
                val notification = notifier.createNotificationFromBundle(this)
                notification
            }
        }

        notification?.let {
            Log.d("NOTIFICATION",it.type.toString() )
        }
        if (notification == null) {
            Log.d("NOTIFICATION","NULL" )
        }

        Log.d("PUSH", "on create time : $time")
        enableEdgeToEdge()
        setContent {
            appState = rememberAppState(time = time)
            GuideExpertTheme(isLightStatusBar = appState.isLightStatusBar.value) {
                MainScreen(appState)
            }
        }

        val data = intent.data
        intent.data = null
        handleDeepLink(data)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
     //   appState.navController.handleDeepLink(intent)
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
       // appState.navController.handleDeepLink(intent)
      //  setIntent(intent)
        Log.d("push notification ", "on onnewintent : ${intent?.extras}")

        Log.d("intent ", " on new intent extras? : ${intent.data}")

        // notification coming when app in inactive/background, data included in intent extra
        val time: String? = intent?.extras?.getString("time")
        time?.let {
           appState.navigateToTest()
            Log.d("push notification ", " on new intent count : $time")
        }

        val data = intent?.data
        handleDeepLink(data)

    }

    private var deepLinkData: Uri? = null

    private fun handleDeepLink(uri: Uri?) {
        //TODO: there is an issue that will cause onNewIntent to be called twice when the activity is already present.
        Log.d("DEEPLINK","handleDeepLink")
     //   Log.d("DEEPLINK",uri.toString())
        if (uri != null  && appState.navController.graph.hasDeepLink(uri)) {
            Log.d("DEEPLINK","4444")
            //possible deep link for LoginFragment
            deepLinkData = uri
            appState.navController.navigate(uri)
        }
    }


}

