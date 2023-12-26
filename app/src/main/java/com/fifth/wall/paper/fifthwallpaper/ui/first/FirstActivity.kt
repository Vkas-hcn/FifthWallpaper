package com.fifth.wall.paper.fifthwallpaper.ui.first

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fifth.wall.paper.fifthwallpaper.R
import com.fifth.wall.paper.fifthwallpaper.ui.home.MainActivity
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.*

class FirstActivity:AppCompatActivity() {
    private lateinit var processBar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        init()
    }
    fun init(){
        processBar = findViewById(R.id.progressBar)
        processBar()
    }
    //进度条2秒
    private fun processBar(){
        lifecycleScope.launch {
            try {
                withTimeout(2000){
                    while (processBar.progress < 100){
                        processBar.progress += 1
                        delay(20)
                    }
                }
            }catch (e:TimeoutCancellationException){
                processBar.progress = 100
                toMainActivity()
                cancel()
            }
        }
    }

    //跳转到主页
    private fun toMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return keyCode == KeyEvent.KEYCODE_BACK
    }

}