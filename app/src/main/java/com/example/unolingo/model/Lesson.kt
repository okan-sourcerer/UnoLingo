package com.example.unolingo.model

data class Lesson(var name: String, var lessons: MutableList<String>){
    constructor() : this("", ArrayList<String>())
}
