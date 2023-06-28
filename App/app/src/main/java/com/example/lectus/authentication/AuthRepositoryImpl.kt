package com.example.lectus.authentication

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.lectus.data.Response
import com.example.lectus.db.addUserToFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository
{
    override val currentUser get() = auth.currentUser
    var signUpResponse by mutableStateOf<SignUpResponse>(Response.Success(false))
        private set
    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String, username: String, context: Context, db: FirebaseFirestore
    ): SignUpResponse
    {
        return try
        {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    signUpResponse = if (task.isSuccessful)
                    {
                        Utils.showMessage(context, "Registration successful")
                        val userUid = FirebaseAuth.getInstance().currentUser?.uid
                        if (userUid != null)
                        {
                            addUserToFirestore(username, email, userUid, db)
                        }
                        Response.Success(true)
                    } else
                    {
                        Utils.showMessage(context,"Registration failed: ${task.exception?.message}")
                        Response.Failure(Exception(task.exception?.message))
                    }
                }
            Response.Loading
        }
        catch (e: Exception)
        {
            Response.Failure(e)
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String, password: String
    ): SignInResponse
    {
        return try
        {
            auth.signInWithEmailAndPassword(email, password).await()
            Response.Success(true)
        }
        catch (e: Exception)
        {
            Response.Failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse
    {
        return try
        {
            auth.sendPasswordResetEmail(email).await()
            Response.Success(true)
        }
        catch (e: Exception)
        {
            Response.Failure(e)
        }
    }

    override fun signOut() = auth.signOut()


    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)
}
