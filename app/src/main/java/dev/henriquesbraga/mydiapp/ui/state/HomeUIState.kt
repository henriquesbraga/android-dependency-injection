package dev.henriquesbraga.mydiapp.ui.state

import dev.henriquesbraga.mydiapp.model.Post

data class HomeUiState(
    val posts: List<Post> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null,
    val reload: Int = 0
)

