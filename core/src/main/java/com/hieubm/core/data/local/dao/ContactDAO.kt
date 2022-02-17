package com.hieubm.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.hieubm.core.data.model.Contact

@Dao
interface ContactDAO {

    @Query("SELECT * FROM contacts")
    suspend fun getContacts(): List<Contact>

    @Insert(onConflict = REPLACE)
    suspend fun insertContacts(contacts: List<Contact>)

    @Update
    suspend fun updateContact(contact: Contact)
}