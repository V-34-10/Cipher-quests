package com.ciphero.questa.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.edit
import com.ciphero.questa.R
import java.text.SimpleDateFormat
import java.util.Date

object PreferencesManager {
    fun checkerPrivacyAccepted(context: Context): Boolean =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .getBoolean(context.getString(R.string.key_privacy_pref), false)

    fun setPrivacyAccepted(context: Context) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .edit { putBoolean(context.getString(R.string.key_privacy_pref), true).apply() }

    fun resetScoresSettings(context: Context) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE).edit()
            .putString(
                context.getString(R.string.key_balance_scores_pref),
                context.getString(R.string.default_balance)
            ).apply()

    fun setScoresBalance(context: Context, balance: String) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE).edit()
            .putString(
                context.getString(R.string.key_balance_scores_pref),
                balance
            ).apply()

    fun getScoresBalance(context: Context) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .getString(
                context.getString(R.string.key_balance_scores_pref),
                context.getString(R.string.default_balance)
            )

    fun setNameGameMenu(context: Context, gameNameResId: Int) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .edit {
                putString(
                    context.getString(R.string.key_name_game_pref),
                    context.getString(gameNameResId)
                ).apply()
            }

    fun setNameGameMenu(context: Context, gameNameResId: String) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .edit {
                putString(
                    context.getString(R.string.key_name_game_pref),
                    gameNameResId
                ).apply()
            }

    fun getNameGameMenu(context: Context) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .getString(
                context.getString(R.string.key_name_game_pref),
                context.getString(R.string.first_game_btn)
            )


    fun getRewardCurrentDay(context: Context) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .getInt("currentDay", 1)

    fun setRewardCurrentDay(context: Context, day: Int) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .edit().putInt("currentDay", day).apply()

    fun getLastRewardShownDate(context: Context) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .getString("lastRewardShownDate", null)

    fun setLastRewardShownDate(context: Context, date: SimpleDateFormat) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .edit().putString("lastRewardShownDate", date.format(Date())).apply()

    fun getLastRewardDay(context: Context) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .getString("lastRewardDay", null)

    fun setLastRewardDay(context: Context, currentDate: String) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE).edit()
            .putString("lastRewardDay", currentDate).apply()


    fun resetRewardProgress(context: Context, date: SimpleDateFormat) {
        with(
            context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
                .edit()
        ) {
            putInt("currentDay", 1)
            putString("lastRewardDay", date.format(Date()))
            putBoolean("day1Rewarded", false)
            putBoolean("day2Rewarded", false)
            putBoolean("day3Rewarded", false)
            commit()
        }
    }

    fun getCurrentDayRewarded(context: Context, currentDay: Int): Boolean =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .getBoolean("day${currentDay}Rewarded", false)

    fun setCurrentDayRewarded(context: Context, currentDay: Int) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE).edit()
            .putBoolean("day${currentDay}Rewarded", true).apply()


    fun setStatusOfferwall(context: Context, status: Boolean) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE).edit()
            .putBoolean("StatusOffer", status).apply()

    fun getStatusOfferwall(context: Context) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .getBoolean("StatusOffer", false)
}