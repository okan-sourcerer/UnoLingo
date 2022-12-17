package com.example.unolingo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.unolingo.fragments.ForumFragment
import com.example.unolingo.fragments.MenuFragment
import com.example.unolingo.fragments.ProfileFragment

class PagerTabAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return 3 // first is home screen fragment, second is forum screen, last is settings fragment
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when(position){
            2 -> ProfileFragment.newInstance()
            1 -> ForumFragment.newInstance()
            else -> MenuFragment.newInstance() // this is Home screen.
        }
        return fragment
    }
}