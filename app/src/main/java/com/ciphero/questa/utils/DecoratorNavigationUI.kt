package com.ciphero.questa.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.fragment.app.Fragment
import com.ciphero.questa.ui.daily.DailyRewardActivity
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.ui.privacy.PrivacyActivity
import com.ciphero.questa.utils.PreferencesManager.checkerPrivacyAccepted

object DecoratorNavigationUI {

    fun hideNavigationBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.setDecorFitsSystemWindows(false)
            activity.window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else
            activity.window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
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

    fun navigateToLink(context: Context, link: String) =
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
}