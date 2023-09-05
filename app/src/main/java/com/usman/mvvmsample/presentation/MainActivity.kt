package com.usman.mvvmsample.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.usman.mvvmsample.presentation.theme.DogAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DogAppTheme {
                DogBreedsApp()
            }
        }
    }
}