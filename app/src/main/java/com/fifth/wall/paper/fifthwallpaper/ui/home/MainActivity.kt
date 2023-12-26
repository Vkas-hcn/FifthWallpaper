package com.fifth.wall.paper.fifthwallpaper.ui.home

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.fifth.wall.paper.fifthwallpaper.R
import com.fifth.wall.paper.fifthwallpaper.ui.setting.SettingActivity
import com.fifth.wall.paper.fifthwallpaper.ui.web.WebFifthActivity
import com.fifth.wall.paper.fifthwallpaper.utils.FifthUtils

class MainActivity : AppCompatActivity() {
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mainList: List<Drawable>
    private lateinit var mainRecycler: androidx.recyclerview.widget.RecyclerView
    private lateinit var imageViewBack: ImageView
    private lateinit var atvPp: AppCompatTextView
    private lateinit var atvShare: AppCompatTextView
    private lateinit var atvUpdate: AppCompatTextView
    private lateinit var llMenu: LinearLayout
    private lateinit var llMenuContent: LinearLayout


    private lateinit var imgTop1: ImageView
    private lateinit var imgTop2: ImageView
    private lateinit var imgTop3: ImageView
    private lateinit var imgTop4: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initAdapter()
        initClick()
    }

    private fun initView() {
        mainRecycler = findViewById(R.id.rv_main)
        imageViewBack = findViewById(R.id.imageView_menu)
        atvPp = findViewById(R.id.atv_pp)
        atvShare = findViewById(R.id.atv_share)
        atvUpdate = findViewById(R.id.atv_update)
        llMenu = findViewById(R.id.ll_menu)
        llMenuContent = findViewById(R.id.ll_menu_content)
        imgTop1=findViewById(R.id.img_top_1)
        imgTop2=findViewById(R.id.img_top_2)
        imgTop3=findViewById(R.id.img_top_3)
        imgTop4=findViewById(R.id.img_top_4)
    }

    private fun initClick() {
        imageViewBack.setOnClickListener {
            llMenu.visibility = android.view.View.VISIBLE
        }
        llMenu.setOnClickListener {
            llMenu.visibility = android.view.View.GONE
        }
        llMenuContent.setOnClickListener {
        }
        atvPp.setOnClickListener {
            //隐私政策
            val intent = Intent(this, WebFifthActivity::class.java)
            startActivity(intent)
        }
        atvShare.setOnClickListener {
            //分享
            FifthUtils.shareText(
                this, "https://play.google.com/store/apps/details?id=${packageName}"
            )
        }
        atvUpdate.setOnClickListener {
            //更新
            FifthUtils.toWeb(this, "https://play.google.com/store/apps/details?id=${packageName}")
        }
        imgTop1.setOnClickListener {
            toSettingActivity(6)
        }
        imgTop2.setOnClickListener {
            toSettingActivity(32)
        }
        imgTop3.setOnClickListener {
            toSettingActivity(10)
        }
        imgTop4.setOnClickListener {
            toSettingActivity(20)
        }
    }

    private fun initAdapter() {
        mainList = FifthUtils.getWallList(this)
        mainRecycler.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        mainRecycler.addItemDecoration(getItemDecoration())
        mainAdapter = MainAdapter(this, mainList)
        mainRecycler.adapter = mainAdapter
        mainAdapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                toSettingActivity(position)
            }
        })
    }

    //item边距
    private fun getItemDecoration(): androidx.recyclerview.widget.RecyclerView.ItemDecoration {
        return object : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: android.graphics.Rect,
                view: android.view.View,
                parent: androidx.recyclerview.widget.RecyclerView,
                state: androidx.recyclerview.widget.RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                val spanCount = 2
                val spacing = 20
                if (position >= 0) {
                    val column = position % spanCount
                    outRect.left = spacing - column * spacing / spanCount
                    outRect.right = (column + 1) * spacing / spanCount
                    if (position < spanCount) {
                        outRect.top = spacing
                    }
                    outRect.bottom = spacing
                } else {
                    outRect.left = 0
                    outRect.right = 0
                    outRect.top = 0
                    outRect.bottom = 0
                }
            }
        }
    }

    //跳转到设置页
    private fun toSettingActivity(position: Int) {
        val intent = Intent(this, SettingActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("imgPos", position)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(event?.keyCode == KeyEvent.KEYCODE_BACK){
            if(llMenu.visibility == android.view.View.VISIBLE){
                llMenu.visibility = android.view.View.GONE
                return true
            }
            finish()
        }
        return true
    }
}