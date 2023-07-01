package com.example.sobesgbusmmap.eduApp.domain

data class Lesson(
    val lessonName: String,
    val lessonTimeInterval: String,
    val isSkype: Boolean,
    val teacher: String
)