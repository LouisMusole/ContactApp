package com.tacite.contactapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Upsert
    suspend fun updateContact(contact : ContactEntity)

    @Delete
    suspend fun deleteContact(contact: ContactEntity)

    @Query("SELECT * FROM contactentity ORDER BY noms")
    fun getContacts() : Flow<List<ContactEntity>>

    @Query(
        """
            SELECT * 
            FROM contactentity 
            WHERE LOWER(noms) LIKE '%' || LOWER(:query) || '%' 
            ORDER BY noms
        """
    )
    fun getContacts(query : String): Flow<List<ContactEntity>>
}