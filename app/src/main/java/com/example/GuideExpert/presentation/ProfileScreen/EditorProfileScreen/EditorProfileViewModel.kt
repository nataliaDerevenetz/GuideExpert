package com.example.GuideExpert.presentation.ProfileScreen.EditorProfileScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.UpdateAvatarProfileUseCase
import com.example.GuideExpert.domain.repository.ProfileRepository
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.SnackbarEffect
import com.example.GuideExpert.utils.getOrientation
import com.example.GuideExpert.utils.rotateBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

sealed class EditorProfileUiEvent {
    data class OnPermissionGrantedWith(val compositionContext: Context): EditorProfileUiEvent()
    data object OnPermissionDenied: EditorProfileUiEvent()
    data class OnImageSavedWith (val compositionContext: Context): EditorProfileUiEvent()
    data object OnImageSavingCanceled: EditorProfileUiEvent()
    data class OnFinishPickingImagesWith(val compositionContext: Context, val imageUrl: Uri): EditorProfileUiEvent()
    data class OnFirstNameChanged(val firstName: String): EditorProfileUiEvent()
    data class OnLastNameChanged(val lastName: String): EditorProfileUiEvent()
    data class OnSexChanged(val sex: String): EditorProfileUiEvent()
    data class OnEmailChanged(val email: String): EditorProfileUiEvent()
    data class OnBirthdayChanged(val birthday: Date): EditorProfileUiEvent()
    data object OnLoadProfile: EditorProfileUiEvent()
    data object OnSaveProfile: EditorProfileUiEvent()
    data object OnSaveAvatarProfile: EditorProfileUiEvent()
}

data class EditorViewState(
    val tempFileUrl: Uri? = null,
    val selectedPicture: ImageBitmap? = null,
    val tempFileGalleryUrl: Uri? = null,
    val tempFileSystemUrl: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val sex: String = "",
    val birthday: Date? = null, )

sealed interface LoadAvatarState{
    object Idle:LoadAvatarState
    object Loading:LoadAvatarState
    object Success:LoadAvatarState
    data class Error(val error: String):LoadAvatarState
}

data class LoadAvatarUIState(
    val contentState: LoadAvatarState = LoadAvatarState.Idle
)


