package com.example.lectus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.lectus.authentication.MainViewModel
import com.example.lectus.mainnavigation.NavGraph
import com.example.lectus.mainnavigation.Screen
import com.example.lectus.ui.theme.LectusTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
public final class MainActivity: ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var viewModel: MainViewModel

    @OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberAnimatedNavController()
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            LectusTheme {
                Surface(
                    Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                )
                {
                    NavGraph(
                        navController = navController
                    )
                    AuthState(viewModel = viewModel)
                }
            }
        }
    }
    @Composable
    private fun AuthState(viewModel: MainViewModel) {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        if (isUserSignedOut) {
            NavigateToLoginScreen()
        } else {
            NavigateToMainScreen()
        }
    }
    @Composable
    private fun NavigateToLoginScreen() = navController.navigate(Screen.LoginScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToMainScreen() = navController.navigate(Screen.MainScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }


}

@Preview (showBackground = true)
@Composable
fun defaultPreview(){

}
