package com.hieubm.core.data.remote.response

import com.google.gson.annotations.SerializedName
import com.hieubm.core.data.model.Contact

data class ContactsResponse(
    @SerializedName("contacts")
    val contactList: List<Contact>
)