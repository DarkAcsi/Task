package com.example.task.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task.MenuPageFragment

class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    private val arr = listOf<Fragment>(MenuPageFragment(), MenuPageFragment(), MenuPageFragment())
    override fun getItemCount(): Int = arr.size

    override fun createFragment(position: Int): Fragment = arr[position]
}