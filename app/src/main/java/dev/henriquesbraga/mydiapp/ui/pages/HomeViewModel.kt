package dev.henriquesbraga.mydiapp.ui.pages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.henriquesbraga.mydiapp.repository.PostsRepository
import dev.henriquesbraga.mydiapp.ui.state.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class HomeViewModel (
    private val repository: PostsRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null)
            }
            try {
                val posts = repository.fetchPosts()
                _uiState.update { it.copy(loading = false, posts = posts) }
            }
            catch (e: CancellationException) {
                throw e
            }
            catch (e: Exception) {
                _uiState.update { it.copy(loading = false, error = e.message) }
            }
        }
    }



    companion object {

        fun factory(repository: PostsRepository) = viewModelFactory {
            initializer {
                HomeViewModel(repository = repository)
            }
        }
    }

}