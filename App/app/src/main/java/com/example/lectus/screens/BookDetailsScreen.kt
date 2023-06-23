package com.example.lectus.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.lectus.R
import com.example.lectus.composables.Header
import com.example.lectus.data.Book
import com.example.lectus.getFontFamily

@Composable
fun BookDetailsScreen(book: Book, navigateBack: () -> Unit) {
    var desctiption: String
    var pageCount: String
    var publisher: String
    if (book.volumeInfo.description != null)
    {
        desctiption = book.volumeInfo.description
    }
    else
    {
        desctiption = "-"
    }
    if (book.volumeInfo.pageCount != null)
    {
        pageCount = book.volumeInfo.pageCount.toString()
    }
    else
    {
        pageCount = "-"
    }
    if (book.volumeInfo.publisher != null)
    {
        publisher = book.volumeInfo.publisher
    }
    else
    {
        publisher = "-"
    }

    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            Column() {
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = colorResource(id = R.color.caput_mortuum),
                        modifier = Modifier.width(40.dp).clickable { navigateBack()  }
                    )

                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                ) {
                    if (book.volumeInfo.imageLinks != null)
                    {
                        val url: StringBuilder = StringBuilder(book.volumeInfo.imageLinks?.thumbnail)
                        url.insert(4, "s")
                        Image(
                            painter = rememberImagePainter(data = url.toString()),
                            contentDescription = stringResource(id = R.string.book_img_description),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(300.dp)
                        )
                    }
                    else
                    {
                        Box(
                            modifier = Modifier
                                .size(300.dp)
                                .background(colorResource(id = R.color.khaki)),
                        ) {
                            Icon(
                                Icons.Filled.ImageNotSupported,
                                contentDescription = null,
                                tint = colorResource(id = R.color.caput_mortuum),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(70.dp, 200.dp),
                            )
                        }
                    }

                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = book.volumeInfo.title,
                        style = TextStyle(
                            color = colorResource(id = R.color.caput_mortuum),
                            fontFamily = getFontFamily(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                ) {
                    Column()
                    {
                        Row(Modifier.padding(bottom = 5.dp)) {
                            Text(
                                text = "Authors: ",
                                style = TextStyle(
                                    color = colorResource(id = R.color.caput_mortuum),
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                )
                            )
                            book.volumeInfo.authors?.let {
                                    authors ->
                                Text(text = authors.joinToString(),
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = getFontFamily(),
                                        color = colorResource(id = R.color.caput_mortuum)
                                    )
                                )
                            }
                        }
                        Row(Modifier.padding(bottom = 5.dp)) {
                            Text(
                                text = "Description: ",
                                style = TextStyle(
                                    color = colorResource(id = R.color.caput_mortuum),
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                text = desctiption,
                                style = TextStyle(
                                    color = colorResource(id = R.color.caput_mortuum),
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                )
                            )
                        }
                        Row(Modifier.padding(bottom = 5.dp)) {
                            Text(
                                text = "Page count: ",
                                style = TextStyle(
                                    color = colorResource(id = R.color.caput_mortuum),
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                text = pageCount,
                                style = TextStyle(
                                    color = colorResource(id = R.color.caput_mortuum),
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                )
                            )
                        }
                        Row(Modifier.padding(bottom = 5.dp)) {
                            Text(
                                text = "Publisher: ",
                                style = TextStyle(
                                    color = colorResource(id = R.color.caput_mortuum),
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                text = publisher,
                                style = TextStyle(
                                    color = colorResource(id = R.color.caput_mortuum),
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}