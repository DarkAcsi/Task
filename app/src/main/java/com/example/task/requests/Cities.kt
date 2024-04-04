package com.example.task.requests

data class Cities(
    val meals: List<City>
){
    fun toStrings(): List<String> {
        return this.meals.map{ it.strArea }
    }
}

data class City(
    val strArea: String,
)