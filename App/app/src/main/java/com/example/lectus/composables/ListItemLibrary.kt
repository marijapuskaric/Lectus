package com.example.lectus.composables

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.lectus.R
import com.example.lectus.data.Book
import com.example.lectus.getFontFamily

@Composable
fun ListItemLibrary(onBookClicked: (Book) -> Unit, book: Book, add:Boolean) {
    Log.d("sta", book.toString())
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(vertical = .4.dp, horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .border(
                    1.dp,
                    color = colorResource(id = R.color.caput_mortuum),
                    shape = RoundedCornerShape(8.dp)
                ).clickable {
                    onBookClicked(book)
                }
        ){
            Row(modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
            ) {
                Column(Modifier.width(IntrinsicSize.Max)) {
                    if (book.volumeInfo.imageLinks != null)
                    {
                        val url: StringBuilder = StringBuilder(book.volumeInfo.imageLinks?.thumbnail)
                        url.insert(4, "s")

                        Image(
                            painter = rememberImagePainter(data = url.toString()),
                            contentDescription = "Book cover image",
                            modifier = Modifier.size(100.dp, 200.dp),
                        )
                    }
                    else
                    {
                        Box(
                            modifier = Modifier
                                .size(100.dp, 200.dp)
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
                Column(modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                verticalArrangement = Arrangement.Center) {
                    Text(
                        text = book.volumeInfo.title,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = getFontFamily(),
                            color = colorResource(id = R.color.caput_mortuum)
                        )
                    )
                    book.volumeInfo.authors?.let { authors -> Text(text = authors.joinToString(), style = TextStyle(fontSize = 14.sp, fontFamily = getFontFamily(), color = colorResource(id = R.color.caput_mortuum)))}
                }
                if (add)
                {
                    Column(modifier = Modifier.align(Alignment.CenterVertically).width(80.dp)) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        ){
                            Icon(
                                Icons.Filled.AddCircleOutline,
                                contentDescription = null,
                                tint = colorResource(id = R.color.caput_mortuum),
                                modifier = Modifier.width(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}







