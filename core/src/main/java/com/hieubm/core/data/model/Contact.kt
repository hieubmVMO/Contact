package com.hieubm.core.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey
    @ColumnInfo(name = "contact_id")
    @SerializedName("id")
    val contactId: Int,

    val name: String?,

    @ColumnInfo(name = "user_name")
    @SerializedName("username")
    val userName: String?,

    val email: String?,

    @ColumnInfo(name = "picture_url")
    val pictureUrl: String?,

    val company: Company,

    @ColumnInfo(name = "is_favourite")
    var isFavourite: Boolean = false
) : Parcelable

@Parcelize
data class Company(
    val name: String?,

    val catchPhrase: String?,

    val bs: String?
) : Parcelable