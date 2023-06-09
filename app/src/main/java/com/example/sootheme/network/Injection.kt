package com.example.sootheme.network

import android.content.Context
import com.example.sootheme.data.UserRepository
import com.example.sootheme.data.datastore.SettingPref
import com.example.sootheme.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val pref = SettingPref.getInstance(context.dataStore)
        return UserRepository.getInstance(apiService, pref)
    }
}