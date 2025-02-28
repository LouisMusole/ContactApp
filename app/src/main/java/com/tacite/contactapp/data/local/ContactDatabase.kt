package com.tacite.contactapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ContactEntity::class],
    version = 3
)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao() : ContactDao
}