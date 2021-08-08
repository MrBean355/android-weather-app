package com.github.mrbean355.android.weatherapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.github.mrbean355.android.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val requestPermission = registerForActivityResult(RequestPermission()) {
        if (it) viewModel.onPermissionGranted()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Column(Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.forest_sunny),
                        contentDescription = "Background",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF47AB2F))
                    ) {
                        Text("TODO")
                    }
                }

                requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}