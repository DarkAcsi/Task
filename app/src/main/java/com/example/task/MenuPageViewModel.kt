package com.example.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.requests.Category
import com.example.task.requests.ItemDish
import com.example.task.requests.RequestsInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MenuPageViewModel: ViewModel() {

    private var requestsInterface: RequestsInterface

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _dishes = MutableLiveData<List<ItemDish>>()
    val dishes: LiveData<List<ItemDish>> = _dishes

    private var orderRequest: Long = 0

    init {
        val retrofit = Retrofit.Builder().baseUrl("https://www.themealdb.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        requestsInterface = retrofit.create(RequestsInterface::class.java)
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            val allCategories =
                withContext(Dispatchers.IO) { requestsInterface.getCategories().categories }
            _categories.postValue(allCategories)
            getDishes(allCategories[0])
        }
    }

    fun getDishes(category: Category?) {
        viewModelScope.launch {
            orderRequest += 1
            val order = orderRequest
            val listDishes = mutableListOf<ItemDish>()
            val simpleDishes = withContext(Dispatchers.IO) {
                requestsInterface.getDishes(
                    category?.strCategory ?: ""
                )
            }
            simpleDishes.meals.forEachIndexed() { index, it ->
                if (order != orderRequest) cancel()
                val aboutDish =
                    withContext(Dispatchers.IO) { requestsInterface.getDishById(it.idMeal).meals[0] }
                listDishes.add(aboutDish.toItemDish())
                if (index % 8 == 7) {
                    _dishes.postValue(listDishes)
                }
                _dishes.postValue(listDishes)
            }
        }
    }
}