@RequiresApi(Build.VERSION_CODES.P)
@HiltViewModel
class EditorProfileViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val profileRepository: ProfileRepository,
    @ApplicationContext val application: Context,
    val updateAvatarProfileUseCase: UpdateAvatarProfileUseCase,
    ) : ViewModel() {

    val profileFlow = profileRepository.profileFlow

    private val _editorViewState: MutableStateFlow<EditorViewState> = MutableStateFlow(EditorViewState())
    // exposes the ViewState to the composable view
    val viewStateFlow: StateFlow<EditorViewState>
        get() = _editorViewState

    private val _editorOldViewState: MutableStateFlow<EditorViewState> = MutableStateFlow(EditorViewState())

    private val _stateLoadAvatar = MutableStateFlow<LoadAvatarUIState>(LoadAvatarUIState())
    val stateLoadAvatar: StateFlow<LoadAvatarUIState> = _stateLoadAvatar.asStateFlow()


    private val _effectChannel = Channel<SnackbarEffect>()
    val effectFlow: Flow<SnackbarEffect> = _effectChannel.receiveAsFlow()

    // receives user generated events and processes them in the provided coroutine context
    @RequiresApi(Build.VERSION_CODES.P)
    fun handleEvent(event: EditorProfileUiEvent) = viewModelScope.launch {
        when(event) {
            is EditorProfileUiEvent.OnPermissionGrantedWith -> {
                // Create an empty image file in the app's cache directory
                val tempFile = File.createTempFile(
                    "temp_image_file_", /* prefix */
                    ".jpg", /* suffix */
                    event.compositionContext.cacheDir  /* cache directory */
                )

                Log.d("TAG",tempFile.toString())
                // Create sandboxed url for this temp file - needed for the camera API
                val uri = FileProvider.getUriForFile(event.compositionContext,
                     "${this@EditorProfileViewModel.application.packageName}.provider", /* needs to match the provider information in the manifest */
                    tempFile
                )

                _editorViewState.value = _editorViewState.value.copy(tempFileUrl = uri,tempFileSystemUrl = tempFile.toString(),tempFileGalleryUrl=null)
            }

            is EditorProfileUiEvent.OnPermissionDenied -> {
                // maybe log the permission denial event
                println("User did not grant permission to use the camera")
            }


            is EditorProfileUiEvent.OnFinishPickingImagesWith -> {
                var newImages: ImageBitmap? = null
                val inputStream = event.compositionContext.contentResolver.openInputStream(event.imageUrl)
                val bytes = inputStream?.readBytes()
                inputStream?.close()
                if (bytes != null) {
                    val bitmapOptions = BitmapFactory.Options()
                    bitmapOptions.inMutable = true
                    val bitmap: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bitmapOptions)
                    val orientation = event.imageUrl.getOrientation(event.compositionContext.contentResolver)
                    val rotatedBitmap = orientation.let { rotateBitmap(bitmap, it) } ?: bitmap
                    val imageBitmap = Bitmap.createScaledBitmap(rotatedBitmap, rotatedBitmap.width, rotatedBitmap.height, true)
                    newImages = imageBitmap.asImageBitmap()
                } else {
                    // error reading the bytes from the image url
                    println("The image that was picked could not be read from the device at this url: ${event.imageUrl}")
                }
                val currentViewState = _editorViewState.value
                val newCopy = currentViewState.copy(
                    selectedPicture =  newImages,
                    tempFileGalleryUrl = event.imageUrl,
                    tempFileUrl = null
                )
                _editorViewState.value = newCopy
                updateAvatarProfile()
            }

            is EditorProfileUiEvent.OnImageSavedWith -> {
                val tempImageUrl = _editorViewState.value.tempFileUrl
                if (tempImageUrl != null) {
                    val source = ImageDecoder.createSource(event.compositionContext.contentResolver, tempImageUrl)
                    val currentPictures = ImageDecoder.decodeBitmap(source).asImageBitmap()
                    _editorViewState.value = _editorViewState.value.copy(tempFileUrl = null,
                        selectedPicture = currentPictures)
                    updateAvatarProfile()
                }
            }

            is EditorProfileUiEvent.OnImageSavingCanceled -> {
                _editorViewState.value = _editorViewState.value.copy(tempFileUrl = null,tempFileSystemUrl=null, tempFileGalleryUrl = null)
            }

            is EditorProfileUiEvent.OnFirstNameChanged -> {
                setFirstName(event.firstName)
            }

            is EditorProfileUiEvent.OnLastNameChanged -> {
                setLastName(event.lastName)
            }

            is EditorProfileUiEvent.OnSexChanged -> {
                setSex(event.sex)
            }

            is EditorProfileUiEvent.OnEmailChanged -> {
                setEmail(event.email)
            }

            is EditorProfileUiEvent.OnBirthdayChanged -> {
                setBirthday(event.birthday)
            }

            is EditorProfileUiEvent.OnLoadProfile -> {
                loadProfile()
            }

            is EditorProfileUiEvent.OnSaveProfile -> {
             //   saveProfile()
            }

            is EditorProfileUiEvent.OnSaveAvatarProfile -> {
                updateAvatarProfile()
            }
        }
    }

    private fun setFirstName(firstName: String) {
        _editorViewState.update {
           it.copy(firstName = firstName)
        }
    }

    private fun setLastName(lastName: String) {
        _editorViewState.update {
            it.copy(lastName = lastName)
        }
    }

    private fun setSex(sex: String) {
        _editorViewState.update {
            it.copy(sex = sex)
        }
    }

    private fun setEmail(email: String) {
        _editorViewState.update {
            it.copy(email = email)
        }
    }

    private fun setBirthday(birthday: Date) {
        _editorViewState.update {
            it.copy(birthday = birthday)
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _editorOldViewState.update {
                EditorViewState(
                    firstName = profileFlow.value!!.firstName, lastName = profileFlow.value!!.lastName, sex = profileFlow.value!!.sex,
                    email = profileFlow.value!!.email, birthday = profileFlow.value!!.birthday
                )
            }
            _editorViewState.update {
                EditorViewState(
                    firstName = profileFlow.value!!.firstName, lastName = profileFlow.value!!.lastName, sex = profileFlow.value!!.sex,
                    email = profileFlow.value!!.email, birthday = profileFlow.value!!.birthday
                )
            }
        }
    }

    private fun updateAvatarProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_editorOldViewState.value != _editorViewState.value) {
                if (viewStateFlow.value.tempFileGalleryUrl != null || viewStateFlow.value.tempFileSystemUrl != null) {
                    val imagePart = if ( viewStateFlow.value.tempFileGalleryUrl != null) {
                        createImageRequestBody(viewStateFlow.value.tempFileGalleryUrl!!, "image")
                    } else {
                        MultipartBody.Part.createFormData(
                            name = "image",
                            filename = File(viewStateFlow.value.tempFileSystemUrl!!).getName(),
                            body = File(viewStateFlow.value.tempFileSystemUrl!!).asRequestBody(contentType = "image/jpeg".toMediaType()),
                        )
                    }
                    imagePart.let { it ->
                        if (it != null) {
                            updateAvatarProfileUseCase(it).collectLatest { resources ->
                                when(resources){
                                    is UIResources.Error -> {
                                        _editorViewState.update { it.copy(selectedPicture = null) }
                                        sendEffectFlow("Error loading avatar : ${resources.message}")
                                        _stateLoadAvatar.update { it.copy(contentState = LoadAvatarState.Error(resources.message)) }
                                    }
                                    is UIResources.Loading -> {
                                        _stateLoadAvatar.update { it.copy(contentState = LoadAvatarState.Loading) }
                                    }
                                    is UIResources.Success -> {
                                        _stateLoadAvatar.update { it.copy(contentState = LoadAvatarState.Success) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    suspend fun sendEffectFlow(message: String, actionLabel: String? = null) {
        Log.d("MODEL", message)
        _effectChannel.send(SnackbarEffect.ShowSnackbar(message))
    }

    init{
       handleEvent(EditorProfileUiEvent.OnLoadProfile)
    }


    private fun createImageRequestBody(imageUri: Uri, fileName: String): MultipartBody.Part? {
        application.contentResolver?.query(imageUri, null, null, null, null)?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(MediaStore.MediaColumns.DATA)
                if(index >=0) {
                    val picturePath = it.getString(index)

                    val requestBody =
                        File(picturePath).asRequestBody("image/jpeg".toMediaTypeOrNull())
                    return MultipartBody.Part.createFormData(
                        fileName,
                        "${fileName}.jpeg",
                        requestBody
                    )
                }
            }
        }
        return null
    }
}