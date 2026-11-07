package dev.henriquesbraga.mydiapp.ui.pages


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeWithViewModel(modifier: Modifier = Modifier, viewModel: HomeViewModel) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Posts", style = MaterialTheme.typography.headlineLarge)
        Button(onClick = { viewModel.fetchPosts() }, enabled = !uiState.loading) {
            Text(if (uiState.error != null) "Tentar novamente" else "Atualizar posts")
        }

        when {
            uiState.loading -> {
                CircularProgressIndicator()
                Text("Carregando posts…")
            }
            uiState.error != null -> Text(
                text = uiState.error.orEmpty(),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            )
            uiState.posts.isEmpty() -> Text("Nenhum post encontrado.")
            else -> LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(uiState.posts, key = { it.id }) { post ->
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(post.title, style = MaterialTheme.typography.titleMedium)
                        Text(post.body, style = MaterialTheme.typography.bodyLarge)
                    }
                    HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
                }
            }
        }
    }
}