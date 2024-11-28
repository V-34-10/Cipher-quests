package com.ciphero.questa.ui.daily

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivityDailyRewardBinding
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.utils.DecoratorNavigationUI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyRewardActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDailyRewardBinding.inflate(layoutInflater) }
    private val preferences by lazy { getSharedPreferences("CipherQuestsPref", Context.MODE_PRIVATE) }
    private val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)

        if (checkRewardToday()) {
            intentToMenu()
            return
        }

        checkDailyReward()
        loadRewardProgress()
        dailyClickDaysButtons()
    }

    private fun dailyClickDaysButtons() {
        binding.buttonClaimDaily.setOnClickListener {
            val currentDay = preferences.getInt("currentDay", 1)
            if (currentDay in 1..3 && !preferences.getBoolean("day${currentDay}Rewarded", false)) {
                collectReward(currentDay, getButtonByDay(currentDay))
            }
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

    private fun checkRewardToday(): Boolean =
        preferences.getString("lastRewardShownDate", null) == date.format(Date())

    private fun markRewardShownToday() {
        with(preferences.edit()) {
            putString(
                "lastRewardShownDate",
                date.format(Date())
            )
            apply()
        }
    }

    private fun checkDailyReward() {
        val lastRewardDay = preferences.getString("lastRewardDay", null)
        val currentDate = date.format(Date())

        if (lastRewardDay == null || lastRewardDay != currentDate) {
            val currentDay = preferences.getInt("currentDay", 1)
            if (currentDay > 3) {
                resetRewardProgress()
            } else {
                with(preferences.edit()) {
                    putString("lastRewardDay", currentDate)
                    apply()
                }
            }
        }
    }

    private fun resetRewardProgress() {
        with(preferences.edit()) {
            putInt("currentDay", 1)
            putString("lastRewardDay", date.format(Date()))
            putBoolean("day1Rewarded", false)
            putBoolean("day2Rewarded", false)
            putBoolean("day3Rewarded", false)
            commit()
        }
    }

    private fun loadRewardProgress() {
        val currentDay = preferences.getInt("currentDay", 1)
        if (currentDay > 3) {
            resetRewardProgress()
        } else {
            updateRewardUI(currentDay)
        }
    }

    private fun updateRewardUI(currentDay: Int) {
        updateButtonState(binding.btnFirstDay, 1, currentDay)
        updateButtonState(binding.btnSecondDay, 2, currentDay)
        updateButtonState(binding.btnThreeDay, 3, currentDay)
    }

    private fun updateButtonState(button: View, day: Int, currentDay: Int) {
        val isRewarded = preferences.getBoolean("day${day}Rewarded", false)
        button.isEnabled = day == currentDay && !isRewarded
        button.alpha = if (day <= currentDay) 1f else 0.5f
        Log.d("DailyActivity", "Day $day isRewarded: $isRewarded")
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

        val currentBalance = preferences.getString(
            "balanceScores",
            this.getString(R.string.default_balance)
        )
        var balance = currentBalance.toString().filter { it.isDigit() }.toIntOrNull() ?: 0
        balance += rewardAmount
        with(preferences.edit()) {
            putString("balanceScores", balance.toString())
            putBoolean("day${day}Rewarded", true)
            apply()
        }
        markRewardShownToday()
        updateButtonState(button, day, day)

        val nextDay = day + 1
        if (nextDay <= 3) {
            with(preferences.edit()) {
                putInt("currentDay", nextDay)
                apply()
            }
        } else {
            with(preferences.edit()) {
                putInt("currentDay", 1)
                apply()
            }
            resetRewardProgress()
        }

        intentToMenu()
    }

    private fun intentToMenu() {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
        intentToMenu()
    }
}