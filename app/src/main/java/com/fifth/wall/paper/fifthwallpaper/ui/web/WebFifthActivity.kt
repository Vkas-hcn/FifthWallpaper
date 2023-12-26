package com.fifth.wall.paper.fifthwallpaper.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.fifth.wall.paper.fifthwallpaper.R

class WebFifthActivity : AppCompatActivity() {
    private lateinit var imageViewBack: ImageView
    private lateinit var webFifth: WebView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_fifth)
        imageViewBack=findViewById(R.id.imageView_back)
        webFifth=findViewById(R.id.webView_fifth)
        imageViewBack.setOnClickListener { finish() }
        iniWeb()
    }
    private fun iniWeb(){
        webFifth.loadUrl("https://www.baidu.com/")
        webFifth.settings.javaScriptEnabled=true
        webFifth.webChromeClient = object : android.webkit.WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        }
        webFifth.webViewClient = object : android.webkit.WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
    }
}