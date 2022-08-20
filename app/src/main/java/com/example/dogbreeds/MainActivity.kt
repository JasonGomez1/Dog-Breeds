package com.example.dogbreeds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dogbreeds.screens.DogBreedsScreen
import com.example.dogbreeds.ui.theme.DogBreedsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogBreedsTheme {
                DogBreedsScreen()
            }
        }
    }
}
