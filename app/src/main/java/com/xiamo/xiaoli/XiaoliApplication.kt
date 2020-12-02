package com.xiamo.xiaoli

import android.app.Application

class XiaoliApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: XiaoliApplication? = null
            private set
    }
}