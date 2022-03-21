package com.afzaln.common

import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    LaunchedEffect(Unit) {
        WorkoutRepository().schedule()
    }

    Button(onClick = {
        text = "Hello, ${getPlatformName()}"
    }) {
        Text(text)
    }
}
