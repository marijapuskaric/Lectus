package com.example.lectus.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.lectus.R
import com.example.lectus.composables.dialogs.EditDialog
import com.example.lectus.composables.dialogs.StatusSelectionDialog
import com.example.lectus.getFontFamily

@Composable
fun <T> ListItemLibrary(
    onBookClicked: (T) -> Unit,
    book: T,
    title : String?,
    authors: List<String>?,
    publisher: String?,
    description: String?,
    pageCount: Long?,
    image: String?,
    add:Boolean,
    edit:Boolean
) {
    val context = LocalContext.current
    val showStatusDialog = remember { mutableStateOf(false) }
    val showEditDialog = remember { mutableStateOf(false) }
    if (showStatusDialog.value)
    {
        StatusSelectionDialog(
            title,
            authors,
            publisher,
            description,
            pageCount,
            image,
            showDialog = showStatusDialog.value,
            context,
            onBookAdded = { },
            onDismiss = { showStatusDialog.value = false })
    }
    if (showEditDialog.value)
    {
        EditDialog(
            showDialog = showEditDialog.value,
            onBookChanged = { },
            onDismiss = { showEditDialog.value = false },
            title, authors, publisher, pageCount, image, context
        )
    }
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(vertical = .4.dp, horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onBookClicked(book) }
        ) {
            Row(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Column(Modifier.width(IntrinsicSize.Max))
                {
                    if (image != null)
                    {
                        val url: String = if (image.startsWith("http://"))
                        {
                            "https://" + image.substring(7)
                        }
                        else
                        {
                            image
                        }
                        Image(
                            painter = rememberImagePainter(data = url),
                            contentDescription = stringResource(id = R.string.book_cover_image),
                            modifier = Modifier.size(100.dp, 200.dp),
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(100.dp, 200.dp)
                                .background(MaterialTheme.colorScheme.surface),
                        ) {
                            Icon(
                                Icons.Filled.ImageNotSupported,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(70.dp, 200.dp),
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = (title ?: "-"),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = getFontFamily(),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    )
                    authors?.let { authors ->
                        Text(
                            text = authors.joinToString(),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = getFontFamily(),
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                    }
                }
                if (add)
                {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(80.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clickable { showStatusDialog.value = true }
                        ) {
                            Icon(
                                Icons.Filled.AddCircleOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.width(40.dp)
                            )
                        }
                    }
                }
                if (edit)
                {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(80.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clickable { showEditDialog.value = true }
                        ) {
                            Icon(
                                Icons.Filled.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.width(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}








