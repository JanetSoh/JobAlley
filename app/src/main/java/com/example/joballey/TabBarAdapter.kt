package com.example.joballey

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabBarAdapter (activity: FragmentActivity, private val tabCount:Int): FragmentStateAdapter(activity){

    override fun getItemCount(): Int =tabCount

    override fun createFragment(position: Int): Fragment {
        return when (position)
        {
            0->HomePage()
            1->SearchPage()
            2->SavePage()
            3->NotificationPage()
            4->UserPage()
            else ->HomePage()
        }
    }
}