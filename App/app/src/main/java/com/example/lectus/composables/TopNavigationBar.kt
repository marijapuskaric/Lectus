package com.example.lectus.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material.Tab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lectus.getFontFamily


@Composable
fun TopNavigationBar(
    tabs: List<String>,
    onTabSelected: (Int) -> Unit,
    selectedTabIndex: Int
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = MaterialTheme.colorScheme.secondary,
        indicator = {tabPositions ->
            val selectedTabPosition = tabPositions[selectedTabIndex]
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(selectedTabPosition)
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.primary),
                color = MaterialTheme.colorScheme.primary

            )
        }
    ) {
        tabs.forEachIndexed { index, s ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = s,
                        fontFamily = getFontFamily(),
                        color = MaterialTheme.colorScheme.background

                    )
                }
            )
        }
    }
}
