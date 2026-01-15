/**
 * Application class for the Mercado Libre app.
 * This class initializes Hilt for dependency injection across the application.
 * Layer: Application
 * Usage: Automatically instantiated by the Android system to provide global context and DI setup.
 */
package com.example.meliapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MeliApplication : Application()