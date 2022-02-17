package com.hieubm.contact.injection.module

import android.app.Application
import androidx.room.Room
import com.hieubm.contact.util.DATABASE_NAME
import com.hieubm.core.data.local.ContactDatabase
import com.hieubm.core.data.local.dao.ContactDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideContactDatabase(application: Application): ContactDatabase =
        Room.databaseBuilder(application, ContactDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideIconDAO(contactDatabase: ContactDatabase): ContactDAO = contactDatabase.contactDAO()
}