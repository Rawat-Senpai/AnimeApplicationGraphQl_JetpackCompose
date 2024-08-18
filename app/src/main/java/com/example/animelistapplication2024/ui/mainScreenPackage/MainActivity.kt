package com.example.animelistapplication2024.ui.mainScreenPackage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.animelistapplication2024.ui.theme.AnimeListApplication2024Theme
import com.example.animelistapplication2024.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeListApplication2024Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    mainViewModel.getAnimeList()
                    setObserver()
                }
            }
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            mainViewModel.getAnimeList().observe(this@MainActivity) {
                when (it) {
                    is NetworkResult.Loading -> {
                        Log.d("checkingApiResponse", "setupObserver: Else - Called")
                    }
                    is NetworkResult.Success -> {
                        Log.d("checkingApiResponse", it.data.toString())
                        //Update UI according to the data received
                    }
                    is NetworkResult.Error -> {
                        Log.d("checkingApiResponse", it.message.toString())
                    }
                    else -> {
                        Log.d("checkingApiResponse", "setupObserver: Else - Called")
                    }
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
    AnimeListApplication2024Theme {
        Greeting("Android")
    }
}