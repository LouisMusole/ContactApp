package com.tacite.contactapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tacite.contactapp.data.local.ContactEntity
import com.tacite.contactapp.ui.screen.ContactsScreen
import com.tacite.contactapp.ui.screen.DetailsScreen
import com.tacite.contactapp.ui.theme.ContactAppTheme
import com.tacite.contactapp.util.Constants
import com.tacite.contactapp.util.Route
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactAppTheme {

                val viewModel : MainViewModel by viewModel()
                val uiState = viewModel.uiState

                var isDropDownMenuExpanded by remember { mutableStateOf(false) }
                var isUpdateDialogShowed by remember { mutableStateOf(false) }

                var newNames by remember { mutableStateOf("") }
                var newTel by remember { mutableStateOf("") }


                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        SearchBar(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            inputField = {
                                SearchBarDefaults.InputField(
                                    query = uiState.query,
                                    onQueryChange = viewModel::onQueryTextChange,
                                    onSearch = {  },
                                    expanded = uiState.isSearchExpanded,
                                    onExpandedChange = { viewModel.onSetSearchExpanded() },
                                    placeholder = { Text("Rechercher") },
                                    leadingIcon = {
                                        if(uiState.isSearchExpanded){
                                            IconButton(onClick = { viewModel.onSetSearchExpanded()}) {
                                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                                            }
                                        }else{
                                            Icon(Icons.Default.Search, contentDescription = null)
                                        }
                                    },
                                    trailingIcon = {
                                        Row {
                                            if(uiState.query.isNotBlank()){
                                                IconButton(onClick = { viewModel.onClearQuery()}) {
                                                    Icon(Icons.Default.Clear, contentDescription = null)
                                                }
                                            }
                                            Column {
                                                IconButton(onClick = { isDropDownMenuExpanded = true }) {
                                                    Icon(Icons.Default.MoreVert, contentDescription = null)
                                                }
                                                DropdownMenu(
                                                    expanded = isDropDownMenuExpanded,
                                                    onDismissRequest = { isDropDownMenuExpanded = false }) {

                                                    DropdownMenuItem(
                                                        text = { Text(text = "Tous") },
                                                        onClick = {
                                                            viewModel.loadAllContacts(true)
                                                            isDropDownMenuExpanded = false
                                                        })

                                                    DropdownMenuItem(
                                                        text = { Text(text = "Favoris") },
                                                        onClick = {
                                                            viewModel.loadAllContacts(false)
                                                            isDropDownMenuExpanded = false
                                                        })

                                                }
                                            }
                                        }
                                    },
                                )
                            },
                            expanded = uiState.isSearchExpanded,
                            onExpandedChange = { viewModel.onSetSearchExpanded() },
                        ) {
                            Column {
                                if(uiState.query.isNotBlank()){
                                    LazyColumn {
                                        items(uiState.filteredContacts){
                                            ListItem(
                                                headlineContent = { Text(text = it.noms) },
                                                supportingContent = { Text(it.tel) },
                                                overlineContent = {

                                                },
                                                leadingContent = {
                                                    Column {
                                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                                            if (it.isFavorite){
                                                                Icon(
                                                                    imageVector = Icons.Default.Star,
                                                                    contentDescription = null,
                                                                    modifier = Modifier.size(25.dp)
                                                                )
                                                            }else{
                                                                Spacer(modifier = Modifier.width(25.dp))
                                                            }
                                                            Spacer(modifier = Modifier.width(4.dp))
                                                            Box(
                                                                modifier =
                                                                Modifier
                                                                    .size(40.dp)
                                                                    .clip(CircleShape)
                                                                    .background(Color(it.color)),
                                                                contentAlignment = Alignment.Center
                                                            ) {
                                                                Text(
                                                                    text = it.noms.first().uppercase(),
                                                                    style = TextStyle(
                                                                        color = MaterialTheme.colorScheme.onPrimary,
                                                                        fontSize = 24.sp
                                                                    )
                                                                )
                                                            }
                                                        }

                                                        DropdownMenu(
                                                            expanded = isDropDownMenuExpanded,
                                                            onDismissRequest = { isDropDownMenuExpanded = false }) {

                                                            DropdownMenuItem(
                                                                text = {
                                                                    Row (verticalAlignment = Alignment.CenterVertically){
                                                                        Icon(imageVector = Icons.Default.Star, contentDescription = null)
                                                                        Spacer(modifier = Modifier.width(4.dp))
                                                                        Text(text = "Ajouter aux favoris")
                                                                    }
                                                                },
                                                                onClick = {
                                                                    viewModel.updateContact(it.copy(isFavorite = true))
                                                                    isDropDownMenuExpanded = false
                                                                })

                                                            DropdownMenuItem(
                                                                text = {
                                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                                                                        Spacer(modifier = Modifier.width(4.dp))
                                                                        Text(text = "Supprimer")
                                                                    }
                                                                },
                                                                onClick = {
                                                                    viewModel.deleteContact(it)
                                                                    isDropDownMenuExpanded = false
                                                                })

                                                        }

                                                    }


                                                },
                                                trailingContent = {
                                                    Row {
                                                        IconButton(onClick = { sendSMS(it.tel) }) {
                                                            Icon(
                                                                imageVector = Icons.Default.Email,
                                                                contentDescription = null )
                                                        }
                                                        IconButton(onClick = {callNumber(it.tel) }) {
                                                            Icon(
                                                                imageVector = Icons.Default.Call,
                                                                contentDescription = null )
                                                        }
                                                    }
                                                },
                                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                                modifier =
                                                Modifier
                                                    .combinedClickable(
                                                        onClick = {},
                                                        onLongClick = {
                                                            isDropDownMenuExpanded = true
                                                        }
                                                    )
                                                    .fillMaxWidth()
                                                    .padding(top = 4.dp, bottom = 4.dp)
                                            )

                                        }
                                    }

                                }
                            }
                        }
                    },
                    floatingActionButton = {
                        LargeFloatingActionButton(onClick = { isUpdateDialogShowed = true }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = Route.Contacts, Modifier.padding(innerPadding)) {
                        composable<Route.Contacts> {
                            ContactsScreen(
                                contacts = uiState.contacts,
                                onDeleteContact = {contact->
                                    viewModel.deleteContact(contact)
                                },
                                onSetContactToFavorite = {contact->
                                    viewModel.updateContact(contact.copy(isFavorite = true))
                                },
                                onSMSAction = {
                                    sendSMS(it.tel)
                                },
                                onCallAction = {
                                    callNumber(it.tel)
                                }
                            )
                        }
                        composable<Route.ContactDetails> {
                            DetailsScreen()
                        }
                    }
                }


                if(isUpdateDialogShowed){

                    Dialog(onDismissRequest = {
                        isUpdateDialogShowed = false
                        newNames = ""
                        newTel = ""
                    }) {
                        Card {
                            Column(
                                Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()) {
                                Text(text = "Ajouter contact")
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = newNames,
                                    onValueChange = {newNames = it},
                                    label = { Text(text = "Noms")}
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = newTel,
                                    onValueChange = {newTel = it},
                                    label = { Text(text = "Numéro de téléphone")}
                                )

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right) {
                                    TextButton(onClick = {
                                        isUpdateDialogShowed = false
                                        newNames = ""
                                        newTel = ""
                                    }) {
                                        Text(text = "Annuler")
                                    }
                                    TextButton(
                                        enabled = newTel.isNotBlank() && newNames.isNotBlank()
                                        ,onClick = {
                                            viewModel.updateContact(ContactEntity(0,newNames,newTel,false,Constants.colors.random()))
                                            newNames = ""
                                            newTel = ""
                                            isUpdateDialogShowed = false

                                    }) {
                                        Text(text = "Enregistrer")
                                    }
                                }
                            }
                        }
                    }
                }


            }
        }
    }


    private fun sendSMS(phoneNumber: String) {
        val smsIntent = Intent(Intent.ACTION_SENDTO)
        smsIntent.data = Uri.parse("smsto:$phoneNumber")

        if (smsIntent.resolveActivity(packageManager) != null) {
            ContextCompat.startActivity(this,smsIntent,null)
        } else {
            Toast.makeText(this, "No SMS app available. Please install a SMS app from the Play Store.", Toast.LENGTH_LONG).show()
            val nintent = Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
            startActivity(nintent)
        }
    }

    private fun callNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)

        } else {
            Toast.makeText(this, "No dialer app available. Please install a dialer app from the Play Store.", Toast.LENGTH_LONG).show()
            val nintent = Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
            startActivity(nintent)
        }
    }

}

