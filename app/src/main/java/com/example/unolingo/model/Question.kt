package com.example.unolingo.model

data class Question(var questionNum: Int, var answer: String, var lessonID: String,
                    var option1: String, var option2: String, var option3: String, var option4: String,
                    var question: String, var type: Int, var typeSpecificQuestion: String, var filePaths: ArrayList<String>) {

    constructor(): this(0,"","","","","","","",0,"", arrayListOf())
}
