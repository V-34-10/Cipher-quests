package com.ciphero.questa.ui.daily

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivityDailyRewardBinding
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.utils.DecoratorNavigationUI
import com.ciphero.questa.utils.DecoratorNavigationUI.navigateToActivity
import com.ciphero.questa.utils.PreferencesManager.getCurrentDayRewarded
import com.ciphero.questa.utils.PreferencesManager.getLastRewardDay
import com.ciphero.questa.utils.PreferencesManager.getLastRewardShownDate
import com.ciphero.questa.utils.PreferencesManager.getRewardCurrentDay
import com.ciphero.questa.utils.PreferencesManager.getScoresBalance
import com.ciphero.questa.utils.PreferencesManager.resetRewardProgress
import com.ciphero.questa.utils.PreferencesManager.setCurrentDayRewarded
import com.ciphero.questa.utils.PreferencesManager.setLastRewardDay
import com.ciphero.questa.utils.PreferencesManager.setLastRewardShownDate
import com.ciphero.questa.utils.PreferencesManager.setRewardCurrentDay
import com.ciphero.questa.utils.PreferencesManager.setScoresBalance
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyRewardActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDailyRewardBinding.inflate(layoutInflater) }
    private val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)

        if (checkRewardToday()) {
            navigateToActivity(MenuActivity::class.java, this)
            return
        }

        checkDailyReward()
        loadRewardProgress()
        dailyClickDaysButtons()
    }

    private fun dailyClickDaysButtons() {
        binding.buttonClaimDaily.setOnClickListener {
            val currentDay = getRewardCurrentDay(this)
            if (currentDay in 1..3 && !getCurrentDayRewarded(this, currentDay)) collectReward(
                currentDay,
                getButtonByDay(currentDay)
            )
        }
    }

    private fun getButtonByDay(day: Int): View {
        return when (day) {
            1 -> binding.btnFirstDay
            2 -> binding.btnSecondDay
            3 -> binding.btnThreeDay
            else -> throw IllegalArgumentException("Invalid day: $day")
        }
    }

    private fun checkRewardToday(): Boolean = getLastRewardShownDate(this) == date.format(Date())

    private fun markRewardShownToday() = setLastRewardShownDate(this, date)

    private fun checkDailyReward() {
        val lastRewardDay = getLastRewardDay(this)
        val currentDate = date.format(Date())

        if (lastRewardDay == null || lastRewardDay != currentDate) {
            if (getRewardCurrentDay(this) > 3) {
                resetRewardProgress(this, date)
            } else {
                setLastRewardDay(this, currentDate)
            }
        }
    }

    private fun loadRewardProgress() {
        val currentDay = getRewardCurrentDay(this)
        if (currentDay > 3) {
            resetRewardProgress(this, date)
        } else {
            updateRewardUI(currentDay)
        }
    }

    private fun updateRewardUI(currentDay: Int) {
        updateButtonState(binding.btnFirstDay, 1, currentDay)
        updateButtonState(binding.btnSecondDay, 2, currentDay)
        updateButtonState(binding.btnThreeDay, 3, currentDay)
    }

    @SuppressLint("TimberArgCount")
    private fun updateButtonState(button: View, day: Int, currentDay: Int) {
        val isRewarded = getCurrentDayRewarded(this, day)
        button.isEnabled = day == currentDay && !isRewarded
        button.alpha = if (day <= currentDay) 1f else 0.5f
        Timber.tag("DailyActivity").d("%s%s", "%s isRewarded: ", "Day %s", day, isRewarded)
        if (isRewarded) {
            when (day) {
                1 -> button.setBackgroundResource(R.drawable.basic_day_1_done)
                2 -> button.setBackgroundResource(R.drawable.basic_day_2_done)
                3 -> button.setBackgroundResource(R.drawable.basic_day_3_done)
            }
        } else {
            when (day) {
                1 -> button.setBackgroundResource(R.drawable.basic_day_1)
                2 -> button.setBackgroundResource(R.drawable.basic_day_2)
                3 -> button.setBackgroundResource(R.drawable.basic_day_3)
            }
        }
    }

    private fun collectReward(day: Int, button: View) {
        val rewardAmount = when (day) {
            1 -> 100
            2 -> 200
            3 -> 500
            else -> 0
        }

        var balance = getScoresBalance(this).toString().filter { it.isDigit() }.toIntOrNull() ?: 0
        balance += rewardAmount

        setScoresBalance(this, balance.toString())
        setCurrentDayRewarded(this, day)
        markRewardShownToday()
        updateButtonState(button, day, day)

        val nextDay = day + 1
        if (nextDay <= 3) {
            setRewardCurrentDay(this, nextDay)
        } else {
            setRewardCurrentDay(this, 1)
            resetRewardProgress(this, date)
        }

        navigateToActivity(MenuActivity::class.java, this)
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
        navigateToActivity(MenuActivity::class.java, this)
    }
}