package com.hieubm.contact

import android.app.Application
import com.hieubm.contact.injection.component.AppComponent
import com.hieubm.contact.injection.component.DaggerAppComponent
import com.hieubm.contact.util.DebugTree
import timber.log.Timber

class ContactApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initComponents()
    }

    private fun initComponents() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        appComponent = DaggerAppComponent.factory().create(this)
    }
}