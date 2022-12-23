package com.example.unolingo.utils

import android.util.Log
import com.example.unolingo.model.ForumSummaryEntity
import com.example.unolingo.model.Lesson
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

object Utils {

    private val TAG = "Utils"

    val forumSummaryList: ArrayList<ForumSummaryEntity> = ArrayList()

    val lessonList = ArrayList<Lesson>()

    var userName: String = ""

    init{

        val db = FirebaseFirestore.getInstance()
        db.collection("themesAndCourses").get().addOnCompleteListener{ result ->
            Log.d(TAG, "firestore getting lessons: got " + result.result.size() + " items")
            for (document in result.result.iterator()){
                val lesson = document.toObject<Lesson>()
                lessonList.add(lesson)
            }
        }
    }
}