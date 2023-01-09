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

                db.collection("userProgress").whereEqualTo("uid", Firebase.auth.uid.toString()).addSnapshotListener { progressValue, progressError ->
                    if (progressError != null){
                        Log.d(TAG, "retrieveLessons: error retrieving lessons $progressError")
                    }
                    else{
                        if (progressValue != null) {
                            Log.d(TAG, "firestore getting lessons: got " + progressValue.size() + " items")
                            progressList.removeAll { true }
                            for (document in progressValue.iterator()){
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

                            if (course == "Meet Foods"){
                                isAccessible = true
                            }
                            val progress =
                                Firebase.auth.uid?.let { LessonProgress(it, course, false, 0, isAccessible) }
                            if (progress != null) {
                                progresses.add(progress)
                                val map = hashMapOf(
                                    "uid" to progress.uid,
                                    "lessonID" to progress.lessonID,
                                    "completed" to progress.completed,
                                    "score" to progress.score,
                                    "accessible" to progress.accessible
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

    fun updateScoreAndLessonStatus(progress: LessonProgress, isSuccess: Boolean){
        val db = FirebaseFirestore.getInstance()

        val map = hashMapOf("uid" to progress.uid,
            "lessonID" to progress.lessonID,
            "completed" to progress.completed,
            "score" to progress.score,
            "accessible" to progress.accessible)

        db.collection("userProgress").whereEqualTo("uid", map["uid"])
            .whereEqualTo("lessonID", map["lessonID"]).get().addOnSuccessListener {
                map["completed"] = isSuccess
                db.collection("userProgress").document(it.documents[0].id).update(map as Map<String, Any>)
            }
    }

    fun updateAccessibleStatus(progress: LessonProgress){
        val db = FirebaseFirestore.getInstance()
        Log.d(TAG, "updateAccessibleStatus: updating accessibility of $progress")
        val map = hashMapOf("uid" to progress.uid,
            "lessonID" to progress.lessonID,
            "completed" to progress.completed,
            "score" to progress.score,
            "accessible" to true)
        db.collection("userProgress").whereEqualTo("lessonID", map["lessonID"])
            .whereEqualTo("uid", map["uid"])
            .get().addOnSuccessListener {
                db.collection("userProgress").document(it.documents[0].id).update(map as Map<String, Any>)
                    .addOnSuccessListener {it2 ->
                        Log.d(TAG, "updateAccessibleStatus: updated accessibility ${it.documents[0]}")
                    }
            }
    }
}