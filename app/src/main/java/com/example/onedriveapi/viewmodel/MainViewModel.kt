package com.example.onedriveapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.onedriveapi.PreferencesHelper
import com.example.onedriveapi.livedata.SingleLiveEvent

class MainViewModel : ViewModel() {

    private val _authenticationLogin = SingleLiveEvent<Boolean>()
    val authenticationLogin: LiveData<Boolean> = _authenticationLogin

    private fun authenticationOneDrive() {
        val refreshToken = PreferencesHelper.getRefreshToken()
        if (refreshToken.isEmpty()) {
            _authenticationLogin.value = true
        }
    }


}