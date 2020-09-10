package com.example.bnccapplication.lookup

data class LookupData(
    val provinceName : String,
    val numberOfConfirmedCases : Int,
    val numberOfRecoveredCases : Int,
    val numberOfDeathCases: Int
)