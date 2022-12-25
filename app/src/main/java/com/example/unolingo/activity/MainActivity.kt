package com.example.unolingo.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.example.unolingo.R
import com.example.unolingo.adapter.PagerTabAdapter
import com.example.unolingo.fragments.MenuFragment
import com.example.unolingo.model.ForumSummaryEntity
import com.example.unolingo.utils.LessonConnectionHandler
import com.example.unolingo.utils.Utils
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var pagerAdapter: PagerTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseUser = Firebase.auth.currentUser
        if (firebaseUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        setContentView(R.layout.activity_main)

        Utils.forumSummaryList.add(ForumSummaryEntity("Anıl", "Soru 2 hakkında", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("Okan", "Dil öğrenme aktiviteleri tavsiye", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("Çağatay", "İngilizceden sonraki dil?", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("Okan", "Konu dışı", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("Okan", "Kitap öneri", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("Anıl", "Foods and Drinks Soru 5 yardım", "Dün"))
        Utils.forumSummaryList.add(ForumSummaryEntity("Çağatay", "İngilizce film önerisi", "Dün"))

        Log.d(TAG, "onCreate: added elements to forum size: " + Utils.forumSummaryList.size)
        viewPager = findViewById(R.id.main_pager)
        tabLayout = findViewById(R.id.main_tab_layout)

        pagerAdapter = PagerTabAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter

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

    val questionScoreResult =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val intent = it.data
            val lesson = intent?.getStringExtra(MenuFragment.LESSON_ID)
            val score = intent?.getIntExtra(MenuFragment.SCORE_RETURN, 0)
            val isCompleted = intent?.getBooleanArrayExtra(MenuFragment.IS_COMPLETED)

            if (lesson != null || score != null || isCompleted != null){
                Log.d(TAG, "on activity result. Launching result activity: $lesson, $score, $isCompleted")
                val transferIntent = Intent(this, ResultActivity::class.java)
                transferIntent.putExtra(MenuFragment.LESSON_ID, lesson)
                transferIntent.putExtra(MenuFragment.SCORE_RETURN, score)
                transferIntent.putExtra(MenuFragment.IS_COMPLETED, isCompleted)
                startActivity(transferIntent)
            }
        }
    }
}