package com.example.GuideExpert.presentation.ProfileScreen.EditorProfileScreen

import android.Manifest
import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.GuideExpert.R
import com.example.GuideExpert.domain.models.Profile
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import okhttp3.internal.UTC
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale


fun convertLocalDateToTimestampUTC(localDate: LocalDate): Long {
    val zonedDateTime = localDate.atStartOfDay(UTC.toZoneId())
    val instant = zonedDateTime.toInstant()
    return instant.toEpochMilli()
}

@OptIn(ExperimentalMaterial3Api::class)
object PastOrPresentSelectableDates: SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
       // val aa = LocalDate.parse("2025-01-11")
       // if (utcTimeMillis == convertLocalDateToTimestampUTC(aa) ) { return false}
        return utcTimeMillis <= System.currentTimeMillis()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year <= LocalDate.now().year
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorProfileScreen(onNavigateToProfile: () -> Boolean) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon={ IconButton({ onNavigateToProfile() }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back") } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.height(56.dp).shadow(6.dp),
                windowInsets = WindowInsets(0)
            )
        }
    ) {
        innerPadding -> EditorProfileContent(innerPadding)
    }
}


@Stable
interface EditorProfileStateScope {
    val profile: StateFlow<Profile?>
    val viewStateFlow: StateFlow<EditorViewState>
    val onReceive: (Intent) -> Job
}


fun DefaultEditorProfileStateScope(
    profile: StateFlow<Profile?>,
    viewStateFlow: StateFlow<EditorViewState>,
    onReceive : (Intent) -> Job
): EditorProfileStateScope {
    return object : EditorProfileStateScope {
        override val profile: StateFlow<Profile?>
            get() = profile
        override val viewStateFlow: StateFlow<EditorViewState>
            get() = viewStateFlow
        override val onReceive: (Intent) -> Job
            get() = onReceive

    }
}

@Composable
fun rememberDefaultEditorProfileStateScope(
    profile: StateFlow<Profile?>,
    viewStateFlow: StateFlow<EditorViewState>,
    onReceive: (Intent) -> Job
): EditorProfileStateScope = remember(profile,viewStateFlow,onReceive) {
    DefaultEditorProfileStateScope(profile,viewStateFlow,onReceive)
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun EditorProfileContent(innerPadding: PaddingValues,
                         viewModel: EditorProfileViewModel = hiltViewModel(),
                         scopeState: EditorProfileStateScope = rememberDefaultEditorProfileStateScope(profile = viewModel.profileFlow,
                             viewStateFlow = viewModel.viewStateFlow,
                             onReceive = viewModel::onReceive),
) {

    val profile by viewModel.profileFlow.collectAsStateWithLifecycle()

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
            Row {
                Column {

                    scopeState.LoadAvatar()

                    Row(Modifier.padding(10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(profile?.firstName ?: "",fontWeight= FontWeight.Bold)
                        Spacer(Modifier.size(5.dp))
                        Text(profile?.lastName ?: "",fontWeight= FontWeight.Bold)
                    }

                    Text(stringResource(id = R.string.first_name), color = Color.Gray,modifier = Modifier.padding(top = 10.dp))
                    OutlinedTextField(
                        firstName ?: "",
                        { firstName = it },
                        textStyle = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.height(50.dp).fillMaxWidth(),
                        singleLine = true,
                    )
                    Text(stringResource(id = R.string.last_name), color = Color.Gray, modifier = Modifier.padding(top = 10.dp))
                    OutlinedTextField(
                        lastName ?: "",
                        { lastName = it },
                        textStyle = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.height(50.dp).fillMaxWidth(),
                        singleLine = true,
                    )
                    Text(
                        stringResource(id = R.string.birthday),
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 10.dp)
                    )

                    scopeState.DatePickerFieldToModal()

                    Text(stringResource(id = R.string.email), color = Color.Gray, modifier = Modifier.padding(top = 10.dp))
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
                            text = stringResource(id = R.string.error_email),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Text(stringResource(id = R.string.phone), color = Color.Gray, modifier = Modifier.padding(top = 10.dp))
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
                 Text(stringResource(id = R.string.save))
             }

        }
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun EditorProfileStateScope.LoadAvatar() {
     val viewState: EditorViewState by viewStateFlow.collectAsState()
    val profile by profile.collectAsStateWithLifecycle()


    val currentContext = LocalContext.current

    // launches photo picker
    val pickImageFromAlbumLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { url ->
        url?.let { Intent.OnFinishPickingImagesWith(currentContext, it) }
            ?.let { onReceive(it) }
    }

    // launches camera
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isImageSaved ->
        if (isImageSaved) {
            onReceive(Intent.OnImageSavedWith(currentContext))
        } else {
            // handle image saving error or cancellation
            onReceive(Intent.OnImageSavingCanceled)
        }
    }

    // launches camera permissions
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
        if (permissionGranted) {
            onReceive(Intent.OnPermissionGrantedWith(currentContext))
        } else {
            // handle permission denied such as:
            onReceive(Intent.OnPermissionDenied)
        }
    }

    LaunchedEffect(key1 = viewState.tempFileUrl) {
        viewState.tempFileUrl?.let {
            cameraLauncher.launch(it)
        }
    }

    Button(onClick = {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }) {
        Text(text = "Take a photo")
    }
    Spacer(modifier = Modifier.width(16.dp))
    Button(onClick = {
        val mediaRequest = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        pickImageFromAlbumLauncher.launch(mediaRequest)
    }) {
        Text(text = "Pick a picture")
    }

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        viewState.selectedPictures?.let {
            Image(
                bitmap = it,
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clip(CircleShape ).clickable {  },
            )
        }
        if (viewState.selectedPictures == null) {
            SubcomposeAsyncImage(
                model = profile?.avatar?.url,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clip(CircleShape ).clickable {  },
                loading = {
                    CircularProgressIndicator(
                        color = Color.Gray,
                        modifier = Modifier.requiredSize(48.dp)
                    )
                },
                error = {
                    Log.d("TAG", "image load: Error!")
                    Log.d("TAG", "something went wrong ${it.result.throwable.localizedMessage}")
                }
            )
        }

    }

}

@Composable
fun EditorProfileStateScope.DatePickerFieldToModal(modifier: Modifier = Modifier) {
    val profile by profile.collectAsStateWithLifecycle()

    var selectedDate by rememberSaveable  { mutableStateOf(profile?.birthday?.time) }
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
            text = stringResource(id = R.string.error_birthday),
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
    val datePickerState  = rememberDatePickerState(
        selectableDates = PastOrPresentSelectableDates
    )

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
                Text(stringResource(id = R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Long?.isValidBirthday() =  this !== null && this < Date().time