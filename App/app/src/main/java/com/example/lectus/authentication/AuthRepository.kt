package com.example.lectus.authentication


import android.content.Context
import com.example.lectus.data.Response
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow


typealias SignUpResponse = Response<Boolean>
typealias SignInResponse = Response<Boolean>
typealias SendPasswordResetEmailResponse = Response<Boolean>
typealias AuthStateResponse = StateFlow<Boolean>

interface AuthRepository
{
    val currentUser: FirebaseUser?
    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String, username: String, context: Context, db: FirebaseFirestore): SignUpResponse
    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): SignInResponse
    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse
    fun signOut()
    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
}