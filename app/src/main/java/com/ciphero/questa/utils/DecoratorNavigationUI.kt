package com.ciphero.questa.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.fragment.app.Fragment
import com.ciphero.questa.R
import com.ciphero.questa.ui.daily.DailyRewardActivity
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.ui.privacy.PrivacyActivity
import com.ciphero.questa.utils.PreferencesManager.checkerPrivacyAccepted

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

    fun observeStartActivityInSplash(context: Context) = if (checkerPrivacyAccepted(context)) {
        context.startActivity(Intent(context, DailyRewardActivity::class.java))
    } else {
        context.startActivity(Intent(context, PrivacyActivity::class.java))
    }

    fun <T> navigateToActivity(activityClass: Class<T>, activity: Activity) {
        activity.startActivity(Intent(activity, activityClass))
        activity.finish()
    }

    fun navigateToWithFragment(fragment: Fragment) {
        fragment.startActivity(Intent(fragment.requireContext(), MenuActivity::class.java))
        fragment.requireActivity().finish()
    }

    fun navigateToPrivacyLink(context: Context) = context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.link_privacy))))
}