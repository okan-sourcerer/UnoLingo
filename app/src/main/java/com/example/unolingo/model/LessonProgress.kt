package com.example.unolingo.model

data class LessonProgress(var uid: String, var lessonID: String, var completed: Boolean, var score: Int, var accessible: Boolean){

    constructor() : this("", "", false, 0, false)
}
