package com.example.onedriveapi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.onedriveapi.api.RetrofitBuilder
import com.example.onedriveapi.api.data.OAuthToken
import com.example.onedriveapi.databinding.ActivityMainBinding
import com.google.gson.JsonObject
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalException
import retrofit2.Response

const val EXTRA_URL = "extra_url"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        createSingleAccountApp()

        /*  val intentLoadUrl = Intent(this, LoginActivity::class.java).apply {
              putExtra(EXTRA_URL, RetrofitBuilder.LOGIN_URL)
          }

          val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
              Log.d("abba", "result code: ${it.resultCode}")
              Log.d("abba", "result data: ${it.data?.data?.getQueryParameter("code")}")
              if (it.resultCode == RESULT_OK && it.data != null) {
                  val code = it.data?.data?.getQueryParameter("code")
                  if (code != null) {
                      lifecycleScope.launch {
                          val oathToken = withContext(Dispatchers.IO) {
                              getAccessToken(code)
                          }

                          Log.d("abba", "receive token: ${oathToken.body()}")
                          Log.d("abba", "success: ${oathToken.isSuccessful}")
                      }
                  }
              }

          }*/

        binding.btnLogin.setOnClickListener {
//            register.launch(intentLoadUrl)
            mSingleAccountApp?.signIn(this, null, getScope(), getAuthInteractiveCallback())
        }
    }

    private suspend fun getAccessToken(code: String): Response<OAuthToken> {
        Log.d("abba", "start request")
        /*       val requestBody = RequestBody(
                   RetrofitBuilder.CLIENT_ID,
                   RetrofitBuilder.REDIRECT_URI,
                   code,
                   "authorization_code"
               )*/

        val requestBody = JsonObject()
        requestBody.addProperty("grant_type", "authorization_code")
        requestBody.addProperty("code", code)
        requestBody.addProperty("client_id", RetrofitBuilder.CLIENT_ID)
        requestBody.addProperty("redirect_uri", RetrofitBuilder.REDIRECT_URI)

        return RetrofitBuilder.oneDriveApi.getAccessToken(
            RetrofitBuilder.CLIENT_SECRET,
            requestBody
        )
    }

    private var mSingleAccountApp: ISingleAccountPublicClientApplication? = null

    private fun createSingleAccountApp() {
        PublicClientApplication.createSingleAccountPublicClientApplication(
            this,
            R.raw.auth_config_single_account,
            object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {
                override fun onCreated(application: ISingleAccountPublicClientApplication?) {
                    mSingleAccountApp = application
                    Log.d("abba", "createSingleAccountApp: $application")
                }

                override fun onError(exception: MsalException?) {
                    Log.d("abba", "onError: ${exception?.message}")
                }

            })
    }

    private fun getAuthInteractiveCallback(): AuthenticationCallback {
        return object : AuthenticationCallback {
            override fun onSuccess(authenticationResult: IAuthenticationResult?) {
                Log.d("abba", "onSuccess: ")
            }

            override fun onError(exception: MsalException?) {
                Log.d("abba", "onError: ")
            }

            override fun onCancel() {
                Log.d("abba", "onCancel: ")
            }

        }
    }

    private fun getScope(): Array<String> {
        return arrayOf(
            "offline_access",
            "files.read.all"
        )
    }

}