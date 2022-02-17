package com.hieubm.contact.ui.main.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieubm.core.data.model.Contact
import com.hieubm.core.data.model.Result
import com.hieubm.core.data.model.State
import com.hieubm.core.data.repo.IContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val contactRepository: IContactRepository
) : ViewModel() {
    private var data = mutableListOf<Contact>()
        set(value) {
            field = value
            _contactList.postValue(field)
        }
    private var isOnlyFavourite = false

    private val _contactList = MutableLiveData<List<Contact>?>()
    private val _contactChangedPosition = MutableLiveData<Int>()
    private val _state = MutableLiveData<State<Any>>()

    val contactList: LiveData<List<Contact>?> = _contactList
    val contactChangedPosition: LiveData<Int> = _contactChangedPosition
    val state: LiveData<State<Any>> = _state

    init {
        getContacts()
    }

    private fun getContacts() {
        viewModelScope.launch {
            _state.postValue(State.Loading(true))
            val result = contactRepository.getContacts()
            _state.postValue(State.Loading(false))
            when (result) {
                is Result.Success -> data = result.data.toMutableList()

                is Result.Failure -> _state.postValue(State.Failure(result.exception))
            }
        }
    }

    fun getContacts(isOnlyFavourite: Boolean) {
        viewModelScope.launch {
            _state.postValue(State.Loading(true))
            this@ContactsViewModel.isOnlyFavourite = isOnlyFavourite
            val contactList = when {
                isOnlyFavourite -> withContext(Dispatchers.Default) {
                    data.filter { it.isFavourite }
                }
                else -> data
            }
            _state.postValue(State.Loading(false))
            _contactList.postValue(contactList)
        }
    }

    fun updateCategoryFavourite(contact: Contact) {
        viewModelScope.launch {
            val position = withContext(Dispatchers.Default) { data.indexOf(contact) }
            if (position == -1) {   // Contact not found
                return@launch
            }

            contactRepository.updateContact(data[position].apply { isFavourite = !isFavourite })
            if (isOnlyFavourite) {
                // Remove contact from the currently displayed favorites list
                val contactList = _contactList.value?.toMutableList()?.apply {
                    withContext(Dispatchers.Default) {
                        indexOf(contact).takeIf { it != -1 }?.let { removeAt(it) }
                    }
                }
                _contactList.postValue(contactList)
                return@launch
            }

            // Notify contact has changed in the list of all contact is currently displayed
            _contactChangedPosition.postValue(position)
        }
    }
}