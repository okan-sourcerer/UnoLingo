package com.example.unolingo.utils

import android.util.Log
import com.example.unolingo.adapter.LessonAdapter
import com.example.unolingo.model.Lesson
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

object LessonConnectionHandler {

    private val TAG = "LessonConnectionHandler"

    val lessonList = ArrayList<Lesson>()

    fun retrieveLessons(adapter: LessonAdapter){
        val db = FirebaseFirestore.getInstance()
        db.collection("themesAndCourses").get().addOnCompleteListener{ result ->
            Log.d(TAG, "firestore getting lessons: got " + result.result.size() + " items")
            lessonList.removeAll { true }
            for (document in result.result.iterator()){
                val lesson = document.toObject<Lesson>()
                lessonList.add(lesson)
            }
            adapter.list = lessonList
            adapter.notifyDataSetChanged()
        }
    }
    init{


    }
}