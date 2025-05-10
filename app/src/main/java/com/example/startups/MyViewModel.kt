package com.example.startups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    // Signed-in status as LiveData
    private val _signedIn = MutableLiveData<Boolean>()
    val signedIn: LiveData<Boolean> get() = _signedIn

    // Access token as LiveData
    private val _access = MutableLiveData<String>()
    val access: LiveData<String> get() = _access

    // Refresh token as LiveData
    private val _refresh = MutableLiveData<String>()
    val refresh: LiveData<String> get() = _refresh

    // Function to update signed-in status
    fun setSignedIn(isSignedIn: Boolean) {
        _signedIn.value = isSignedIn
    }

    // Function to set access token
    fun setAccessToken(token: String) {
        _access.value = token
    }

    // Function to set refresh token
    fun setRefreshToken(token: String) {
        _refresh.value = token
    }

}