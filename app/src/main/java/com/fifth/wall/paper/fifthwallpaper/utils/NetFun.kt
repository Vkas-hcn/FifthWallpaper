package com.fifth.wall.paper.fifthwallpaper.utils
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.provider.Settings
import android.telephony.TelephonyManager
import com.fifth.wall.paper.fifthwallpaper.bean.App

object NetFun {
    private val sharedPreferences by lazy {
        App.getAppContext().getSharedPreferences(
            "Stunning",
            Context.MODE_PRIVATE
        )
    }
    var uu_stunning = ""
        set(value) {
            sharedPreferences.edit().run {
                putString("uu_stunning", value)
                commit()
            }
            field = value
        }
        get() = sharedPreferences.getString("uu_stunning", "").toString()
    var hmd_data = ""
        set(value) {
            sharedPreferences.edit().run {
                putString("hmd_data", value)
                commit()
            }
            field = value
        }
        get() = sharedPreferences.getString("hmd_data", "").toString()
    @SuppressLint("HardwareIds")
    fun getHmdData(context: Context): Map<String, Any> {
        return mapOf<String, Any>(
            //distinct_id
            "bin" to (uu_stunning),
            //client_ts
            "strum" to (System.currentTimeMillis()),
            //device_model
            "conceal" to Build.MODEL,
            //bundle_id
            "depict" to ("com.stunning.wallpapers.artistic.photo.beauty"),
            //os_version
            "dial" to Build.VERSION.RELEASE,
            //gaid
            "priory" to "",
            //android_id
            "protista" to Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID),
            //os
            "ammeter" to "clang",
            //app_version
            "fleck" to getAppVersion(context),//应用的版本
            //brand
            "wakeup" to ((context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).networkOperator)
        )
    }
    private fun getAppVersion(context: Context): String {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return "Version information not available"
    }
}