package com.example.unolingo.utils

import android.util.Log
import com.example.unolingo.adapter.LessonAdapter
import com.example.unolingo.model.Lesson
import com.example.unolingo.model.LessonProgress
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

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

                db.collection("userProgress").addSnapshotListener { value, error ->
                    if (error != null){
                        Log.d(TAG, "retrieveLessons: error retrieving lessons $error")
                    }
                    else{
                        if (value != null) {
                            Log.d(TAG, "firestore getting lessons: got " + value.size() + " items")
                            progressList.removeAll { true }
                            for (document in value.iterator()){
                                val progress = document.toObject<LessonProgress>()
                                progressList.add(progress)
                            }
                        }
                    }
                    adapter.list = lessonList
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    val progressList = ArrayList<LessonProgress>()

    fun insertInitialLessonProgress(){
        val db = FirebaseFirestore.getInstance()
        val lessons = ArrayList<Lesson>()
        val progresses = ArrayList<LessonProgress>()
        db.collection("themesAndCourses").addSnapshotListener { value, error ->
            if (error != null) {
                Log.d(TAG, "retrieveLessons: error retrieving lessons $error")
                return@addSnapshotListener
            }
            if (value != null) {
                Log.d(TAG, "firestore getting lessons: got " + value.size() + " items")
                lessons.removeAll { true }
                for (index in value.iterator().withIndex()) {
                    val document = value.documents[index.index]
                    val lesson = document.toObject<Lesson>()
                    if (lesson != null) {
                        lessons.add(lesson)
                        for (course in lesson.lessons) {
                            var isAccessible = false
                            if (index.index == 0){
                                isAccessible = true
                            }
                            val progress =
                                Firebase.auth.uid?.let { LessonProgress(it, course, false, 0, isAccessible) }
                            if (progress != null) {
                                progresses.add(progress)
                                val map = hashMapOf(
                                    "uid" to progress.uid,
                                    "lessonID" to progress.lessonID,
                                    "isCompleted" to progress.isCompleted,
                                    "score" to progress.score,
                                    "isAccessible" to progress.isAccessible
                                )
                                db.collection("userProgress").add(map).addOnSuccessListener {
                                    Log.d(TAG, "insertInitialLessonProgress: progress is created and added $progress")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}