package com.example.unolingo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.unolingo.R
import com.example.unolingo.adapter.PagerTabAdapter
import com.example.unolingo.model.ForumSummaryEntity
import com.example.unolingo.utils.Utils
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseUser = Firebase.auth.currentUser
        if (firebaseUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

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