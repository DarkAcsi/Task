package com.example.task.requests

data class Category(
    val idCategory: Int,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String,
)

data class Categories(
    val categories: List<Category>
)