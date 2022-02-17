package com.hieubm.core.data.remote.api

import com.hieubm.core.data.remote.response.ContactsResponse
import retrofit2.http.GET

interface ContactService {

    @GET("/v3/9856bfdc-1ec1-4f43-a2fc-95df02ca417a")
    suspend fun getContacts(): ContactsResponse
}