package com.example.lectus.db

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.lectus.authentication.Utils
import com.example.lectus.data.BOOKS_COLLECTION
import com.example.lectus.data.DAILY_GOAL
import com.example.lectus.data.DAILY_GOAL_COLLECTION
import com.example.lectus.data.DAY_GOAL_COLLECTION
import com.example.lectus.data.LOG_DB
import com.example.lectus.data.MONTHLY_GOAL_COLLECTION
import com.example.lectus.data.TAG
import com.example.lectus.data.USERS_COLLECTION
import com.example.lectus.data.USER_BOOKS_COLLECTION
import com.example.lectus.data.USER_GOALS_COLLECTION
import com.example.lectus.data.YEARLY_GOAL_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.Calendar
import java.util.UUID



fun addUserToFirestore(username: String, email: String, userId: String, db: FirebaseFirestore)
{
    val user = hashMapOf(
        "username" to username,
        "email" to email,
        "userId" to userId
    )

    db.collection(USERS_COLLECTION)
        .add(user)
        .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
}

fun addBookToFirestore(
    title : String,
    authors: List<String>?,
    publisher: String?,
    description: String?,
    pageCount: Long?,
    image: String?,
    currentUserUid: String,
    status: String,
    db: FirebaseFirestore,
    context: Context
) {
    val bookData = hashMapOf(
        "title" to title,
        "authors" to authors,
        "publisher" to publisher,
        "description" to description,
        "pageCount" to pageCount,
        "image" to image,
        "status" to status
    )

    db.collection(USER_BOOKS_COLLECTION)
        .document(currentUserUid)
        .collection(BOOKS_COLLECTION)
        .add(bookData)
        .addOnSuccessListener { documentReference ->
            Log.d(LOG_DB, "Book added with ID: ${documentReference.id}")
            Utils.showMessage(context, "Book successfully added.")
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error adding book", e)
            Utils.showMessage(context, "Error adding book.")
        }
}

fun addImageToFirebaseStorage(
    imageUri: Uri?,
    storage: FirebaseStorage,
    title: String,
    authors: List<String>?,
    description: String?,
    publisher: String?,
    pageCount: Long?,
    status: String,
    currentUserUid: String,
    db: FirebaseFirestore,
    context: Context
) {
    val fileName = "${UUID.randomUUID()}.jpg"
    val storageRef = storage.reference.child(fileName)
    if (imageUri != null) {

        val uploadTask = storageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { downloadUrl ->
                val imageUrl = downloadUrl.toString()
                addBookToFirestore(title, authors, description, publisher, pageCount, imageUrl,  currentUserUid, status, db, context)
            }
        }.addOnFailureListener {
        }
    }
    else {
        addBookToFirestore(title, authors, description, publisher, pageCount, null,  currentUserUid, status, db, context)
    }
}

fun updateBookStatus(
    bookId: String,
    status: String,
    currentUserUid: String,
    db: FirebaseFirestore,
    context: Context
){
    val bookRef = db.collection(USER_BOOKS_COLLECTION)
        .document(currentUserUid)
        .collection(BOOKS_COLLECTION)
        .document(bookId)
    bookRef.update("status", status)
        .addOnSuccessListener {
            Log.d(LOG_DB, "Book status updated successfully")
            Utils.showMessage(context, "Book status updated successfully.")
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error updating book status", e)
            Utils.showMessage(context, "Error updating book status.")
        }
    if (status == "Finished reading")
    {
        updateYearlyFinishedBooks(currentUserUid, db, context)
    }
}

fun deleteBook (
    bookId: String,
    currentUserUid: String,
    db: FirebaseFirestore,
    context: Context
){
    db.collection(USER_BOOKS_COLLECTION)
        .document(currentUserUid)
        .collection(BOOKS_COLLECTION)
        .document(bookId)
        .delete()
        .addOnSuccessListener {
            Log.d(LOG_DB, "Book deleted successfully")
            Utils.showMessage(context, "Book deleted successfully.")
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error deleting book", e)
            Utils.showMessage(context, "Error deleting book.")
        }
}

