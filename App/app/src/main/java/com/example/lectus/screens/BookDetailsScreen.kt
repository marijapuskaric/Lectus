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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.lectus.R
import com.example.lectus.data.Book
import com.example.lectus.data.BookData
import com.example.lectus.getFontFamily

@Composable
fun  <T> BookDetailsScreen(book: T, navigateBack: () -> Unit, readingStatus: Boolean)
{
    var title  = "-"
    var authors: String? = "-"
    var description = "-"
    var pageCount = "-"
    var publisher = "-"
    var image: String? = null
    var status = "-"
    if (book is BookData)
    {
        if (book.title != null)
        {
            title = book.title
        }
        authors = book.authors?.joinToString(", ")
        if (book.description != null)
        {
            description = book.description
        }
        if (book.pageCount != null)
        {
            pageCount = book.pageCount.toString()
        }
        if (book.publisher != null)
        {
            publisher = book.publisher
        }
        image = book.image
        status = book.status
    }
    if (book is Book)
    {
        if (book.volumeInfo.title != null)
        {
            title = book.volumeInfo.title
        }
        authors = book.volumeInfo.authors?.joinToString(", ")
        if (book.volumeInfo.description != null)
        {
            description = book.volumeInfo.description
        }
        if (book.volumeInfo.pageCount != null)
        {
            pageCount = book.volumeInfo.pageCount.toString()
        }
        if (book.volumeInfo.publisher != null)
        {
            publisher = book.volumeInfo.publisher
        }
        image = book.volumeInfo.imageLinks?.thumbnail
    }
    if (authors == null) {
        authors = "-"
    }
    Column(Modifier.fillMaxSize())
    {
        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            Column{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .width(40.dp)
                            .clickable { navigateBack() }
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                ) {
                    if (image != null)
                    {
                        val url: String = if (image.startsWith("http://"))
                        {
                            "https://" + image.substring(7)
                        } else
                        {
                            image
                        }
                        Image(
                            painter = rememberImagePainter(data = url),
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
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.tertiary,
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
                    Column{
                        Row(Modifier.padding(bottom = 5.dp))
                        {
                            Text(
                                text = stringResource(id = R.string.details_authors),
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier.padding(end = 5.dp)
                            )
                            Text(
                                text = authors,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = getFontFamily(),
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            )
                        }
                        Row(Modifier.padding(bottom = 5.dp))
                        {
                            Text(
                                text = stringResource(id = R.string.details_description),
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier.padding(end = 5.dp)
                            )
                            Text(
                                text = description,
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                )
                            )
                        }
                        Row(Modifier.padding(bottom = 5.dp))
                        {
                            Text(
                                text = stringResource(id = R.string.details_page_count),
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier.padding(end = 5.dp)
                            )
                            Text(
                                text = pageCount,
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                )
                            )
                        }
                        Row(Modifier.padding(bottom = 5.dp))
                        {
                            Text(
                                text = stringResource(id = R.string.details_publisher),
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier.padding(end = 5.dp)
                            )
                            Text(
                                text = publisher,
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontFamily = getFontFamily(),
                                    fontSize = 16.sp
                                )
                            )
                        }
                        if (readingStatus)
                        {
                            Row(Modifier.padding(bottom = 5.dp))
                            {
                                Text(
                                    text = stringResource(id = R.string.details_status),
                                    style = TextStyle(
                                        color = MaterialTheme.colorScheme.tertiary,
                                        fontFamily = getFontFamily(),
                                        fontSize = 16.sp
                                    ),
                                    modifier = Modifier.padding(end = 5.dp)
                                )
                                Text(
                                    text = status,
                                    style = TextStyle(
                                        color = MaterialTheme.colorScheme.tertiary,
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
}
