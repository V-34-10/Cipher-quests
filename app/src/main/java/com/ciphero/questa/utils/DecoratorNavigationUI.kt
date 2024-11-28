package com.ciphero.questa.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View

object DecoratorNavigationUI {

    private fun checkVersionSystem(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val config = context.resources.configuration
            config.navigation == Configuration.NAVIGATION_NONAV
        } else {
            false
        }
    }

    fun hideNavigationBar(context: Context) {
        if (checkVersionSystem(context)) {
            (context as Activity).window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }
}