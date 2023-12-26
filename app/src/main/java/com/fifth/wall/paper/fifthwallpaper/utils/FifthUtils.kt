package com.fifth.wall.paper.fifthwallpaper.utils

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout


object FifthUtils {
    fun getDrawableList(context: Context, vararg imageNames: String): List<Drawable> {
        val drawableList = mutableListOf<Drawable>()

        for (imageName in imageNames) {
            val resourceId = context.resources.getIdentifier(
                imageName,
                "drawable",
                context.packageName
            )

            if (resourceId != 0) {
                val drawable = context.resources.getDrawable(resourceId, null)
                drawableList.add(drawable)
            }
        }

        return drawableList
    }

    fun getWallList(context: Context): List<Drawable> {
        val imageNames = arrayOf(
            "ic_36",
            "ic_37",
            "ic_38",
            "ic_39",
            "ic_40",
            "ic_1",
            "ic_2",
            "ic_3",
            "ic_4",
            "ic_5",
            "ic_6",
            "ic_7",
            "ic_8",
            "ic_9",
            "ic_10",
            "ic_41",
            "ic_42",
            "ic_43",
            "ic_44",
            "ic_45",
            "ic_11",
            "ic_12",
            "ic_13",
            "ic_14",
            "ic_15",
            "ic_16",
            "ic_17",
            "ic_18",
            "ic_19",
            "ic_20",
            "ic_21",
            "ic_22",
            "ic_23",
            "ic_24",
            "ic_25",
            "ic_26",
            "ic_27",
            "ic_28",
            "ic_29",
            "ic_30",
            "ic_31",
            "ic_32",
            "ic_33",
            "ic_34",
            "ic_35"
        ) // 替换成你的图片名称
        return getDrawableList(context, *imageNames)
    }

    fun setImageViewFun(
        type: Int,
        activity: AppCompatActivity,
        drawable: Drawable,
        startSet: () -> Unit,
        setCompat: () -> Unit
    ) {
        activity.lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                startSet()
            }
            val bitmap = drawableToBitamp(drawable)
            val wallpaperManager = WallpaperManager.getInstance(activity)
            val whichData = when (type) {
                1 -> WallpaperManager.FLAG_SYSTEM
                2 -> WallpaperManager.FLAG_LOCK
                else -> WallpaperManager.FLAG_SYSTEM
            }
            try {
                wallpaperManager.setBitmap(
                    bitmap,
                    null,
                    false,
                    whichData
                )
                withContext(Dispatchers.Main) {
                    setCompat()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, "Failed to set image", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
        }
    }

    fun setBothWallFun(
        activity: AppCompatActivity,
        drawable: Drawable,
        startSet: () -> Unit,
        setCompat: () -> Unit
    ) {
        activity.lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                startSet()
            }
            val bitmap = drawableToBitamp(drawable)
            val wallpaperManager = WallpaperManager.getInstance(activity)

            try {
                wallpaperManager.setBitmap(
                    bitmap,
                    null,
                    false,
                    WallpaperManager.FLAG_SYSTEM
                )
                wallpaperManager.setBitmap(
                    bitmap,
                    null,
                    false,
                    WallpaperManager.FLAG_LOCK
                )
                withContext(Dispatchers.Main) {
                    setCompat()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, "Failed to set image", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
        }

    }

    fun drawableToBitamp(drawable: Drawable): Bitmap? {
        //声明将要创建的bitmap
        var bitmap: Bitmap? = null
        //获取图片宽度
        val width = drawable.intrinsicWidth
        //获取图片高度
        val height = drawable.intrinsicHeight
        //图片位深，PixelFormat.OPAQUE代表没有透明度，RGB_565就是没有透明度的位深，否则就用ARGB_8888。详细见下面图片编码知识。
        val config =
            if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        //创建一个空的Bitmap
        bitmap = Bitmap.createBitmap(width, height, config)
        //在bitmap上创建一个画布
        val canvas = Canvas(bitmap)
        //设置画布的范围
        drawable.setBounds(0, 0, width, height)
        //将drawable绘制在canvas上
        drawable.draw(canvas)
        return bitmap
    }

    fun shareText(context: Context, text: String, title: String = "Share Text") {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)

        val chooserIntent = Intent.createChooser(shareIntent, title)

        // Verify that the intent will resolve to an activity
        if (shareIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(chooserIntent)
        }
    }

    //跳转外部浏览器
    fun toWeb(context: Context, url: String) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        intent.data = android.net.Uri.parse(url)
        context.startActivity(intent)
    }
}