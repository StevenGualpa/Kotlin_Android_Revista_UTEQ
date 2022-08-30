package com.geek.kotlin_api_revistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class activity_webview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val bundle = this.getIntent().getExtras();
        var link= bundle?.getString("link")

        var webview: WebView =findViewById(R.id.webview);
        webview.webChromeClient=object : WebChromeClient(){

        }
        webview.webViewClient=object : WebViewClient(){

        }
        webview.settings.javaScriptEnabled = true

        val pdf = "https://revistas.uteq.edu.ec/index.php/ingenio/article/view/9/8"

        if (link != null) {
            //webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true")
            webview.loadUrl("https://revistas.uteq.edu.ec/index.php/ingenio/article/view/9/8")
            webview.loadUrl(link)
            webview.WebViewTransport()

        }
    }
}