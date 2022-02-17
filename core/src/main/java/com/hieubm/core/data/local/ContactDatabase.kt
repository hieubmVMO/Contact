package com.hieubm.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hieubm.core.data.local.dao.ContactDAO
import com.hieubm.core.data.model.Contact
import com.hieubm.core.util.Converters
import com.hieubm.core.util.DATABASE_VERSION

@Database(
    entities = [Contact::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDAO(): ContactDAO
}