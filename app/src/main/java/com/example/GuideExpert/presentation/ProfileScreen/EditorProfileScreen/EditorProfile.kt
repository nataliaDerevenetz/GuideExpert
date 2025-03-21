package com.example.GuideExpert.presentation.ProfileScreen.EditorProfileScreen

import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.GuideExpert.presentation.ProfileScreen.ProfileMainScreen.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorProfileScreen(onNavigateToYandex: () -> Unit,
                        viewModel: ProfileViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon={ IconButton({
                    Log.d("CLICK","BACK")
                //    scopeState.navigateToBack()
                    }
                ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back") } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.height(56.dp).shadow(6.dp),
                windowInsets = WindowInsets(0)
            )
        }
    ) { innerPadding -> EditorProfileContent(innerPadding)

    }

}


@Composable
fun EditorProfileContent(innerPadding: PaddingValues, onNavigateToYandex: () -> Unit ={},
                         viewModel: ProfileViewModel = hiltViewModel()
) {


    val profile by viewModel.profileFlow.collectAsStateWithLifecycle()

/*
    if (profile?.birthday == null) {
        Log.d("TAG", "birthday null")
    } else {
        Log.d("TAG", profile?.birthday.toString())
    }
   // val birthday = profile?.birthday?.let { SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(it) }
*/

    var firstName by rememberSaveable{mutableStateOf(profile?.firstName)}
    var lastName by rememberSaveable{mutableStateOf(profile?.lastName)}
    var email by rememberSaveable{mutableStateOf(profile?.email)}
    val scrollState = rememberScrollState()
    var isErrorEmail by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

        Column(
            modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp ).fillMaxSize().verticalScroll(scrollState),
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
            Row {
                Column {
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

                    DatePickerFieldToModal(profile?.birthday)

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
fun DatePickerFieldToModal(birthday: Date?,modifier: Modifier = Modifier) {
    var selectedDate by rememberSaveable  { mutableStateOf(birthday?.time) }
    var isErrorBirthday by rememberSaveable { mutableStateOf(false) }
    var showModal by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = { },
        placeholder = { Text("DD/MM/YYYY") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        textStyle = TextStyle(fontSize = 16.sp),
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            },
        isError = isErrorBirthday,
    )
    if (isErrorBirthday) {
        Text(
            text = "Error Birthday",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

    if (showModal) {
        DatePickerModal(
            onDateSelected = { selectedDate = it
                showModal = false
                isErrorBirthday = !selectedDate.isValidBirthday()
                             },
            onDismiss = { showModal = false }
        )
    }
}


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Long?.isValidBirthday() =  this !== null && this < Date().time