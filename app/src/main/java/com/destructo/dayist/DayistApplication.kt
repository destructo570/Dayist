package com.destructo.dayist

import android.app.Application
import timber.log.Timber

class DayistApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}