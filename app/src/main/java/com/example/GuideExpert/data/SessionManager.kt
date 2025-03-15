package com.example.GuideExpert.data

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.GuideExpert.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SessionManager (val context: Context) {
   // private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

   /* companion object {
        const val USER_TOKEN = "user_token"
    }
*/
    /**
     * Function to save auth token
     */
 /*   fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }
*/
    /**
     * Function to fetch auth token
     */
/*    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
*/
//----------

    private suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { settings ->
            settings[key] = value
        }
    }

    private fun <T> read(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        context.dataStore.data.map { settings ->
            settings[key] ?: defaultValue
        }

    private val AUTH_TOKEN = stringPreferencesKey("auth_token")
    suspend fun setAuthToken(value: String) = save(AUTH_TOKEN, value)

    fun getAuthToken(): Flow<String> = read(AUTH_TOKEN, "")


    private val PROFILE_ID = intPreferencesKey("profile_id")
    suspend fun setProfileId(value: Int) = save(PROFILE_ID, value)

    fun getProfileId(): Flow<Int> = read(PROFILE_ID, 0)

    private val PROFILE_TIME = intPreferencesKey("profile_time")
    suspend fun setProfileTime(value: Int) = save(PROFILE_TIME, value)

    fun getProfileTime(): Flow<Int> = read(PROFILE_TIME, 0)
}