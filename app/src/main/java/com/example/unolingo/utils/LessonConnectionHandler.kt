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
        db.collection("themesAndCourses").addSnapshotListener { value, error ->
            if (error != null){
                Log.d(TAG, "retrieveLessons: error retrieving lessons $error")
                return@addSnapshotListener
            }
            if (value != null) {
                Log.d(TAG, "firestore getting lessons: got " + value.size() + " items")
                lessonList.removeAll { true }
                for (document in value.iterator()){
                    val lesson = document.toObject<Lesson>()
                    lessonList.add(lesson)
                }
                adapter.list = lessonList
                adapter.notifyDataSetChanged()
            }
        }
    }
}