package com.hieubm.core.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.hieubm.core.data.model.Company

class Converters {
    private val gson: Gson by lazy { Gson() }

    @TypeConverter
    fun Company.toJson(): String = gson.toJson(this)

    @TypeConverter
    fun fromCompanyJson(companyJson: String): Company = gson.fromJson(companyJson, Company::class.java)
}