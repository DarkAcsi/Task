package com.example.task

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.adapters.CategoriesAdapter
import com.example.task.adapters.CategoryListener
import com.example.task.adapters.DishesAdapter
import com.example.task.requests.Category
import com.example.task.databinding.FragementMenuPageBinding


class MenuPageFragment : Fragment(R.layout.fragment_home_page) {

    private lateinit var binding: FragementMenuPageBinding
    val viewModel by viewModels<MenuPageViewModel>()
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var dishesAdapter: DishesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragementMenuPageBinding.bind(view)
        initialCategoriesAdapter()
        initialDishesAdapter()

        viewModel.categories.observe(viewLifecycleOwner) {
            categoriesAdapter.items = it
        }
        viewModel.dishes.observe(viewLifecycleOwner) {
            dishesAdapter.items = it
            dishesAdapter.notifyDataSetChanged()
        }

    }

    private fun initialCategoriesAdapter() {
        categoriesAdapter = CategoriesAdapter(requireContext(), object : CategoryListener {
            override fun changeCategory(category: Category) {
                viewModel.getDishes(category)
            }
        })
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
        layoutManager.reverseLayout = false
        binding.listCategories.layoutManager = layoutManager
        binding.listCategories.adapter = categoriesAdapter
    }

    private fun initialDishesAdapter() {
        dishesAdapter = DishesAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.reverseLayout = false
        binding.listDishes.layoutManager = layoutManager
        binding.listDishes.adapter = dishesAdapter
    }

}