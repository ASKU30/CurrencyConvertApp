package com.example.currencyconvertcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconvertcompose.ui.home.Home
import com.example.currencyconvertcompose.ui.home.HomeViewModel
import com.example.currencyconvertcompose.ui.theme.CurrencyConvertComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConvertComposeTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: HomeViewModel by viewModels()
                    Home(
                        viewModel = viewModel
                    )
                }*/
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: HomeViewModel by viewModels()
                    Home(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CurrencyConvertComposeTheme {
        Greeting("Android")
    }
}