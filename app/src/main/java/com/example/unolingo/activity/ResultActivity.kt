package com.example.unolingo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.example.unolingo.R
import com.example.unolingo.fragments.MenuFragment

class ResultActivity : AppCompatActivity() {

    private lateinit var lesson: TextView
    private lateinit var score: TextView
    private lateinit var isCompleted: TextView

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val lessonName = intent.getStringExtra(MenuFragment.LESSON_ID)
        val lessonScore = intent.getIntExtra(MenuFragment.SCORE_RETURN, 0)
        val lessonCompletion = intent.getBooleanExtra(MenuFragment.LESSON_ID, false)

        lesson = findViewById(R.id.result_lesson)
        score = findViewById(R.id.result_score)
        isCompleted = findViewById(R.id.result_isCompleted)

        button = findViewById(R.id.result_button)

        lesson.text = lessonName
        score.text = lessonScore.toString()
        if (lessonScore > 60){
            isCompleted.text = "Congratulations! Lesson is completed!"
        }else{
            isCompleted.text = "Lesson is failed. Please enter again to test yourself."
        }

        button.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            finish()
        }

        return true
    }
}