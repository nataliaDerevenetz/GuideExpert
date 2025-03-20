package com.example.GuideExpert.presentation.ProfileScreen.EditorProfileScreen

import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.GuideExpert.presentation.ProfileScreen.ProfileMainScreen.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun EditorProfileScreen(onNavigateToYandex: () -> Unit,
                        viewModel: ProfileViewModel = hiltViewModel()
) {


    val profile by viewModel.profileFlow.collectAsStateWithLifecycle()


    if (profile?.birthday == null) {
        Log.d("TAG", "birthday null")
    } else {
        Log.d("TAG", profile?.birthday.toString())
    }
    val birthday = profile?.birthday?.let { SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(it) }


    var firstName by remember{mutableStateOf(profile?.firstName)}
    var lastName by remember{mutableStateOf(profile?.lastName)}
    var email by remember{mutableStateOf(profile?.email)}
    val scrollState = rememberScrollState()
    var isErrorEmail by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.padding(start = 15.dp,end = 15.dp).fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            /* Image(
            imageVector = ImageVector.vectorResource(R.drawable.button_yandex),
            contentDescription = "Yandex",
            modifier = Modifier.clickable {
                onNavigateToYandex()
                //ProfileActivity.newIntent(context)
            }
        )*/
            Row() {
                Column() {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(profile?.firstName ?: "",fontWeight= FontWeight.Bold)
                        Spacer(Modifier.size(5.dp))
                        Text(profile?.lastName ?: "",fontWeight= FontWeight.Bold)
                    }

                    Text("Имя", color = Color.Gray,modifier = Modifier.padding(top = 10.dp))
                    OutlinedTextField(
                        firstName ?: "",
                        { firstName = it },
                        textStyle = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.height(50.dp).fillMaxWidth(),
                        singleLine = true,
                    )
                    Text("Фамилия", color = Color.Gray, modifier = Modifier.padding(top = 10.dp))
                    OutlinedTextField(
                        lastName ?: "",
                        { lastName = it },
                        textStyle = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.height(50.dp).fillMaxWidth(),
                        singleLine = true,
                    )
                    Text(
                        "Дата рождения",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    OutlinedTextField(
                        birthday.toString(),
                        {},
                        enabled = false,
                        textStyle = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.height(50.dp).fillMaxWidth()
                            .clickable { Log.d("TAG", "BIRTHDAY") },
                        colors = OutlinedTextFieldDefaults.colors().copy(
                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        singleLine = true,
                    )

                    Text("Почта", color = Color.Gray, modifier = Modifier.padding(top = 10.dp))
                    OutlinedTextField(
                        email ?: "",
                        { email = it },
                        textStyle = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.height(50.dp).fillMaxWidth(),
                        isError = isErrorEmail,
                        trailingIcon = {
                            if (isErrorEmail)
                                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
                        },
                        singleLine = true,
                    )
                    if (isErrorEmail) {
                        Text(
                            text = "Error email",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Text("Телефон", color = Color.Gray, modifier = Modifier.padding(top = 10.dp))
                    OutlinedTextField(
                        profile?.phone ?: "",
                        {},
                        enabled = false,
                        textStyle = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.height(50.dp).fillMaxWidth(),
                        singleLine = true,
                    )
                }

            }
            Button(onClick = {
               isErrorEmail = !email.isValidEmail()
            },modifier = Modifier.padding(top = 10.dp).height(50.dp).fillMaxWidth()) {
                 Text("Сохранить")
             }

        }
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
fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()