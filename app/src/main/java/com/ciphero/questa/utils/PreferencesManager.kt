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
            .getInt(context.getString(R.string.key_current_day_pref), 1)

    fun setRewardCurrentDay(context: Context, day: Int) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .edit().putInt(context.getString(R.string.key_current_day_pref), day).apply()

    fun getLastRewardShownDate(context: Context) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .getString(context.getString(R.string.key_last_reward_shown_date_pref), null)

    fun setLastRewardShownDate(context: Context, date: SimpleDateFormat) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .edit().putString(
                context.getString(R.string.key_last_reward_shown_date_pref),
                date.format(Date())
            ).apply()

    fun getLastRewardDay(context: Context) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .getString(context.getString(R.string.key_last_reward_day_pref), null)

    fun setLastRewardDay(context: Context, currentDate: String) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE).edit()
            .putString(context.getString(R.string.key_last_reward_day_pref), currentDate).apply()


    fun resetRewardProgress(context: Context, date: SimpleDateFormat) {
        with(
            context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
                .edit()
        ) {
            putInt(context.getString(R.string.key_current_day_pref), 1)
            putString(context.getString(R.string.key_last_reward_day_pref), date.format(Date()))
            putBoolean(context.getString(R.string.key_day1_rewarded_pref), false)
            putBoolean(context.getString(R.string.key_day2_rewarded_pref), false)
            putBoolean(context.getString(R.string.key_day3_rewarded_pref), false)
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
            .putBoolean(context.getString(R.string.key_status_offer_pref), status).apply()

    fun getStatusOfferwall(context: Context) =
        context.getSharedPreferences(context.getString(R.string.app_name_pref), MODE_PRIVATE)
            .getBoolean(context.getString(R.string.key_status_offer_pref), false)
}