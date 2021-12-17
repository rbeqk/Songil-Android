package com.example.songil.webview_address

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.WebviewActivityAddressBinding

class WebAddressActivity : BaseActivity<WebviewActivityAddressBinding>(R.layout.webview_activity_address) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWeb()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWeb(){
        val webview = binding.webView
        webview.settings.javaScriptEnabled = true
        webview.addJavascriptInterface(KaKaoJavaScriptInterface(), "Android")
        webview.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                binding.webView.loadUrl("javascript:execDaumPostcode();")
            }
        }
        webview.loadUrl("https://l5x5l.github.io/webview_test/")
    }

    inner class KaKaoJavaScriptInterface {
        @JavascriptInterface
        fun processDATA(address: String?) {
            Intent().apply {
                putExtra("address", address)
                setResult(RESULT_OK, this)
            }
            finish()
        }
    }
}