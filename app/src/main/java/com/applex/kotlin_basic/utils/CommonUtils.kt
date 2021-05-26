package com.applex.kotlin_basic.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import android.widget.Toast
import kotlin.math.roundToInt

/**
 * Created by Prajata on 25 May 2021
 */
class CommonUtils(private val context: Context) {

    fun showToast(data: String) = Toast.makeText(context, data, Toast.LENGTH_SHORT).show()

    fun checkInternetConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

    fun settingImage(id: Int, view: ImageView) {
        val displayWidth = context.resources.displayMetrics.widthPixels
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        BitmapFactory.decodeResource(context.resources, id, options)

        val width = options.outWidth
        if (width > displayWidth) {
            options.inSampleSize = (width.toFloat() / displayWidth.toFloat()).roundToInt()
        }
        options.inJustDecodeBounds = false

        val scaledBitmap = BitmapFactory.decodeResource(context.resources, id, options)
        view.setImageBitmap(scaledBitmap)
    }
}