package com.example.GuideExpert.presentation.ProfileScreen.EditorProfileScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

sealed class Intent {
    data class OnPermissionGrantedWith(val compositionContext: Context): Intent()
    data object OnPermissionDenied: Intent()
    data class OnImageSavedWith (val compositionContext: Context): Intent()
    data object OnImageSavingCanceled: Intent()
    data class OnFinishPickingImagesWith(val compositionContext: Context, val imageUrls: Uri): Intent()
}

data class EditorViewState(
    val tempFileUrl: Uri? = null,
    val selectedPictures: ImageBitmap? = null,
)

@HiltViewModel
class EditorProfileViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val profileRepository: ProfileRepository,
    @ApplicationContext val application: Context,
) : ViewModel() {

    val profileFlow = profileRepository.profileFlow

    private val _editorViewState: MutableStateFlow<EditorViewState> = MutableStateFlow(EditorViewState())
    // exposes the ViewState to the composable view
    val viewStateFlow: StateFlow<EditorViewState>
        get() = _editorViewState
    // endregion

    // region Intents
    // receives user generated events and processes them in the provided coroutine context
    @RequiresApi(Build.VERSION_CODES.P)
    fun onReceive(intent: Intent) = viewModelScope.launch {
        when(intent) {
            is Intent.OnPermissionGrantedWith -> {
                // Create an empty image file in the app's cache directory
                val tempFile = File.createTempFile(
                    "temp_image_file_", /* prefix */
                    ".jpg", /* suffix */
                    intent.compositionContext.cacheDir  /* cache directory */
                )
                // Create sandboxed url for this temp file - needed for the camera API
                val uri = FileProvider.getUriForFile(intent.compositionContext,
                     "${this@EditorProfileViewModel.application.packageName}.provider", /* needs to match the provider information in the manifest */
                    tempFile
                )
                _editorViewState.value = _editorViewState.value.copy(tempFileUrl = uri)
            }

            is Intent.OnPermissionDenied -> {
                // maybe log the permission denial event
                println("User did not grant permission to use the camera")
            }


            is Intent.OnFinishPickingImagesWith -> {
                var newImages: ImageBitmap? = null
                val inputStream = intent.compositionContext.contentResolver.openInputStream(intent.imageUrls)
                val bytes = inputStream?.readBytes()
                inputStream?.close()
                if (bytes != null) {
                    val bitmapOptions = BitmapFactory.Options()
                    bitmapOptions.inMutable = true
                    val bitmap: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bitmapOptions)
                    newImages = bitmap.asImageBitmap()
                } else {
                    // error reading the bytes from the image url
                    println("The image that was picked could not be read from the device at this url: ${intent.imageUrls}")
                }
                val currentViewState = _editorViewState.value
                val newCopy = currentViewState.copy(
                    selectedPictures =  newImages,
                    tempFileUrl = null
                )
                _editorViewState.value = newCopy
            }

            is Intent.OnImageSavedWith -> {
                val tempImageUrl = _editorViewState.value.tempFileUrl
                if (tempImageUrl != null) {
                    val source = ImageDecoder.createSource(intent.compositionContext.contentResolver, tempImageUrl)
                    val currentPictures = ImageDecoder.decodeBitmap(source).asImageBitmap()
                    _editorViewState.value = _editorViewState.value.copy(//tempFileUrl = null,
                        selectedPictures = currentPictures)
                }
            }

            is Intent.OnImageSavingCanceled -> {
                _editorViewState.value = _editorViewState.value.copy(tempFileUrl = null)
            }
        }
    }
}