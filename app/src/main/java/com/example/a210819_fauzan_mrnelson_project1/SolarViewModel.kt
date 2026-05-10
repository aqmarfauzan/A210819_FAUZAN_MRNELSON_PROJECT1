package com.example.a210819_fauzan_mrnelson_project1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Data class for each energy log entry
data class EnergyLog(
    val id: Int,
    val date: String,
    val solarGenerated: String,
    val energyUsed: String,
    val notes: String
)

class SolarViewModel : ViewModel() {

    // --- Existing UserData state ---
    private val _userData = MutableStateFlow(UserData())
    val userData: StateFlow<UserData> = _userData.asStateFlow()

    fun updateUsername(name: String) {
        _userData.value = _userData.value.copy(username = name)
    }

    fun updateHouseNo(house: String) {
        _userData.value = _userData.value.copy(houseNo = house)
    }

    fun updateDailyTarget(target: String) {
        _userData.value = _userData.value.copy(dailyTarget = target)
    }

    // --- NEW: Energy Log List state ---
    private val _energyLogs = MutableStateFlow<List<EnergyLog>>(emptyList())
    val energyLogs: StateFlow<List<EnergyLog>> = _energyLogs.asStateFlow()

    private var nextId = 1

    // ADD a new log entry
    fun addEnergyLog(date: String, solarGenerated: String, energyUsed: String, notes: String) {
        val newLog = EnergyLog(
            id = nextId++,
            date = date,
            solarGenerated = solarGenerated,
            energyUsed = energyUsed,
            notes = notes
        )
        _energyLogs.value = _energyLogs.value + newLog
    }

    // DELETE a log entry
    fun deleteEnergyLog(id: Int) {
        _energyLogs.value = _energyLogs.value.filter { it.id != id }
    }

    // --- NEW: Settings state ---
    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled.asStateFlow()

    fun toggleNotifications(enabled: Boolean) {
        _notificationsEnabled.value = enabled
    }

    fun toggleDarkMode(enabled: Boolean) {
        _darkModeEnabled.value = enabled
    }
}