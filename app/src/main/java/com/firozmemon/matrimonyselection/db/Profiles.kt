package com.firozmemon.matrimonyselection.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.firozmemon.matrimonyselection.db.Profiles.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Profiles(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PROFILE_ID) var id: Long = 0,
    @ColumnInfo(name = PROFILE_NAME) var name: String,
    @ColumnInfo(name = PROFILE_AGE) var age: Int,
    @ColumnInfo(name = PROFILE_IMAGE_URL) var imgUrl: String,
    @ColumnInfo(name = PROFILE_ADDRESS) var address: String,
    @ColumnInfo(name = PROFILE_DECISION) var decision: Int = -1
) {

    companion object {
        const val TABLE_NAME = "profile"
        const val PROFILE_ID = "pId"
        const val PROFILE_NAME = "name"
        const val PROFILE_AGE = "age"
        const val PROFILE_IMAGE_URL = "imgUrl"
        const val PROFILE_ADDRESS = "address"
        const val PROFILE_DECISION = "decision"
    }

}