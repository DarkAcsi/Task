package com.example.task

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.adapters.BannersAdapter
import com.example.task.adapters.ViewPagerAdapter
import com.example.task.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var citiesAdapter: ArrayAdapter<String>
    private lateinit var bannersAdapter: BannersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        viewPagerAdapter = ViewPagerAdapter(requireActivity())
        initialCitiesSpinner()
        initialBannersAdapter()

        bannersAdapter.items = getBanners()

    }

    private fun initialCitiesSpinner() {

        citiesAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_city,
            getCities()
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

    private fun getCities(): List<String> {
        return listOf(
            "Москва",
            "Нижний Новгород",
            "Казань",
            "Санкт-Петербург",
        )
    }

    private fun getBanners(): List<String> {
        return listOf(
            "https://s3-alpha-sig.figma.com/img/0e5f/8769/bbdcde65e7e95920e9f71a180817df1a?Expires=1713139200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=kEt2jBoCKntC8RFvfuwgGSJNm4yslKYtS4TmFC8ZFcgU39LOGiMVduXq6Njwpm-ALfzM7vW0jnvmhqhls0iTPt6QpClCyH37xWW34t8V5773ZFJz86~-Fe1OIl9wPGt8A14XSsM2FZX~OBnasz96C43LvJR-y9iHLnyw5FMfLNheYbrQ0-pmjRRW2x0sOWYpyqg2f2Wg5jWLwo98stZ5ixgp3Lg~IjJ-GK9CQfhinObcQlxnn5EUj4XCPcdLMeuuMXe82pwqdYpQPJMlFq1pC2e77Ka4nE9FSvH0L7C4EUqlvtZZCrEEb3DWP02Po4NfKx6v2WhJ8mxSeX3TzQ-7IA__",
            "https://s3-alpha-sig.figma.com/img/b42b/b92c/f6acf7e8e259819d3dd44499cb49eb54?Expires=1713139200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=e9YP2Rme7pvV6O9WCzoKM2CD5R0NQ0ErodQrbADFu1diuZcTZkAjr~YX0NKBXLgtj0OKNkqWEZs4DaiFzSpNiCWlk12~jJ~qYjEswzCCE0iShsnzS4HSzTLNk6V7Q1z-tCToP0jTOpgUQRebOn9aMf-YHcSHtwtuywrdNGsAHzMKiQ6-u~4c87Hd8xwlfQYDZypdr0yNnGBNjaujzxcm6wdlg-rbM8qOuQQgdduGA0O7PVxzCA85b7VNkcg-nlvJFQw72ccpTWF~-wHbbwBvW3V5fBPVyNnwfw3lnjWKdUcRPwI3cUYTlT3vnaQn~PMt9cCtEmwC9syk0AGqWDUU8Q__",
        )
    }

}