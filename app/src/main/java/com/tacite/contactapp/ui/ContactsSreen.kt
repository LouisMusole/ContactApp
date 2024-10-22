package com.tacite.contactapp.ui



import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tacite.contactapp.data.local.ContactEntity
import com.tacite.contactapp.util.Constants.sendMail


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsScreen(
    contacts : List<ContactEntity>,
    onDeleteContact : (ContactEntity) -> Unit,
    onUpdateContact : (ContactEntity) -> Unit,
    onSetContactToFavorite : (ContactEntity) -> Unit,
    onSMSAction : (ContactEntity) -> Unit,
    onCallAction : (ContactEntity) -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        val handler = LocalUriHandler.current

        val context = LocalContext.current

        val groupedList = contacts.groupBy { it.noms.first() }

        LazyColumn {

            groupedList.forEach { (initial, contactsForInitial) ->

                stickyHeader {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                            .size(70.dp, 60.dp)
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(text = initial.toString().uppercase(),
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                            style = TextStyle(
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                items(contactsForInitial){ contact->
                    var isDropDownMenuExpanded by remember {
                        mutableStateOf(false)
                    }
                    ListItem(
                        headlineContent = { Text(text = contact.noms) },
                        supportingContent = { Text(contact.tel) },
                        overlineContent = {

                        },
                        leadingContent = {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (contact.isFavorite){
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
                                            .background(Color(contact.color)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = contact.noms.first().uppercase(),
                                            style = TextStyle(
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                fontSize = 24.sp
                                            )
                                        )
                                    }
                                }

                            }


                        },
                        trailingContent = {
                            Row {
                                IconButton(onClick = { onSMSAction(contact) }) {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null )
                                }
                                IconButton(onClick = {onCallAction(contact) }) {
                                    Icon(
                                        imageVector = Icons.Default.Call,
                                        contentDescription = null )
                                }
                                Column {
                                    IconButton(onClick = { isDropDownMenuExpanded = true}) {
                                        Icon(
                                            imageVector = Icons.Default.MoreVert,
                                            contentDescription = null )
                                    }
                                    DropdownMenu(
                                        expanded = isDropDownMenuExpanded,
                                        onDismissRequest = { isDropDownMenuExpanded = false }) {

                                        if(contact.email.isNotBlank()){
                                            DropdownMenuItem(
                                                text = {
                                                    Row (verticalAlignment = Alignment.CenterVertically){
                                                        Text(text = "Email")
                                                    }
                                                },
                                                onClick = {
                                                    context.sendMail(contact.email, "")
                                                    isDropDownMenuExpanded = false
                                                })
                                        }

                                        if(contact.fb.isNotBlank()){
                                            DropdownMenuItem(
                                                text = {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text(text = "Facebook")
                                                    }
                                                },
                                                onClick = {
                                                    handler.openUri(contact.fb)
                                                    isDropDownMenuExpanded = false
                                                })
                                        }

                                        if(contact.x.isNotBlank()){
                                            DropdownMenuItem(
                                                text = {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text(text = "X (Twitter)")
                                                    }
                                                },
                                                onClick = {
                                                    handler.openUri(contact.x)
                                                    isDropDownMenuExpanded = false
                                                })
                                        }

                                        if(contact.linkedin.isNotBlank()){
                                            DropdownMenuItem(
                                                text = {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text(text = "Linkedn")
                                                    }
                                                },
                                                onClick = {
                                                    handler.openUri(contact.linkedin)
                                                    isDropDownMenuExpanded = false
                                                })
                                        }

                                        HorizontalDivider()

                                        DropdownMenuItem(
                                            text = {
                                                Row (verticalAlignment = Alignment.CenterVertically){
                                                    Icon(imageVector = Icons.Default.Star, contentDescription = null)
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text(text = "Ajouter aux favoris")
                                                }
                                            },
                                            onClick = {
                                                onSetContactToFavorite(contact)
                                                isDropDownMenuExpanded = false
                                            })

                                        DropdownMenuItem(
                                            text = {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text(text = "Modifier")
                                                }
                                            },
                                            onClick = {
                                                onUpdateContact(contact)
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
                                                onDeleteContact(contact)
                                                isDropDownMenuExpanded = false
                                            })
                                    }
                                }

                            }
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 4.dp)
                    )

                }

            }

        }
    }

}