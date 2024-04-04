package com.example.task

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.adapters.BannersAdapter
import com.example.task.adapters.CategoriesAdapter
import com.example.task.adapters.CategoryListener
import com.example.task.adapters.DishesAdapter
import com.example.task.databinding.FragmentHomePageBinding
import com.example.task.requests.Category

class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    private lateinit var binding: FragmentHomePageBinding
    val viewModel by viewModels<HomePageViewModel>()

    private lateinit var citiesAdapter: ArrayAdapter<String>
    private lateinit var bannersAdapter: BannersAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var dishesAdapter: DishesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomePageBinding.bind(view)
        initialCitiesSpinner()
        initialBannersAdapter()
        initialCategoriesAdapter()
        initialDishesAdapter()

        bannersAdapter.items = viewModel.getBanners()

        viewModel.categories.observe(viewLifecycleOwner) {
            categoriesAdapter.items = it
        }
        viewModel.dishes.observe(viewLifecycleOwner) {
            dishesAdapter.items = it
            dishesAdapter.notifyDataSetChanged()
        }

    }

    private fun initialCitiesSpinner() {

        citiesAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_city,
            viewModel.getCities()
        )
        citiesAdapter.setDropDownViewResource(R.layout.item_city)
        binding.spinnerCities.adapter = citiesAdapter

        binding.spinnerCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    private fun initialBannersAdapter() {
        bannersAdapter = BannersAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
        layoutManager.reverseLayout = false
        binding.listBanners.layoutManager = layoutManager
        binding.listBanners.adapter = bannersAdapter
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