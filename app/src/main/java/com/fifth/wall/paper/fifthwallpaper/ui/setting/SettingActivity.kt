package com.fifth.wall.paper.fifthwallpaper.ui.setting

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.fifth.wall.paper.fifthwallpaper.R
import com.fifth.wall.paper.fifthwallpaper.utils.FifthUtils
import com.fifth.wall.paper.fifthwallpaper.utils.SaveImageToGallery

class SettingActivity : AppCompatActivity() {
    private lateinit var imgDetail: AppCompatImageView
    private lateinit var imgBack: ImageView

    private lateinit var tvDown: TextView
    private lateinit var tvApply: TextView

    private lateinit var tvSetSrc: TextView
    private lateinit var tvSetHome: TextView
    private lateinit var tvSetBoth: TextView
    private lateinit var tvCancel: TextView
    private lateinit var llButton: LinearLayout
    private lateinit var llSetting: LinearLayout
    private lateinit var loading: ProgressBar
    private var imgDrawable: Int? = null
    private var drawable: Drawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initView()
        iniData()
        click()
    }

    private fun initView() {
        imgDetail = findViewById(R.id.img_detail)
        tvDown = findViewById(R.id.tv_download)
        tvApply = findViewById(R.id.tv_apply)
        tvSetSrc = findViewById(R.id.tv_set_as_screen)
        tvSetHome = findViewById(R.id.tv_set_as_home)
        tvSetBoth = findViewById(R.id.tv_set_as_both)
        tvCancel = findViewById(R.id.tv_cancel)
        llButton = findViewById(R.id.ll_button)
        llSetting = findViewById(R.id.ll_setting)
        loading = findViewById(R.id.pb_loading)
        imgBack = findViewById(R.id.imageView_back)
    }

    private fun iniData() {
        imgDrawable = intent.getIntExtra("imgPos", 0)
        drawable = FifthUtils.getWallList(this).getOrNull(imgDrawable ?: 0)
        imgDetail.setImageDrawable(drawable)
    }

    private fun click() {
        imgBack.setOnClickListener { finish() }
        tvDown.setOnClickListener {
            //下载
            SaveImageToGallery.saveImageToGallery(this, drawable!!)
        }
        tvApply.setOnClickListener {
            //应用
            setWallUI()

        }
        tvSetSrc.setOnClickListener {
            //设置为锁屏
            setWall(2)
        }
        tvSetHome.setOnClickListener {
            setWall(1)
        }
        tvSetBoth.setOnClickListener {
            setBothWall()
        }
        tvCancel.setOnClickListener {
            setCancelUI()
        }
    }


    private fun setWallUI() {
        //设置壁纸
        llButton.visibility = View.GONE
        llSetting.visibility = View.VISIBLE
        tvCancel.visibility = View.VISIBLE
    }

    private fun setCancelUI() {
        //取消设置
        llButton.visibility = View.VISIBLE
        llSetting.visibility = View.GONE
        tvCancel.visibility = View.GONE
    }

    private fun setWall(type:Int) {
        FifthUtils.setImageViewFun(
            type,
            this,
            drawable!!,
            {
                loading.visibility = View.VISIBLE
            },
            {
                setFinish()
            }
        )
    }
    private fun setBothWall() {
        FifthUtils.setBothWallFun(
            this,
            drawable!!,
            {
                loading.visibility = View.VISIBLE
            },
            {
                setFinish()
            }
        )
    }

    private fun setFinish() {
        loading.visibility = View.GONE
        setCancelUI()
        toSuccessActivity()
    }

    //跳转成功页
    private fun toSuccessActivity(){
        val intent = Intent(this, SuccessActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("imgPos",imgDrawable ?: 0)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==SaveImageToGallery.REQUEST_WRITE_EXTERNAL_STORAGE){
            SaveImageToGallery.saveImageToGallery(this, drawable!!)
        }
    }
}