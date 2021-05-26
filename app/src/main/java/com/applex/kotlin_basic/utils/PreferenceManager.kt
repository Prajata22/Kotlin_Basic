package com.applex.kotlin_basic.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Prajata on 25 May 2021
 */
class PreferenceManager(context: Context?) {

    private var preferences: SharedPreferences? =
        context?.getSharedPreferences(Constants.PREFERENCE_NAME, 0)
    private var editor: SharedPreferences.Editor? = preferences?.edit()

    fun setSortOrder(order: Int) {
        editor?.putInt(Constants.ORDER_BY, order)
        editor?.apply()
    }

    fun getSortOrder() = preferences?.getInt(Constants.ORDER_BY, 0)
}