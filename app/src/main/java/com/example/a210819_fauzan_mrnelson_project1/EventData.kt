package com.example.a210819_fauzan_mrnelson_project1

data class UserData(
    val username: String = "",
    val houseNo: String = "",
    val energyUsed: String = "1.07 kWh",
    val solarGenerated: String = "2.45 kWh",
    val battery: String = "78%",
    val co2Saved: String = "3.2 kg",
    val dailyTarget: String = "5.0 kWh"
)

