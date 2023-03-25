package com.plcoding.composepaging3caching.data.remote

//Beer Data transfer object
data class BeerDto(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val first_brewed: String,
    val image_url: String?
)
