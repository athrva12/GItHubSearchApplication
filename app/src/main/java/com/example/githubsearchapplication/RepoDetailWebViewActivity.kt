package com.example.githubsearchapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class RepoDetailWebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gitUrl = intent.getStringExtra("URL")
        setContentView(R.layout.activity_repo_detail_web_view)
        webView = findViewById(R.id.webView)
        val weSett = webView.settings
        weSett.javaScriptEnabled = true
        if (gitUrl != null) {
            webView.loadUrl(gitUrl)
        }

    }
}