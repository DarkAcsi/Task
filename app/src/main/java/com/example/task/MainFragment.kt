package com.example.task

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.task.adapters.ViewPagerAdapter
import com.example.task.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        val adapter = ViewPagerAdapter(requireActivity())
        adapter.addFragment(MenuPageFragment())
        binding.pagerMain.setAdapter(adapter)
        binding.pagerMain.setUserInputEnabled(false);

        binding.bottomMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemMenu -> binding.pagerMain.setCurrentItem(0, true)
            }
            true
        }
        binding.pagerMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomMenu.selectedItemId = R.id.itemMenu
                }
            }
        })
    }
}