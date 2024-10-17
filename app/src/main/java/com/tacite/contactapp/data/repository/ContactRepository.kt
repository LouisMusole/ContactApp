package com.tacite.contactapp.data.repository

import com.tacite.contactapp.data.local.ContactDao
import com.tacite.contactapp.data.local.ContactEntity

class ContactRepository(
    private val dao: ContactDao
) {
    suspend fun update(contact : ContactEntity) = dao.updateContact(contact)

    suspend fun delete(contact: ContactEntity) = dao.deleteContact(contact)

    fun getContacts() = dao.getContacts()

    fun getContacts(query : String) = dao.getContacts(query)
}