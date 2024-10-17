package com.tacite.contactapp.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tacite.contactapp.data.local.ContactEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    contacts : List<ContactEntity>
){
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {

        val groupedList = contacts.groupBy { it.noms.first() }

        LazyColumn {

            groupedList.forEach { (initial, contactsForInitial) ->

                stickyHeader {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainerLow).size(70.dp, 60.dp)
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(text = initial.toString(),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = TextStyle(
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }



                }

                items(contactsForInitial){
                    ListItem(
                        headlineContent = { Text(text = it.noms) },
                        supportingContent = { Text(it.tel) },
                        leadingContent = {
                            Box(
                                modifier =
                                Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                                    .background(Color(it.color)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = it.noms.first().uppercase(),
                                    style = TextStyle(
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontSize = 32.sp
                                    )
                                )
                            }
                        },



                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier =
                        Modifier
                            .clickable {
                                //textFieldState.setTextAndPlaceCursorAtEnd(it.noms)
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp, vertical = 4.dp)
                    )
                }

            }

        }
    }
}