fun setUserYearlyGoal(
    yearlyGoal: Int,
    currentUserUid: String,
    db: FirebaseFirestore,
    currentYear: Int,
    context: Context
){
    val goalData = hashMapOf(
        "goal" to yearlyGoal,
        "finished" to null,
    )
    db.collection(USER_GOALS_COLLECTION)
        .document(currentUserUid)
        .collection(YEARLY_GOAL_COLLECTION)
        .document(currentYear.toString())
        .set(goalData)
        .addOnSuccessListener {
            Log.d(LOG_DB, "Yearly goal document created for $currentYear")
            Utils.showMessage(context, "Yearly goal updated for $currentYear")
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error adding goal", e)
            Utils.showMessage(context, "Error updating yearly goal for $currentYear")
        }
}

fun updateYearlyGoal (
    yearlyGoal: Int,
    currentUserUid: String,
    db: FirebaseFirestore,
    context: Context
){
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val goalRef = db.collection(USER_GOALS_COLLECTION)
        .document(currentUserUid)
        .collection(YEARLY_GOAL_COLLECTION)
        .document(currentYear.toString())
    goalRef.get()
        .addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists())
            {
                goalRef.update("goal", yearlyGoal)
                    .addOnSuccessListener {
                        Log.d(LOG_DB, "Yearly goal updated for $currentYear")
                        Utils.showMessage(context, "Yearly goal updated for $currentYear")
                    }
                    .addOnFailureListener { e ->
                        Log.e(LOG_DB, "Error updating yearly goal", e)
                        Utils.showMessage(context, "Error updating yearly goal.")
                    }
            }
            else {
                setUserYearlyGoal(yearlyGoal, currentUserUid, db, currentYear, context)
            }
        }
        .addOnFailureListener {e ->
            Log.d(LOG_DB, "Error checking yearly goal document", e)
        }

}

fun updateYearlyFinishedBooks(
    currentUserUid: String,
    db: FirebaseFirestore,
    context: Context
){
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val goalRef = db.collection(USER_GOALS_COLLECTION)
        .document(currentUserUid)
        .collection(YEARLY_GOAL_COLLECTION)
        .document(currentYear.toString())
    goalRef.get()
        .addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val currentFinishedCount = documentSnapshot.getLong("finished") ?: 0
                val newFinishedCount = currentFinishedCount + 1
                goalRef.update("finished", newFinishedCount)
                    .addOnSuccessListener {
                        Log.d(LOG_DB, "Yearly finished books updated for $currentYear")
                        Utils.showMessage(context, "Yearly finished books updated for $currentYear")
                    }
                    .addOnFailureListener { e ->
                        Log.e(LOG_DB, "Error updating yearly finished books", e)
                        Utils.showMessage(context, "Error updating yearly finished books.")
                    }
            } else {
                Utils.showMessage(context, "Yearly goal document does not exist for $currentYear")
            }
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error checking yearly goal document", e)
        }
}


fun setUserDailyGoal(
    option: String,
    number: Int,
    currentUserUid: String,
    db: FirebaseFirestore,
    context: Context
){
    val goalData = hashMapOf(
        "option" to option,
        "number" to number,
    )
    db.collection(USER_GOALS_COLLECTION)
        .document(currentUserUid)
        .collection(DAILY_GOAL_COLLECTION)
        .document(DAILY_GOAL)
        .set(goalData)
        .addOnSuccessListener {
            Log.d(LOG_DB, "Daily goal document created")
            Utils.showMessage(context, "Daily goal updated.")
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error adding goal", e)
            Utils.showMessage(context, "Error updating daily goal.")
        }
}

fun addDailyGoalsAchieved(
    currentYear: Int,
    currentMonth: String,
    day: Int,
    achieved: Boolean,
    currentUserUid: String,
    db: FirebaseFirestore,
){
    val achievedData = hashMapOf(
        "achievedGoal" to achieved
    )
    db.collection(USER_GOALS_COLLECTION)
        .document(currentUserUid)
        .collection(DAILY_GOAL_COLLECTION)
        .document(currentYear.toString())
        .collection(MONTHLY_GOAL_COLLECTION)
        .document(currentMonth)
        .collection(DAY_GOAL_COLLECTION)
        .document(day.toString())
        .set(achievedData)
        .addOnSuccessListener {
            Log.d(LOG_DB, "Achieved goal for day $day added.")
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error adding achieved goal for day $day", e)
        }
}







