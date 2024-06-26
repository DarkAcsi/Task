package com.example.task

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.requests.Categories
import com.example.task.requests.Category
import com.example.task.requests.ItemDish
import com.example.task.requests.ItemDishes
import com.example.task.requests.RequestsInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileReader
import java.io.FileWriter


class MenuPageViewModel: ViewModel() {

    private var requestsInterface: RequestsInterface

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _dishes = MutableLiveData<List<ItemDish>>()
    val dishes: LiveData<List<ItemDish>> = _dishes

    private var orderRequest: Long = 0
    private var selectedCategory = Category(0, "", "", "")

    init {
        val retrofit = Retrofit.Builder().baseUrl("https://www.themealdb.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        requestsInterface = retrofit.create(RequestsInterface::class.java)
//        getCategories()
    }

    fun loadData(context: Context, isConnection: Boolean){
        if (isConnection){
            getCategories(context, isConnection)
        } else {
            val fileSelectedCategory = FileReader(File(context.filesDir, "selected_category.txt"))
            val fileCategories = FileReader(File(context.filesDir, "categories.txt"))
            val fileDishes = FileReader(File(context.filesDir, "dishes.txt"))
            selectedCategory = Json.decodeFromString<Category>(fileSelectedCategory.readText())
            _categories.postValue(Json.decodeFromString<Categories>(fileCategories.readText()).categories)
            _dishes.postValue(Json.decodeFromString<ItemDishes>((fileDishes.readText())).meals)
            fileSelectedCategory.close()
            fileCategories.close()
            fileDishes.close()
        }
    }

    fun getCities(): List<String> {
        return listOf(
            "Москва",
            "Нижний Новгород",
            "Казань",
            "Санкт-Петербург",
        )
    }

    fun getBanners(): List<String> {
        return listOf(
            "https://s3-alpha-sig.figma.com/img/0e5f/8769/bbdcde65e7e95920e9f71a180817df1a?Expires=1713139200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=kEt2jBoCKntC8RFvfuwgGSJNm4yslKYtS4TmFC8ZFcgU39LOGiMVduXq6Njwpm-ALfzM7vW0jnvmhqhls0iTPt6QpClCyH37xWW34t8V5773ZFJz86~-Fe1OIl9wPGt8A14XSsM2FZX~OBnasz96C43LvJR-y9iHLnyw5FMfLNheYbrQ0-pmjRRW2x0sOWYpyqg2f2Wg5jWLwo98stZ5ixgp3Lg~IjJ-GK9CQfhinObcQlxnn5EUj4XCPcdLMeuuMXe82pwqdYpQPJMlFq1pC2e77Ka4nE9FSvH0L7C4EUqlvtZZCrEEb3DWP02Po4NfKx6v2WhJ8mxSeX3TzQ-7IA__",
            "https://s3-alpha-sig.figma.com/img/b42b/b92c/f6acf7e8e259819d3dd44499cb49eb54?Expires=1713139200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=e9YP2Rme7pvV6O9WCzoKM2CD5R0NQ0ErodQrbADFu1diuZcTZkAjr~YX0NKBXLgtj0OKNkqWEZs4DaiFzSpNiCWlk12~jJ~qYjEswzCCE0iShsnzS4HSzTLNk6V7Q1z-tCToP0jTOpgUQRebOn9aMf-YHcSHtwtuywrdNGsAHzMKiQ6-u~4c87Hd8xwlfQYDZypdr0yNnGBNjaujzxcm6wdlg-rbM8qOuQQgdduGA0O7PVxzCA85b7VNkcg-nlvJFQw72ccpTWF~-wHbbwBvW3V5fBPVyNnwfw3lnjWKdUcRPwI3cUYTlT3vnaQn~PMt9cCtEmwC9syk0AGqWDUU8Q__",
        )
    }

    private fun getCategories(context: Context, isConnection: Boolean) {
        viewModelScope.launch {
            val allCategories =
                withContext(Dispatchers.IO) { requestsInterface.getCategories().categories }
            _categories.postValue(allCategories)

            val fileCategories = FileWriter(File(context.filesDir, "categories.txt"))
            fileCategories.write(Json.encodeToString(Categories(allCategories)))
            fileCategories.close()

            getDishes(context, allCategories[0], isConnection)
        }
    }

    fun getDishes(context: Context, category: Category, isConnection: Boolean) {
        selectedCategory = category
        if (!isConnection) {return}
        viewModelScope.launch {
            orderRequest += 1
            val order = orderRequest
            val listDishes = mutableListOf<ItemDish>()
            val simpleDishes = withContext(Dispatchers.IO) {
                requestsInterface.getDishes(
                    category.strCategory
                )
            }
            simpleDishes.meals.forEachIndexed() { index, it ->
                if (order != orderRequest) cancel()
                val aboutDish =
                    withContext(Dispatchers.IO) { requestsInterface.getDishById(it.idMeal).meals[0] }
                listDishes.add(aboutDish.toItemDish())
                val fileDishes = FileWriter(File(context.filesDir, "dishes.txt"))
                fileDishes.write(Json.encodeToString(ItemDishes(listDishes)))
                fileDishes.close()
                if (index % 8 == 7) {
                    _dishes.postValue(listDishes)
                    Log.d("sdfsdf", index.toString())
                }
            }
            _dishes.postValue(listDishes)
        }
    }
}