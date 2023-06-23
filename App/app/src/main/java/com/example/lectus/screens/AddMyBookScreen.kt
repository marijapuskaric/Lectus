package com.example.lectus.screens

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lectus.R
import com.example.lectus.composables.CustomOutlinedTextField
import com.example.lectus.composables.Header
import com.example.lectus.getFontFamily
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import java.io.InputStream
import kotlinx.coroutines.launch

@Composable
fun AddMyBookScreen() {
    val focusManager = LocalFocusManager.current

    var title by rememberSaveable { mutableStateOf("") }
    var authors by rememberSaveable { mutableStateOf("") }
    var publisher by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var pageCount by rememberSaveable { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }
    fun deleteImage() {
        selectedImage = null
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.champagne))) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(bottom = 60.dp)
        ) {
            Card(
                modifier = Modifier
                    .padding(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.tan)),
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.caput_mortuum),
                        text = stringResource(id = R.string.add_my_book),
                        modifier = Modifier
                            .padding(bottom = 35.dp, top = 35.dp),
                    )
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.caput_mortuum),
                        text = stringResource(id = R.string.title),
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left
                    )
                    CustomOutlinedTextField(
                        value = title,
                        onValueChange = {title = it},
                        label = stringResource(id = R.string.title),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {focusManager.moveFocus(FocusDirection.Down)}
                        )
                    )
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.caput_mortuum),
                        text = stringResource(id = R.string.authors),
                        modifier = Modifier
                            .padding(start = 20.dp, top = 25.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left,

                        )
                    CustomOutlinedTextField(
                        value = authors,
                        onValueChange = {authors = it},
                        label = stringResource(id = R.string.authors),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {focusManager.moveFocus(FocusDirection.Down)}
                        )
                    )
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.caput_mortuum),
                        text = stringResource(id = R.string.description),
                        modifier = Modifier
                            .padding(start = 20.dp, top = 25.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left,

                        )
                    CustomOutlinedTextField(
                        value = description,
                        onValueChange = {description= it},
                        label = stringResource(id = R.string.description),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {focusManager.moveFocus(FocusDirection.Down)}
                        )
                    )
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.caput_mortuum),
                        text = stringResource(id = R.string.publisher),
                        modifier = Modifier
                            .padding(start = 20.dp, top = 25.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left,

                        )
                    CustomOutlinedTextField(
                        value = publisher,
                        onValueChange = {publisher = it},
                        label = stringResource(id = R.string.publisher),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {focusManager.moveFocus(FocusDirection.Down)}
                        )
                    )
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.caput_mortuum),
                        text = stringResource(id = R.string.page_count),
                        modifier = Modifier
                            .padding(start = 20.dp, top = 25.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left,

                        )
                    CustomOutlinedTextField(
                        value = pageCount,
                        onValueChange = {pageCount = it},
                        label = stringResource(id = R.string.page_count),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.caput_mortuum),
                        text = stringResource(id = R.string.photo),
                        modifier = Modifier
                            .padding(start = 20.dp, top = 25.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left,

                        )
                    AddImageField(
                        image = selectedImage,
                        onImageSelected = { imageBitmap -> selectedImage = imageBitmap },
                        onDeleteImage = { deleteImage() }
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.redwood)),

                    ) {
                        Text(fontFamily = getFontFamily(), text = stringResource(id = R.string.add), color = colorResource(id = R.color.champagne))
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }


        }
    }
    
}

@Composable
fun AddImageField(
    image: ImageBitmap?,
    onImageSelected: (ImageBitmap) -> Unit,
    onDeleteImage: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { result: Uri? ->
            result?.let { uri ->
                scope.launch {
                    val imageBitmap = loadBitmapFromUri(context.contentResolver, uri)
                    onImageSelected(imageBitmap)
                }
            }
        }
    )


    Row(Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .border(
                    1.dp,
                    color = colorResource(id = R.color.caput_mortuum),
                    shape = RoundedCornerShape(10.dp)
                )
                .background(
                    colorResource(id = R.color.champagne),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
        ) {
            if (image != null) {
                Row(Modifier.fillMaxSize()) {
                    Image(
                        painter = BitmapPainter(image),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                    IconButton(
                        onClick = onDeleteImage,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Image",
                            tint = colorResource(id = R.color.caput_mortuum)
                        )
                    }
                }
            } else {
                IconButton(
                    onClick = {
                        activityResultLauncher.launch("image/*")
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Image",
                        tint = colorResource(id = R.color.caput_mortuum)
                    )
                }
            }
        }
    }
}

private suspend fun loadBitmapFromUri(contentResolver: ContentResolver, uri: Uri): ImageBitmap {
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val bitmap = inputStream?.use { input ->
        BitmapFactory.decodeStream(input)
    }
    return bitmap?.asImageBitmap() ?: throw IllegalStateException("Failed to load bitmap from Uri: $uri")
}