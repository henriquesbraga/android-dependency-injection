package dev.henriquesbraga.mydiapp.repository

import dev.henriquesbraga.mydiapp.model.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class PostsRepository @Inject constructor (
    private val client: HttpClient
) {
    suspend fun fetchPosts(): List<Post> =
        client.get("https://jsonplaceholder.typicode.com/posts").body()
}