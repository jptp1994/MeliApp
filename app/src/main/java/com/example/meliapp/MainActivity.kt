/**
 * Main activity of the Mercado Libre app.
 * Sets up the Compose UI and navigation for the application.
 * Layer: Presentation
 * Usage: Entry point for the app, handles UI composition and navigation setup.
 */
package com.example.meliapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.meliapp.presentation.ui.navigation.MeliAppNavHost
import com.example.meliapp.presentation.ui.theme.MeliAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeliAppTheme {
                MeliAppNavHost()
            }
        }
    }
}
