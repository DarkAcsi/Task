package com.example.task.requests

data class Dish(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: Long,
)

data class Dishes(
    val meals: List<Dish>,
)

data class AboutDish(
    val idMeal: Long,
    val strMeal: String,
    val strDrinkAlternate: Any?,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String,
    val strTags: String,
    val strYoutube: String,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strIngredient16: String?,
    val strIngredient17: String?,
    val strIngredient18: String?,
    val strIngredient19: String?,
    val strIngredient20: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strMeasure16: String?,
    val strMeasure17: String?,
    val strMeasure18: String?,
    val strMeasure19: String?,
    val strMeasure20: String?,
    val strSource: Any?,
    val strImageSource: Any?,
    val strCreativeCommonsConfirmed: Any?,
    val dateModified: Any?,
) {
    fun toItemDish(): ItemDish {
        val listIngredients = listOf(
            this.strIngredient1,
            this.strIngredient2,
            this.strIngredient3,
            this.strIngredient4,
            this.strIngredient5,
            this.strIngredient6,
            this.strIngredient7,
            this.strIngredient8,
            this.strIngredient9,
            this.strIngredient10,
            this.strIngredient11,
            this.strIngredient12,
            this.strIngredient13,
            this.strIngredient14,
            this.strIngredient15,
            this.strIngredient16,
            this.strIngredient17,
            this.strIngredient18,
            this.strIngredient19,
            this.strIngredient20
        ).filter{it -> !it.isNullOrBlank()}
        return ItemDish(
            this.idMeal,
            this.strMeal,
            this.strMealThumb,
            listIngredients.joinToString(", ")
        )
    }
}

data class AboutDishes(
    val meals: List<AboutDish>
)

data class ItemDish(
    val idMeal: Long,
    val strMeal: String,
    val strMealThumb: String,
    val ingredients: String,
)
