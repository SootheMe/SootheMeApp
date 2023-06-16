package com.example.sootheme.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPref private constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun getUserName(): Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY] ?: DEFAULT_VALUE
    }

    suspend fun saveUserName(name: String) {
        dataStore.edit {
            it[USER_NAME_KEY] = name
        }
    }

    /*fun getUserEmail(): Flow<String> = dataStore.data.map {
        it[USER_EMAIL_KEY] ?: DEFAULT_VALUE
    }*/

    /*suspend fun saveUserEmail(email: String) {
        dataStore.edit {
            it[USER_EMAIL_KEY] = email
        }
    }*/

    fun getUserToken(): Flow<String> = dataStore.data.map {
        it[USER_TOKEN_KEY] ?: DEFAULT_VALUE
    }

    suspend fun saveUserToken(token: String) {
        dataStore.edit {
            it[USER_TOKEN_KEY] = token
        }
    }

    fun getFirstTime(): Flow<Boolean> = dataStore.data.map {
        it[FIRST_TIME_KEY] ?: true
    }

    suspend fun login() {
        dataStore.edit {
            it[FIRST_TIME_KEY] = false
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it[FIRST_TIME_KEY] = true
            it[USER_TOKEN_KEY] = ""
            it[USER_NAME_KEY] = ""
        }
    }

    companion object {
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
        private val FIRST_TIME_KEY = booleanPreferencesKey("first_time")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")

        @Volatile
        private var INSTANCE: SettingPref? = null
        private const val DEFAULT_VALUE = ""

        fun getInstance(dataStore: DataStore<Preferences>): SettingPref {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPref(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}