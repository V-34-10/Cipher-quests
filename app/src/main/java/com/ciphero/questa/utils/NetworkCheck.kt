package com.ciphero.questa.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

object NetworkCheck {

    @SuppressLint("ObsoleteSdkInt")
    fun checkAccessInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && (activeNetworkInfo.isConnected || activeNetworkInfo.isConnectedOrConnecting)
    }
}