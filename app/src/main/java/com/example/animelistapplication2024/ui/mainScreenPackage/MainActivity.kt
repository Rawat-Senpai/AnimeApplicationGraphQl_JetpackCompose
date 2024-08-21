package com.example.animelistapplication2024.ui.mainScreenPackage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.GetAnimeListQuery
import com.example.animelistapplication2024.ui.theme.AnimeListApplication2024Theme
import com.example.animelistapplication2024.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.getAnimeList().observe(this) { animeListState ->
            setContent {
                AnimeListApplication2024Theme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        AnimeListScreen(animeListState)
                    }
                }
            }
        }
    }

    @Composable
    private fun AnimeListScreen(animeListState: NetworkResult<out Any>?) {

        when (animeListState) {
            is NetworkResult.Loading -> {
                Log.d("checkingApiResponse", " loading ")
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is NetworkResult.Success -> {
                val animeList = animeListState.data as? GetAnimeListQuery.Page

                LazyColumn {
                    items(animeList?.media ?: emptyList()) { anime ->
                        AnimeListItem(anime = anime!!)
                    }
                }


                Log.d("checkingApiResponse", animeList?.media.toString())
                animeList?.let {
                    Log.d("checkingApiResponse", "Data received: ${it.media} items")
                    // Update UI according to the data received
                    it.media?.forEach { anime ->
                        Log.d("checkingApiResponse_1", "Anime Name: ${anime.toString()}")
                    }
                } ?: Log.d("checkingApiResponse", "No data available")
                //Update UI according to the data received
            }

            is NetworkResult.Error -> {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Error: ${animeListState.message}",
                        color = Color.Red
                    )
                }


                Log.d("checkingApiResponse", animeListState.message.toString())
            }

            else -> {
                Log.d("checkingApiResponse", "setupObserver: Else - Called")

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Unexpected state")
                }

            }
        }

    }

    @Composable
    fun AnimeListItem(anime: GetAnimeListQuery.Medium) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),

            ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = anime.title?.english ?: anime.title?.native ?: "Unknown",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = anime.description.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "URL: ${anime.siteUrl}", style = MaterialTheme.typography.bodyMedium)
            }
        }

    }

}



