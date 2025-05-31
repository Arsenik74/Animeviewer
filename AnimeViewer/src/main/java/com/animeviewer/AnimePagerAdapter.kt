package com.animeviewer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AnimePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AnimeListFragment.newInstance("anime-sama")
            1 -> AnimeListFragment.newInstance("voiranime")
            2 -> AnimeListFragment.newInstance("franime")
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
} 