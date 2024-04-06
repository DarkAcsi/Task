package com.example.task.requests

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val idCategory: Int,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String,
)

@Serializable
data class Categories(
    val categories: List<Category>
)