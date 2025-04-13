package com.example.GuideExpert

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen.setStatusBarColor
import com.example.GuideExpert.presentation.MainScreen
import com.example.GuideExpert.ui.theme.GuideExpertTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuideExpertTheme {
                val context = LocalContext.current
                val isLight = rememberSaveable{mutableStateOf(true)}

           //     val onChangeStatusBarTheme = {isLightTheme:Boolean -> isLight.value = isLightTheme}
                MainScreen(isLight)

                LaunchedEffect(isLight.value) {
                    Log.d("UUU", "Light ::" + isLight.value.toString())
                    setStatusBarColor((context as Activity).window,isLight)
                }

             /*   DisposableEffect(isLight.value) {onDispose {
                    setStatusBarColor((context as Activity).window,isLight)
                    }
                }*/

            }
        }
    }
}
