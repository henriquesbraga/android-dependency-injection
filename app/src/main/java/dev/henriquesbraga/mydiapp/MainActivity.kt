package dev.henriquesbraga.mydiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.henriquesbraga.mydiapp.repository.PostsRepository
import dev.henriquesbraga.mydiapp.ui.pages.HomeViewModel
import dev.henriquesbraga.mydiapp.ui.pages.HomeWithViewModel
import dev.henriquesbraga.mydiapp.ui.theme.MydiappTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {

    val client: HttpClient by lazy {
        HttpClient(OkHttp) {
            expectSuccess = true

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 30_000
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MydiappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val homeViewModel: HomeViewModel by viewModels {
                        HomeViewModel.factory(repository = PostsRepository(client = client))
                    }
                    HomeWithViewModel(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = homeViewModel
                    )
                }
            }
        }
    }
}