package com.nytimes.android.ramonaharrison.helpers

import android.app.Activity
import android.content.Context


class StorageHelper {

    private val NEXT_SHORT_CODE = "next_short_code"
    private val KEY_PREFIX = "anchor;"
    private val INITIAL_SHORT_CODE = 142

    /** Gets a new short code that can be used to store the anchor ID.  */
    fun nextShortCode(activity: Activity): Int {
        val sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE)
        val shortCode = sharedPrefs.getInt(NEXT_SHORT_CODE, INITIAL_SHORT_CODE)
        // Increment and update the value in sharedPrefs, so the next code retrieved will be unused.
        sharedPrefs.edit().putInt(NEXT_SHORT_CODE, shortCode + 1)
            .apply()
        return shortCode
    }

    /** Stores the cloud anchor ID in the activity's SharedPrefernces.  */
    fun storeUsingShortCode(activity: Activity, shortCode: Int, cloudAnchorId: String) {
        val sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE)
        sharedPrefs.edit().putString(KEY_PREFIX + shortCode, cloudAnchorId).apply()
    }

    /**
     * Retrieves the cloud anchor ID using a short code. Returns an empty string if a cloud anchor ID
     * was not stored for this short code.
     */
    fun getCloudAnchorID(activity: Activity, shortCode: Int): String {
        val sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPrefs.getString(KEY_PREFIX + shortCode, "")
    }
}