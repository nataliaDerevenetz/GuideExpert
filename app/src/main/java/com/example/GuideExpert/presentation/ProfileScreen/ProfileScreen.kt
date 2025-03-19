package com.example.GuideExpert.presentation.ProfileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.GuideExpert.presentation.ProfileScreen.ProfileMainScreen.ProfileViewModel


@Composable
fun ProfileScreen(
    snackbarHostState: SnackbarHostState,
    onChangeVisibleBottomBar: (Boolean) -> Unit,
    onNavigateToHome:() ->Unit
) {
    NavigationProfileScreen(snackbarHostState,onChangeVisibleBottomBar = onChangeVisibleBottomBar,
        onNavigateToHome = onNavigateToHome)
}



@Composable
fun Screen2View( onNavigateToYandex: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()) {

  //  onNavigateToYandex()
    Column {
        Text("Screen2")
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
