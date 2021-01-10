package com.firozmemon.matrimonyselection.db

import androidx.room.*
import com.firozmemon.matrimonyselection.db.Profiles.Companion.PROFILE_DECISION
import com.firozmemon.matrimonyselection.db.Profiles.Companion.PROFILE_ID
import com.firozmemon.matrimonyselection.db.Profiles.Companion.TABLE_NAME

@Dao
interface ProfilesDao {

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $PROFILE_ID DESC")
    fun getAllProfiles(): List<Profiles>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(profiles: Profiles): Long

    @Query("UPDATE $TABLE_NAME SET $PROFILE_DECISION = :decision WHERE $PROFILE_ID = :pId")
    fun updateDecision(pId: Long, decision: Int)

    @Update
    fun updateProfiles(profiles: Profiles)

}