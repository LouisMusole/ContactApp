package com.tacite.contactapp.ui

import com.tacite.contactapp.data.local.ContactEntity

data class UiState(
    val query : String = "",
    val isSearchExpanded : Boolean = false,
    val filteredContacts : List<ContactEntity> = emptyList(),
    val contacts : List<ContactEntity> = emptyList()
)
