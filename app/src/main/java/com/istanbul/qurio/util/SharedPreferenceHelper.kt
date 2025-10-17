package com.istanbul.qurio.util

import android.content.Context
import androidx.core.content.edit

object SharedPreferenceHelper {

    private const val QURIO_PREF_FILE_NAME = "qurio_App_Preference"
    private const val OPEN_APP_FIRST_TIME  = "open_app_first_time"


    fun saveIsFirstTimeOpenApp(value : Boolean, context : Context){
        val sharedPreference = context.getSharedPreferences(QURIO_PREF_FILE_NAME,Context.MODE_PRIVATE)
        sharedPreference.edit {
            putBoolean(OPEN_APP_FIRST_TIME, value)
        }
    }

    fun getIsFirstTimeOpenApp(context : Context) : Boolean {
        val sharedPreference = context.getSharedPreferences(QURIO_PREF_FILE_NAME,Context.MODE_PRIVATE)
        return sharedPreference.getBoolean(OPEN_APP_FIRST_TIME,true)
    }
}