package com.plcoding.composepaging3caching.domain

//Data class that will represent a beer
data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    val firstBrewed: String,
    val description: String,
    val imageUrl: String?
)
