package com.tacite.contactapp.util

import kotlinx.serialization.Serializable

object Route {
    @Serializable
    object Contacts

    @Serializable
    object Favorites

    @Serializable
    data class ContactDetails(
        val noms : String,
        val tel : String,
        val email : String,
        val isFavorite : Boolean)
}