package com.tusbus.mymvvm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @name MyMVVM
 * @classPackage com.tusbus.mymvvm
 * @author lycc1
 * @time 2020/12/10 17:24
 * @class describe
 * @class 中文描述
 */
@HiltAndroidApp
class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}