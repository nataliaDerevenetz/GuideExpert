package com.example.GuideExpert

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.GuideExpert.presentation.AppState
import com.example.GuideExpert.presentation.MainScreen
import com.example.GuideExpert.presentation.MainViewModel
import com.example.GuideExpert.presentation.rememberAppState
import com.example.core.design.theme.GuideExpertTheme
import com.example.notifications.Notifier
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var appState: AppState

    @Inject
    lateinit var notifier: Notifier

    private val mainViewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        retrieveFCMToken()

        val notification = notifier.createNotificationFromBundle(intent?.extras)

        enableEdgeToEdge()
        setContent {
            appState = rememberAppState(notification=notification)
            GuideExpertTheme(isLightStatusBar = appState.isLightStatusBar.value) {
                MainScreen(appState = appState, viewModel = mainViewModel)
            }
        }

        val data = intent.data
        intent.data = null
        handleDeepLink(data)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // notification coming when app in inactive/background, data included in intent extra
        val notification = notifier.createNotificationFromBundle(intent.extras)
        notification?.let {
            appState.navigateToScreenFromNotification(notification)
        }
        handleDeepLink(intent.data)

    }

    private var deepLinkData: Uri? = null

    private fun handleDeepLink(uri: Uri?) {
        if (uri != null  && appState.navController.graph.hasDeepLink(uri)) {
            //possible deep link for navigation
            deepLinkData = uri
            appState.navController.navigate(uri)
        }
    }


    private fun retrieveFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("push notification device token", "failed with error: ${task.exception}")
                return@OnCompleteListener
            }
            val token = task.result
              lifecycleScope.launch {
                  mainViewModel.registerTokenOnServer(token)
              }
        })
    }

    private fun requestNotificationPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if(!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }
}

