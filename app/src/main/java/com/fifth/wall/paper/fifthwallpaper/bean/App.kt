package com.fifth.wall.paper.fifthwallpaper.bean

import android.app.Application
import android.content.Context
import android.util.Log
import com.fifth.wall.paper.fifthwallpaper.utils.FifthUtils
import com.fifth.wall.paper.fifthwallpaper.utils.NetClassFun
import com.fifth.wall.paper.fifthwallpaper.utils.NetFun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class App : Application() {
    var successState = false
    companion object{
        private lateinit var instance: App
        fun getAppContext() = instance
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        if (NetFun.uu_stunning.isEmpty()) {
            NetFun.uu_stunning = UUID.randomUUID().toString()
        }
        getBlackList(this@App)
    }

    fun getBlackList(context: Context) {
        if (NetFun.hmd_data.isNotEmpty()) {
            return
        }
        GlobalScope.launch(Dispatchers.IO) {
            getBlackList(context, onSuccess = {
                successState = true
                Log.e("TAG", "The blacklist request is successful：$it")
                NetFun.hmd_data = it
            }, onFail = {
                retry(context)
            })
        }
    }
    //发起黑名单请求
    fun getBlackList(context: Context,onSuccess:(it:String)->Unit,onFail:(it:String)->Unit) {
        val map = NetFun.getHmdData(context)
        Log.e("TAG", "Blacklist request data= $map", )
        NetClassFun().getMapData("https://swigging.stunningwallpapers.net/basin/goody", map, {
            onSuccess(it)
        }, {
            //请求失败
            onFail(it)
        })
    }
    //请求重试
    fun retry(context: Context) {
        successState = false
        GlobalScope.launch(Dispatchers.IO) {
            delay(10000)
            Log.e("TAG", "The blacklist request failed")
            getBlackList(context)
        }
    }

}