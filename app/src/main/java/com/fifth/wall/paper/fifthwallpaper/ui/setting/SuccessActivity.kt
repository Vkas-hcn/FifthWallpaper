package com.fifth.wall.paper.fifthwallpaper.ui.setting

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.fifth.wall.paper.fifthwallpaper.R
import com.fifth.wall.paper.fifthwallpaper.ui.home.MainAdapter
import com.fifth.wall.paper.fifthwallpaper.utils.FifthUtils

class SuccessActivity : AppCompatActivity() {
    private lateinit var bakc: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)
        setBack()
        setImageView()
    }

    private fun setBack() {
        bakc = findViewById(R.id.imageView_back)
        bakc.setOnClickListener {
            finish()
        }
    }

    private fun setImageView() {
        val imgEnd: ImageView = findViewById(R.id.img_end)
        val imgDrawable: Int?
        imgDrawable = intent.getIntExtra("imgPos", 0)
        val drawable: Drawable? = FifthUtils.getWallList(this).getOrNull(imgDrawable ?: 0)
        imgEnd.setImageDrawable(drawable)
    }
}