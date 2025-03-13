package com.example.GuideExpert.presentation.ProfileScreen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.GuideExpert.R
import com.example.GuideExpert.presentation.UserViewModel

@Composable
fun ProfileScreen(count :Int, onIcr :()->Unit) {

    NavigationProfileScreen(count,onIcr)
    /*
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen1) {
        val onNavigateToScreen2 = { navController.navigateToScreen2("Ok it Screen2")}
        profileDestination(onNavigateToScreen2,count,onIcr)
    }*/

}


@Composable
fun Screen1View(onNavigateToScreen2: () -> Unit,
                viewModel: UserViewModel = hiltViewModel()) {

    var token by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current


    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally ){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.button_yandex),
            contentDescription = "Yandex",
            modifier = Modifier.clickable { ProfileActivity.newIntent(context) }
        )
        Text("Screen1")
        Button(onClick = { onNavigateToScreen2() }) {
            Text("To Screen2")
        }

        Button(onClick = { token = viewModel.getToken() ?: "" }) {
            Text("AUTH : $token")
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
