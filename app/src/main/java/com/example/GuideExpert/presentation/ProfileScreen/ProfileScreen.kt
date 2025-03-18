package com.example.GuideExpert.presentation.ProfileScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.GuideExpert.R
import com.example.GuideExpert.data.repository.ProfileResources
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.SnackbarEffect

@Composable
fun ProfileScreen(
    snackbarHostState: SnackbarHostState,
    onChangeVisibleBottomBar: (Boolean) -> Unit
) {
    NavigationProfileScreen(snackbarHostState,onChangeVisibleBottomBar =onChangeVisibleBottomBar)
}


@Composable
fun Screen1View(snackbarHostState: SnackbarHostState,
                onNavigateToYandex: () -> Unit,
                onNavigateToScreen2: () -> Unit,
                viewModel: ProfileViewModel = hiltViewModel()
) {

    Log.d("MODEL", "000")
    var token by rememberSaveable { mutableStateOf("") }

   // var profileId = viewModel.profileId.collectAsStateWithLifecycle()
   // var authToken = viewModel.authToken.collectAsStateWithLifecycle()

    val profile = viewModel.profileFlow.collectAsStateWithLifecycle()

    val profileState = viewModel.profileStateFlow.collectAsStateWithLifecycle()

    when(profileState.value) {
        is ProfileResources.Error -> {
            Log.d("PROFILESTATE","Error")
        }
       // is ProfileResources.Idle -> {  Log.d("PROFILESTATE","IDLE")}
        is ProfileResources.Loading -> {  Log.d("PROFILESTATE","LOADING")}
        is ProfileResources.Success -> {  Log.d("PROFILESTATE","Success")
            //(profileState.value as ProfileResources.Success).data?.let { Log.d("PROFILESTATE", it.login) }
        }
    }


   // Log.d("TAG","profileId :: " + profileId.value.toString())
   // Log.d("TAG","authToken :: " + authToken.value.toString())


    val effectFlow by viewModel.effectFlow.collectAsStateWithLifecycle(null)
    LaunchedEffect(effectFlow) {
        Log.d("MODEL", "888")
        effectFlow?.let {
            when (it) {
                is SnackbarEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally ){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.button_yandex),
            contentDescription = "Yandex",
            modifier = Modifier.clickable {
                onNavigateToYandex()
                //ProfileActivity.newIntent(context)
            }
        )
        Text("Screen1")
        Button(onClick = { onNavigateToScreen2() }) {
            Text(profile.value?.realName ?: "")
        }

        Text("Incr :: ${viewModel.count}")
        Button(onClick = {viewModel.increase()}) {
            Text(text = "Increase", fontSize = 25.sp)
        }

      /* Button(onClick = { token = viewModel.getToken() ?: "" }) {
            Text("AUTH : $token")
        }*/
    }
}

@Composable
fun Screen2View( onNavigateToYandex: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()) {

  //  onNavigateToYandex()
    Column {
        Text("Screen2")
        Text("Incr :: ${viewModel.count}")
        Button(onClick = {viewModel.increase()}) {
                Text(text = "Increase", fontSize = 25.sp)
        }
        LoadData()
    }
}

@Composable
fun LoadData() {
    Column {
        //   Text("uuu :: ${vm.nameScreen2}")
       // Text("Incr :: $count")
       // Button(onClick = {onIcr()}) {
       //     Text(text = "Increase", fontSize = 25.sp)
       // }
    }
}
