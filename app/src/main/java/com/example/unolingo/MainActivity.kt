package com.example.unolingo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.unolingo.adapter.PagerTabAdapter
import com.example.unolingo.model.ForumSummaryEntity
import com.example.unolingo.model.Lesson
import com.example.unolingo.utils.Utils
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Utils.forumSummaryList.add(ForumSummaryEntity("asd", "Güzel Başlık", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("asd", "Güzel Başlık", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("asd", "Güzel Başlık", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("asd", "Güzel Başlık", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("asd", "Güzel Başlık", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("asd", "Güzel Başlık", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("asd", "Güzel Başlık", "Dün"))

        Log.d(TAG, "onCreate: added elements to forum size: " + Utils.forumSummaryList.size)
        viewPager = findViewById(R.id.main_pager)
        tabLayout = findViewById(R.id.main_tab_layout)

        viewPager.adapter = PagerTabAdapter(supportFragmentManager, lifecycle)

        // handle tab select actions
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // get content list by filtering based on tabs
                // replace current fragment to new tab fragment
                viewPager.setCurrentItem(tab!!.position, true)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {} })

        // when page changes, change current tab as well
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}