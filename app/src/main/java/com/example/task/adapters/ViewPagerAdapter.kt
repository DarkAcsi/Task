package com.example.task.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: FragmentActivity?) : FragmentStateAdapter(fragment!!) {
    private val list: MutableList<Fragment> = ArrayList()

    fun addFragment(fragment: Fragment) {
        list.add(fragment)
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}