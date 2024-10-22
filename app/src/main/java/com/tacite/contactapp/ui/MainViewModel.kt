package com.tacite.contactapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tacite.contactapp.data.local.ContactEntity
import com.tacite.contactapp.data.repository.ContactRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ContactRepository) : ViewModel() {

    var uiState by mutableStateOf(UiState())

    init {
        loadAllContacts(true)
    }

    fun updateContact(contact : ContactEntity){
        viewModelScope.launch {
            repository.update(contact)
        }
    }

    fun deleteContact(contact : ContactEntity){
        viewModelScope.launch {
            repository.delete(contact)
        }
    }

    fun loadAllContacts(all :Boolean){
        viewModelScope.launch {
            repository.getContacts().collect{ it ->
                uiState = if(all){
                    uiState.copy(contacts = it)
                }else{
                    uiState.copy(contacts = it.filter {contact-> contact.isFavorite })
                }
            }
        }
    }

    private fun filteredContacts(query :String){
        viewModelScope.launch {
            repository.getContacts(query).collect{
                delay(500)
                uiState = uiState.copy(filteredContacts = it)
            }
        }
    }

    fun onQueryTextChange(text: String) {
        uiState = uiState.copy(query = text)
        filteredContacts(text)
    }

    fun onClearQuery(){
        uiState = uiState.copy(query = "")
    }

    fun onSetSearchExpanded() {
        uiState = uiState.copy(isSearchExpanded = !uiState.isSearchExpanded)
    }

}