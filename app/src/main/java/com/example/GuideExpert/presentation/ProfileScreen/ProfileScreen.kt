package com.example.GuideExpert.presentation.ProfileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileScreen(count :Int, onIcr :()->Unit) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen1) {
        val onNavigateToScreen2 = { navController.navigateToScreen2("Ok it Screen2")}
        profileDestination(onNavigateToScreen2,count,onIcr)
    }

}


@Composable
fun Screen1View(onNavigateToScreen2: () -> Unit) {
    Column {
        Text("Screen1")
        Button(onClick = { onNavigateToScreen2() }) {
            Text("To Screen2")
        }
    }
}

@Composable
fun Screen2View(count :Int, onIcr :()->Unit) {
    Column {
        Text("Screen2")
        LoadData(count,onIcr)
    }
}

@Composable
fun LoadData(count :Int, onIcr :()->Unit) {
    Column {
        //   Text("uuu :: ${vm.nameScreen2}")
        Text("Incr :: $count")
        Button(onClick = {onIcr()}) {
            Text(text = "Increase", fontSize = 25.sp)
        }
    }
}
