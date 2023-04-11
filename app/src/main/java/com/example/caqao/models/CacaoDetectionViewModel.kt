package com.example.caqao.models

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.example.caqao.MainActivity
import com.example.caqao.network.CacaoApi
import com.example.caqao.network.CacaoDetection
import com.example.caqao.network.ImageValidationStatus
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

enum class CaqaoApiStatus { LOADING, ERROR, DONE }

class CacaoDetectionViewModel: ViewModel() {

    private val _selectedImage = MutableLiveData<Uri>()
    val selectedImage: LiveData<Uri>
        get() = _selectedImage

    private val _status = MutableLiveData<CaqaoApiStatus>()
    val status: LiveData<CaqaoApiStatus> = _status

    private val _cacaoDetection = MutableLiveData<CacaoDetection>()
    val cacaoDetection: LiveData<CacaoDetection>
        get() = _cacaoDetection

    private val _detections = MutableLiveData<List<CacaoDetection>>()
    val detections: LiveData<List<CacaoDetection>> = _detections

    private val _imageValidationStatus = MutableLiveData<ImageValidationStatus>()
    val imageValidationStatus: LiveData<ImageValidationStatus> = _imageValidationStatus

    private val _detectionStatus = MutableLiveData<String>()
    val detectionStatus: LiveData<String> = _detectionStatus

    init {
        resetCacaoDetection()
    }

    fun selectImage(uri: Uri) {
        _selectedImage.value = uri
    }

    fun assessCacaoBeans(context: Context, contentResolver: ContentResolver, beanSize: Int) {
        val filesDir = context.filesDir
        val file = File(filesDir, "image.jpg")

        val inputSteam = _selectedImage.value?.let { contentResolver.openInputStream(it) }
        val outputStream = FileOutputStream(file)
        inputSteam!!.copyTo(outputStream)

        val imageFileRequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageFilePart  = MultipartBody.Part.createFormData("image", file.name,
            imageFileRequestBody)

        viewModelScope.launch {
            _status.value = CaqaoApiStatus.LOADING
            try {
                _cacaoDetection.value = CacaoApi.retrofitService.assess(imageFilePart, beanSize)
                Log.d("BeanGrade", "BeanGrade: ${_cacaoDetection.value!!.beanGrade}")

                if (_cacaoDetection.value!!.beanGrade == "--") {
                    Toast.makeText(context, "No Cacao Beans Detected!", Toast.LENGTH_SHORT).show()
                }

                _status.value = CaqaoApiStatus.DONE
            } catch (e: retrofit2.HttpException) {
                Log.d("HTTPError", "Error: ${e}")
                _status.value = CaqaoApiStatus.ERROR
            } catch (e: Exception) {
                Log.d("AssessmentFailed", "Error: ${e}")
                _status.value = CaqaoApiStatus.ERROR
            }
        }
    }

    fun saveAssessmentResults() {
        viewModelScope.launch {
            try {
                CacaoApi.retrofitService.saveDetectionResults(
                    _cacaoDetection.value?.imgSrcUrl.toString())
            } catch (e: Exception) {
                Log.d("Saving Results Failed", "Error: ${e}")
            }
        }
    }

    fun resetCacaoDetection() {
        _cacaoDetection.value = CacaoDetection(
            -1, null, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, "--", "--")
    }

    fun getCacaoDetections() {
        viewModelScope.launch {
            try {
                _detections.value = CacaoApi.retrofitService.getDetections()
                _detectionStatus.value = "Success: CAQAO detections retrieved"
            } catch (e: Exception) {
                _detectionStatus.value = "Failure: ${e.message}"
            }
        }
    }

    fun validateImage(context: Context, contentResolver: ContentResolver) {
        val filesDir = context.filesDir
        val file = File(filesDir, "image.jpg")

        val inputSteam = _selectedImage.value?.let { contentResolver.openInputStream(it) }
        val outputStream = FileOutputStream(file)
        inputSteam!!.copyTo(outputStream)

        val imageFileRequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageFilePart  = MultipartBody.Part.createFormData("image", file.name,
            imageFileRequestBody)

        viewModelScope.launch {
            try {
                // TODO: send image to the server and check if the image contains cacao beans or not
                _imageValidationStatus.value = CacaoApi.retrofitService.validateImage(imageFilePart)
                val response = _imageValidationStatus.value
                if (response?.status == 200) {
                    Log.d("ImageValidationSuccess",
                        "HTTP status code: ${response.status} Message: ${response.message}")
                }
                else {
                    Log.d("ImageValidationFailed",
                        "HTTP status code: ${response?.status} Message: ${response?.message}")
                    Toast.makeText(context, "${response?.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.d("ImageValidationFailed", "Error: ${e}")
            }
        }

    }


}
