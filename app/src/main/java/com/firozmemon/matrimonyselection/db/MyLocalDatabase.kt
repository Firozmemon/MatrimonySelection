package com.firozmemon.matrimonyselection.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Profiles::class], version = 1)
abstract class MyLocalDatabase : RoomDatabase() {

    abstract fun profilesDao(): ProfilesDao

    companion object {
        private var INSTANCE: MyLocalDatabase? = null
        private const val DB_NAME = "matrimony.db"

        fun getDatabase(context: Context): MyLocalDatabase {
            if (INSTANCE == null) {
                synchronized(MyLocalDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MyLocalDatabase::class.java,
                            DB_NAME
                        ).build()
                    }
                }
            }

            return INSTANCE!!
        }
    }
}