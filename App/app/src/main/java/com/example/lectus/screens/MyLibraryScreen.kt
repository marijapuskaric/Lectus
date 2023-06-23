package com.example.lectus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.lectus.R
import com.example.lectus.composables.Header
import com.example.lectus.composables.RecyclerView
import com.example.lectus.getFontFamily

@Composable
fun MyLibraryScreen() {
    var selectedTopItem by remember { mutableStateOf(0) }
    val itemsTop = listOf("All", "To read", "Finished reading")
    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.champagne))) {
        Header()
        Row(
        ){
            NavigationBar(
                containerColor = colorResource(id = R.color.redwood)
            ) {
                itemsTop.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {},
                        label = {
                            Text(
                                item,
                                fontFamily = getFontFamily(),
                                color = colorResource(id = R.color.champagne)
                            )
                        },
                        selected = selectedTopItem == index,
                        onClick = { selectedTopItem = index },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = colorResource(id = R.color.champagne)
                        )
                    )
                }
            }
        }
        Column(Modifier.weight(1f)) {
          //  RecyclerView(false)
        }
    }
}