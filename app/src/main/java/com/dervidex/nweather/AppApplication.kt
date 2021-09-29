package com.dervidex.nweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class AppApplication : Application(){
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val Token = "XPxOmhHnrA9Exnzw"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}