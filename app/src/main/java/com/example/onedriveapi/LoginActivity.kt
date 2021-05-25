package com.example.onedriveapi

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.onedriveapi.api.RetrofitBuilder
import com.example.onedriveapi.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initWebview()
        val url = intent.getStringExtra(EXTRA_URL)
        if (url.isNullOrEmpty()) finish()

        binding.webview.loadUrl(url!!)

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebview() {
        binding.webview.webChromeClient = WebChromeClient()
        binding.webview.webViewClient = MyWebViewClient()
        binding.webview.settings.javaScriptEnabled = true

    }


    inner class MyWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.url.text = url
            if (url?.startsWith(RetrofitBuilder.REDIRECT_URI) == true) {
                setResult(RESULT_OK, Intent().apply {
                    data = Uri.parse(url)
                })
                finish()
            }
        }
    }

}

