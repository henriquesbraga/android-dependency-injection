package dev.henriquesbraga.mydiapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val title: String,
    val body: String,
)
