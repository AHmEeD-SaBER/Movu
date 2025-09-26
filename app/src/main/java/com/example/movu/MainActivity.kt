package com.example.movu

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.theme.MovuTheme
import com.example.navigation.MovuNavHost
import com.example.core_ui.base.Routes
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val metrics = Resources.getSystem().displayMetrics
        val swDp = min(metrics.widthPixels, metrics.heightPixels) / metrics.density
        Log.d("DeviceInfo", "Smallest width = $swDp dp")

        setContent {
            MovuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MovuNavHost(
                        navController = navController,
                        startDestination = Routes.Splash
                    )
                }
            }
        }
    }
}
