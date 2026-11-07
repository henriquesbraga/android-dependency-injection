package dev.henriquesbraga.mydiapp

import android.app.Application

class MyApplication : Application() {
    val appContainer by lazy {
        AppCointainer()
    }
}