package com.applex.kotlin_basic.utils.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.applex.kotlin_basic.utils.Constants

class PreferenceManager @SuppressLint("CommitPrefEdits") constructor(context: Context?) {

    private var preferences : SharedPreferences?
    private var editor : SharedPreferences.Editor?
    private val constants = Constants()

    init {
        preferences = context?.getSharedPreferences(constants.PREFERENCE_NAME, 0)
        editor = preferences?.edit()
    }

    fun setSortOrder(order : Int) {
        editor?.putInt(constants.ORDER_BY, order)
        editor?.apply()
    }

    fun getSortOrder() = preferences?.getInt(constants.ORDER_BY, 0)
}