package com.example.lectus.screens

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lectus.R
import com.example.lectus.composables.CustomOutlinedTextField
import com.example.lectus.getFontFamily
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.example.lectus.authentication.Utils
import com.example.lectus.db.addImageToFirebaseStorage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.InputStream
import kotlinx.coroutines.launch

@Composable
fun AddMyBookScreen() {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val db = Firebase.firestore
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    var title by rememberSaveable { mutableStateOf("") }
    var authors by rememberSaveable { mutableStateOf("") }
    var publisher by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var pageCount by rememberSaveable { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    var selectedImageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val statusOptions = listOf(
        stringResource(id = R.string.to_read),
        stringResource(id = R.string.reading),
        stringResource(id = R.string.finished_reading))
    var selectedStatus by remember { mutableStateOf("") }
    val storage = FirebaseStorage.getInstance()
    fun deleteImage() {
        selectedImage = null
        selectedImageBitmap = null
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
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
                    containerColor = MaterialTheme.colorScheme.primary),
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
                        color = MaterialTheme.colorScheme.tertiary,
                        text = stringResource(id = R.string.add_my_book),
                        modifier = Modifier
                            .padding(bottom = 35.dp, top = 35.dp),
                    )
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.tertiary,
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
                        color = MaterialTheme.colorScheme.tertiary,
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
                        color = MaterialTheme.colorScheme.tertiary,
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
                        color = MaterialTheme.colorScheme.tertiary,
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
                        color = MaterialTheme.colorScheme.tertiary,
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
                        color = MaterialTheme.colorScheme.tertiary,
                        text = stringResource(id = R.string.photo),
                        modifier = Modifier
                            .padding(start = 20.dp, top = 25.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left,

                        )
                    AddImageField(
                        imageUri = selectedImage,
                        onImageSelected = { uri -> selectedImage = uri },
                        image = selectedImageBitmap,
                        onImageSelectedBitmap = { imageBitmap -> selectedImageBitmap = imageBitmap },
                        onDeleteImage = { deleteImage() },
                    )

                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        text = stringResource(id = R.string.select_reading_status),
                        modifier = Modifier
                            .padding(start = 20.dp, top = 25.dp, bottom = 10.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left,

                        )

                    statusOptions.forEach { status ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedStatus = status }
                                .padding(start = 30.dp, top = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedStatus == status,
                                onClick = { selectedStatus = status },
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .size(10.dp),
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.tertiary,
                                    unselectedColor = MaterialTheme.colorScheme.tertiary
                                )
                            )
                            androidx.compose.material.Text(
                                text = status,
                                fontFamily = getFontFamily(),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            if (title != "" && selectedStatus != "") {
                                val authorNames: List<String> =
                                    authors.split(",").map { it.trim() }.toList()
                                var pageCountLong: Long? = null
                                if (currentUserUid != null) {
                                    if (pageCount != "") {
                                        pageCountLong = pageCount.toLong()
                                    }
                                    addImageToFirebaseStorage(
                                        selectedImage,
                                        storage,
                                        title,
                                        authorNames,
                                        description,
                                        publisher,
                                        pageCountLong,
                                        selectedStatus,
                                        currentUserUid,
                                        db,
                                        context
                                    )
                                }
                                title = ""
                                authors = ""
                                publisher = ""
                                description = ""
                                pageCount = ""
                                selectedImage = null
                                selectedImageBitmap = null
                                selectedStatus = ""
                            }
                            else{
                                Utils.showMessage(context, "Review fields, title and reading status cannot be empty.")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),

                    ) {
                        Text(fontFamily = getFontFamily(), text = stringResource(id = R.string.add), color = MaterialTheme.colorScheme.background)
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }


        }
    }
    
}

@Composable
fun AddImageField(
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    image: ImageBitmap?,
    onImageSelectedBitmap: (ImageBitmap) -> Unit,
    onDeleteImage: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { result: Uri? ->
            result?.let { uri ->
                scope.launch {
                    val imageBitmap = loadBitmapFromUri(context.contentResolver, uri)
                    onImageSelectedBitmap(imageBitmap)
                    onImageSelected(uri)
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
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                )
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
        ) {
            if (imageUri != null && image != null) {
                Row(Modifier.fillMaxSize()) {
                    Log.d("IMAGE", imageUri.toString())
                    Image(
                        painter = BitmapPainter(image),
                        contentDescription = stringResource(id = R.string.select_image),
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
                            contentDescription = stringResource(id = R.string.delete_image),
                            tint = MaterialTheme.colorScheme.tertiary
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
                        contentDescription = stringResource(id = R.string.add_image),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}

private fun loadBitmapFromUri(contentResolver: ContentResolver, uri: Uri): ImageBitmap {
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val bitmap = inputStream?.use { input ->
        BitmapFactory.decodeStream(input)
    }
    return bitmap?.asImageBitmap() ?: throw IllegalStateException("Failed to load bitmap from Uri: $uri")
}

