package com.hieubm.core.data.repo

import com.hieubm.core.data.local.dao.ContactDAO
import com.hieubm.core.data.model.Contact
import com.hieubm.core.data.model.Result
import com.hieubm.core.data.remote.api.ContactService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

interface IContactRepository {

    suspend fun getContacts(): Result<List<Contact>>

    suspend fun updateContact(contact: Contact): Result<Unit>
}

@Singleton
class ContactRepository @Inject constructor(
    private val contactService: ContactService,

    private val contactDAO: ContactDAO,

    @Named("DispatcherIO")
    private val dispatcherIO: CoroutineDispatcher
) : IContactRepository {

    override suspend fun getContacts(): Result<List<Contact>> =
        withContext(dispatcherIO) {
            var contactList: List<Contact>
            try {
                val contactsResponse = contactService.getContacts()
                contactList = contactsResponse.contactList
                contactDAO.insertContacts(contactList)      // Update contact data from API into DB
            } catch (e: IOException) {
                Timber.e(e)
                contactList = contactDAO.getContacts()      // Get contact data cached from DB
            } catch (e: Exception) {
                Timber.e(e)
                return@withContext Result.Failure(e)
            }
            Result.Success(contactList)
        }

    override suspend fun updateContact(contact: Contact): Result<Unit> = withContext(dispatcherIO) {
        try {
            contactDAO.updateContact(contact)
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Failure(e)
        }
    }
}