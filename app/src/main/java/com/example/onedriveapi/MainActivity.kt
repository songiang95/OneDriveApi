package com.example.onedriveapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.onedriveapi.api.RetrofitBuilder
import com.example.onedriveapi.api.data.OAuthToken
import com.example.onedriveapi.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody

const val EXTRA_URL = "extra_url"

class MainActivity : AppCompatActivity() {

    private lateinit var register: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        PreferencesHelper.start(this)
        initViewAction()
        createRegisterForActivityResult()
    }

    private fun initViewAction() {
        binding.btnLogin.setOnClickListener {
            lifecycleScope.launch {
                if (!isAuthenticated()) {
                    authenticationOneDrive()
                } else {
                    Toast.makeText(this@MainActivity, "authenticated", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnGetData.setOnClickListener {
            lifecycleScope.launch {
                val response =
                    RetrofitBuilder.oneDriveApi.getDriveChildren(RetrofitBuilder.ONEDRIVE_ROOT_PATH)
                Log.d("abba", "response:${response.body()?.value?.size}")
            }
        }
    }

    private fun createRegisterForActivityResult(){
         register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                val code = it.data?.data?.getQueryParameter("code")
                if (code != null) {
                    lifecycleScope.launch {
                        //get refresh token
                        val refreshToken = getRefreshToken(code)
                        if (refreshToken != null) {
                            //get access token
                            getAccessToken(refreshToken.refresh_token)
                        }
                    }
                }
            }
        }
    }

    private fun loginToAuthentication() {
        val intentLoadUrl = Intent(this, LoginActivity::class.java).apply {
            putExtra(EXTRA_URL, RetrofitBuilder.LOGIN_URL)
        }
        register.launch(intentLoadUrl)
    }

    private suspend fun authenticationOneDrive() {
        val refreshToken = PreferencesHelper.getRefreshToken()
        if (refreshToken.isEmpty()) {
            //login
            loginToAuthentication()
        } else {
            //get new access token from refresh token
            getAccessToken(refreshToken)
        }
    }

    private suspend fun isAuthenticated(): Boolean = withContext(Dispatchers.IO) {
        val isAuthenticated: Boolean

        val accessToken = PreferencesHelper.getAccessToken()
        isAuthenticated = if (accessToken.isEmpty()) {
            false
        } else {
            val response =
                RetrofitBuilder.oneDriveApi.getDriveChildren(RetrofitBuilder.ONEDRIVE_ROOT_PATH)

            response.isSuccessful
        }
        isAuthenticated
    }

    private fun handleAuthToken(isRefreshToken: Boolean, authToken: OAuthToken?) {
        if (authToken == null) {
            if (isRefreshToken) {
                Toast.makeText(this, "get refresh token failed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "get access token failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (isRefreshToken) {
                Toast.makeText(this, "get refresh token success", Toast.LENGTH_SHORT).show()
                PreferencesHelper.saveRefreshToken(authToken.refresh_token)
            } else {
                Toast.makeText(this, "get access token success", Toast.LENGTH_SHORT).show()
                PreferencesHelper.saveAccessToken(authToken.access_token)
            }
        }
    }

    private suspend fun getAccessToken(refreshToken: String): OAuthToken? {
        val formBody = FormBody.Builder().apply {
            add("grant_type", "refresh_token")
            add("client_id", RetrofitBuilder.CLIENT_ID)
            add("redirect_uri", RetrofitBuilder.REDIRECT_URI)
            add("refresh_token", refreshToken)
        }.build()

        val result = RetrofitBuilder.authOneDriveApi.getAccessToken(
            RetrofitBuilder.CLIENT_SECRET,
            formBody
        )
        handleAuthToken(false, result)
        return result
    }

    private suspend fun getRefreshToken(code: String): OAuthToken? {
        val formBody = FormBody.Builder().apply {
            add("grant_type", "authorization_code")
            add("code", code)
            add("client_id", RetrofitBuilder.CLIENT_ID)
            add("redirect_uri", RetrofitBuilder.REDIRECT_URI)
        }.build()

        val result = RetrofitBuilder.authOneDriveApi.getAccessToken(
            RetrofitBuilder.CLIENT_SECRET,
            formBody
        )
        handleAuthToken(true, result)
        return result
    }

}