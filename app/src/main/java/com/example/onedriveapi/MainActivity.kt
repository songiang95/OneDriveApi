package com.example.onedriveapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.onedriveapi.api.RetrofitBuilder
import com.example.onedriveapi.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val EXTRA_URL = "extra_url"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val intentLoadUrl = Intent(this, LoginActivity::class.java).apply {
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
                            Log.d("abba", "ax: ")
                            RetrofitBuilder.oneDriveApi.getAccessToken(
                                RetrofitBuilder.CLIENT_ID,
                                RetrofitBuilder.REDIRECT_URI,
                                RetrofitBuilder.CLIENT_SECRET,
                                code
                            ).execute()
                        }
                        Log.d("abba", "token: ${oathToken.body()}")
                    }
                }
            }

        }

        binding.btnLogin.setOnClickListener {
            register.launch(intentLoadUrl)
        }


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) return
        Log.d("abba", "onNewIntent: ${intent.data}")
    }
}