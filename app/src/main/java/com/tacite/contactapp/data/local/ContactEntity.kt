package com.tacite.contactapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val noms : String,
    val tel : String,
    val isFavorite : Boolean,
    val color : Long
)
