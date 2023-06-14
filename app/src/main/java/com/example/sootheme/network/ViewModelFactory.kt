package com.example.sootheme.network

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sootheme.data.UserRepository

class ViewModelFactory(private val repo: UserRepository) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(com.example.sootheme.model.LoginViewModel::class.java) -> com.example.sootheme.model.LoginViewModel(repo) as T
                modelClass.isAssignableFrom(com.example.sootheme.model.RegisterViewModel::class.java) -> com.example.sootheme.model.RegisterViewModel(repo) as T
                modelClass.isAssignableFrom(com.example.sootheme.model.MainViewModel::class.java) -> com.example.sootheme.model.MainViewModel(repo) as T
                modelClass.isAssignableFrom(com.example.sootheme.model.StoryViewModel::class.java) -> com.example.sootheme.model.StoryViewModel(repo) as T
                modelClass.isAssignableFrom(com.example.sootheme.model.DetailStoryViewModel::class.java) -> com.example.sootheme.model.DetailStoryViewModel(repo) as T
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }

        companion object {
            @Volatile
            private var instance: ViewModelFactory? = null

            @JvmStatic
            fun getInstance(context : Context): ViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideRepository(context))
                }.also { instance = it }
        }
}