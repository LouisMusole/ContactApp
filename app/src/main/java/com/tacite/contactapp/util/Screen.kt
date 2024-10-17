package com.tacite.contactapp.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen<T>( val route: T, val text : String, val icon: ImageVector) {
    data object Contacts : Screen<Route.Contacts>(Route.Contacts, "Contacts", Icons.Filled.Person)
    data object FavoriteContacts : Screen<Route.Favorites>(Route.Favorites, "Favoris", Icons.Filled.Favorite)
}