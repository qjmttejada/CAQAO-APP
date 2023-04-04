package com.example.caqao.models

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.example.caqao.MainActivity
import com.example.caqao.network.CacaoApi
import com.example.caqao.network.CacaoDetection
import com.example.caqao.network.UserToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors

enum class CaqaoApiStatus { LOADING, ERROR, DONE }

var USER_TOKEN: String? = null
var USER_TOKEN_STATUS: Int? = null

class CacaoDetectionViewModel: ViewModel() {

    private val _selectedImage = MutableLiveData<Uri>()
    val selectedImage: LiveData<Uri>
        get() = _selectedImage

    private val _status = MutableLiveData<CaqaoApiStatus>()
    val status: LiveData<CaqaoApiStatus> = _status

    private val _cacaoDetection = MutableLiveData<CacaoDetection>()
    val cacaoDetection: LiveData<CacaoDetection>
        get() = _cacaoDetection

    private val _userToken = MutableLiveData<UserToken>()
    val userToken: LiveData<UserToken>
        get() = _userToken

    private val _detectionLen = MutableLiveData<String>()
    val detectionLen: LiveData<String> = _detectionLen

    private val _detections = MutableLiveData<List<CacaoDetection>>()
    val detections: LiveData<List<CacaoDetection>> = _detections

    private val _detectionStatus = MutableLiveData<String>()
    val detectionStatus: LiveData<String> = _detectionStatus

    init {
        resetCacaoDetection()
        resetUserToken()
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
                _status.value = CaqaoApiStatus.DONE
            } catch (e: Exception) {
                _status.value = CaqaoApiStatus.ERROR
            }
        }
    }

    fun saveAssessmentResults() {
        viewModelScope.launch {
            try {
                CacaoApi.retrofitService.saveDetectionResults(
                    "Bearer ${USER_TOKEN}",
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

    fun registerUser(firstName: String, lastName: String, email: String, username: String,
        password: String) {
        viewModelScope.launch {
            try {
                CacaoApi.retrofitService.registerUser(firstName, lastName, email, username, password)
                // Log.d("ImgSrcUrl", "${_cacaoDetection.value?.imgSrcUrl.toString()}")
            } catch (e: Exception) {
                Log.d("RegistrationFailed", "Error: ${e}")
            }
        }
    }

    fun loginUser(username: String, password: String): Boolean {

        viewModelScope.launch {
            try {
                _userToken.value = CacaoApi.retrofitService.loginUser(username, password)
                USER_TOKEN = _userToken.value!!.accessToken.toString()
                USER_TOKEN_STATUS = _userToken.value!!.status
                delay(2000)
                Log.d("UserToken", "Token: ${USER_TOKEN}")
                Log.d("UserTokenStatus", "${_userToken.value!!.status}")
            } catch (e: Exception) {
                Log.d("LoginFailed", "Error: ${e}")
            }
        }

        return USER_TOKEN_STATUS == 200
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

    fun resetUserToken() {
        _userToken.value = UserToken("--", 401)
    }
}
