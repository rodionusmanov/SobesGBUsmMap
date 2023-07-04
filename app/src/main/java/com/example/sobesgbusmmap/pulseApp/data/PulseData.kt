package com.example.sobesgbusmmap.pulseApp.data

data class PulseData(
    var date: String = "",
    var pulseMorning: String = "",
    var pressureUpMorning: String = "",
    var pressureDownMorning: String = "",
    var timeMorning: String = "",
    var pulseEvening: String = "",
    var pressureUpEvening: String = "",
    var pressureDownEvening: String = "",
    var timeEvening: String = ""
)