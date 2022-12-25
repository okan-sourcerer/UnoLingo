package com.example.unolingo.model

data class LessonProgress(var uid: String, var lessonID: String, var isCompleted: Boolean, var score: Int, var isAccessible: Boolean){

    constructor() : this("", "", false, 0, false)
}
