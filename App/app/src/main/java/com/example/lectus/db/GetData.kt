package com.example.lectus.db

import android.util.Log
import com.example.lectus.data.BOOKS_COLLECTION
import com.example.lectus.data.BookData
import com.example.lectus.data.DAILY_GOAL
import com.example.lectus.data.DAILY_GOAL_COLLECTION
import com.example.lectus.data.DAY_GOAL_COLLECTION
import com.example.lectus.data.LOG_DB
import com.example.lectus.data.MONTHLY_GOAL_COLLECTION
import com.example.lectus.data.USERS_COLLECTION
import com.example.lectus.data.USER_BOOKS_COLLECTION
import com.example.lectus.data.USER_GOALS_COLLECTION
import com.example.lectus.data.YEARLY_GOAL_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

fun getBooksFromFirestore(
    db: FirebaseFirestore,
    currentUserUid: String,
    callback: (List<BookData>) -> Unit
){
    val booksCollection = db.collection(USER_BOOKS_COLLECTION)
        .document(currentUserUid)
        .collection(BOOKS_COLLECTION)

    booksCollection.get()
        .addOnSuccessListener { querySnapshot ->
            val books = querySnapshot.documents.mapNotNull { documentSnapshot ->
                val bookData = documentSnapshot.toObject(BookData::class.java)
                bookData
            }
            callback(books)
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error fetching books", e)
            callback(emptyList())
        }
}

fun getBookId(
    title : String,
    authors: List<String>?,
    publisher: String?,
    pageCount: Long?,
    image: String?,
    currentUserUid: String,
    db: FirebaseFirestore,
    callback: (String?) -> Unit
){
    db.collection(USER_BOOKS_COLLECTION)
        .document(currentUserUid)
        .collection(BOOKS_COLLECTION)
        .whereEqualTo("title", title)
        .whereEqualTo("authors", authors)
        .whereEqualTo("publisher", publisher)
        .whereEqualTo("image", image)
        .whereEqualTo("pageCount", pageCount)
        .get()
        .addOnSuccessListener { querySnapshot ->
            if(!querySnapshot.isEmpty){
               val bookId = querySnapshot.documents[0].id
                callback(bookId)
            }
            else{
                callback(null)
            }
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error retrieving book ID", e)
            callback(null)
        }
}

fun getUser (
    currentUserUid: String,
    db: FirebaseFirestore,
    callback: (String?, String?) -> Unit
){
    db.collection(USERS_COLLECTION)
        .whereEqualTo("userId", currentUserUid)
        .get()
        .addOnSuccessListener {querySnapshot ->
            if (!querySnapshot.isEmpty)
            {
                val userDocument = querySnapshot.documents[0]
                val username = userDocument.getString("username")
                val email = userDocument.getString("email")
                callback.invoke(username, email)
            }
            else{
                callback.invoke(null, null)
            }
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error retrieving user data", e)
            callback.invoke(null, null)
        }
}

fun getYearlyGoal(
    currentUserUid: String,
    db: FirebaseFirestore,
    callback: (Long, Long) -> Unit
){
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val goalRef = db.collection(USER_GOALS_COLLECTION)
        .document(currentUserUid)
        .collection(YEARLY_GOAL_COLLECTION)
        .document(currentYear.toString())
    goalRef.get()
        .addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val goal = documentSnapshot.getLong("goal")
                val finished = documentSnapshot.getLong("finished")
                Log.d(LOG_DB, goal.toString())
                Log.d(LOG_DB, finished.toString())
                if (goal != null && finished != null) callback(goal, finished)
            }
            else{
                callback(0, 0)
            }
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error retrieving yearly goal", e)
            callback(0, 0)
        }

}

fun getDailyGoal(
    currentUserUid: String,
    db: FirebaseFirestore,
    callback: (String?, Long?) -> Unit
){
    val goalRef = db.collection(USER_GOALS_COLLECTION)
        .document(currentUserUid)
        .collection(DAILY_GOAL_COLLECTION)
        .document(DAILY_GOAL)
    goalRef.get()
        .addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val option = documentSnapshot.getString("option")
                val number = documentSnapshot.getLong("number")
                Log.d(LOG_DB, option.toString())
                Log.d(LOG_DB, number.toString())
                callback(option, number)
            }
            else{
                callback(null, null)
            }
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error retrieving daily goal", e)
            callback(null, null)
        }
}

fun getMonthlyAchievements(
    currentUserUid: String,
    currentYear: Int,
    monthName: String,
    daysInMonth: Int,
    dayStates: MutableList<Boolean>,
    db: FirebaseFirestore
) {
    db.collection(USER_GOALS_COLLECTION)
        .document(currentUserUid)
        .collection(DAILY_GOAL_COLLECTION)
        .document(currentYear.toString())
        .collection(MONTHLY_GOAL_COLLECTION)
        .document(monthName)
        .collection(DAY_GOAL_COLLECTION)
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val querySnapshot = task.result
                querySnapshot?.documents?.forEach { document ->
                    val day = document.id.toIntOrNull()
                    val achieved = document.getBoolean("achievedGoal")
                    if (day != null && day in 1..daysInMonth) {
                        dayStates[day - 1] = achieved ?: false
                    }
                }
            } else {
                // Handle the failure case
                Log.e(LOG_DB, "Error fetching achievements: ${task.exception}")
            }
        }
        .addOnFailureListener { e ->
            Log.e(LOG_DB, "Error getting achievement history", e)
        }
}