package com.example.a210819_fauzan_mrnelson_project1

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a210819_fauzan_mrnelson_project1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: SolarViewModel) {

    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val userData             by viewModel.userData.collectAsState()
    val energyLogs           by viewModel.energyLogs.collectAsState()
    val context = LocalContext.current

    // ── Edit Profile dialog state ──
    var showEditDialog by remember { mutableStateOf(false) }
    var editUsername   by remember { mutableStateOf("") }
    var editHouseNo    by remember { mutableStateOf("") }

    // ── Daily Target dialog state ──
    var showTargetDialog by remember { mutableStateOf(false) }
    var editDailyTarget  by remember { mutableStateOf("") }

    // ════════════════════════════════
    // DIALOG: Edit Profile
    // ════════════════════════════════
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("✏️ Edit Profile", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = editUsername,
                        onValueChange = { editUsername = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = editHouseNo,
                        onValueChange = { editHouseNo = it },
                        label = { Text("House No") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editUsername.isNotBlank()) viewModel.updateUsername(editUsername)
                        if (editHouseNo.isNotBlank())  viewModel.updateHouseNo(editHouseNo)
                        showEditDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) { Text("Save", color = TextWhite) }
            },
            dismissButton = {
                OutlinedButton(onClick = { showEditDialog = false }) { Text("Cancel") }
            }
        )
    }

    // ════════════════════════════════
    // DIALOG: Daily Target
    // ════════════════════════════════
    if (showTargetDialog) {
        AlertDialog(
            onDismissRequest = { showTargetDialog = false },
            title = { Text("🎯 Set Daily Target", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Current target: ${userData.dailyTarget}",
                        style = MaterialTheme.typography.bodySmall,
                        color = PrimaryGreen,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = editDailyTarget,
                        onValueChange = { editDailyTarget = it },
                        label = { Text("New Target (kWh)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Text("Quick presets:", style = MaterialTheme.typography.bodySmall)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("3.0", "5.0", "8.0", "10.0").forEach { preset ->
                            FilterChip(
                                selected = editDailyTarget == preset,
                                onClick  = { editDailyTarget = preset },
                                label    = { Text(preset) }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editDailyTarget.isNotBlank()) {
                            viewModel.updateDailyTarget("$editDailyTarget kWh")
                        }
                        showTargetDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) { Text("Save", color = TextWhite) }
            },
            dismissButton = {
                OutlinedButton(onClick = { showTargetDialog = false }) { Text("Cancel") }
            }
        )
    }

    // ════════════════════════════════
    // MAIN UI
    // ════════════════════════════════
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("⚙️ Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryGreen,
                    titleContentColor = TextWhite
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ── 1. ACCOUNT INFO + EDIT ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = LightGreen.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "👤 Account Info",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen
                        )
                        // Pencil icon — opens edit dialog
                        IconButton(onClick = {
                            editUsername   = userData.username
                            editHouseNo    = userData.houseNo
                            showEditDialog = true
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = PrimaryGreen)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Username : ${userData.username}")
                    Text("House No : ${userData.houseNo}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Tap ✏️ to edit your profile",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.45f)
                    )
                }
            }

            // ── 2. DAILY TARGET ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "🎯 Daily Solar Target",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Current: ${userData.dailyTarget}",
                            style = MaterialTheme.typography.bodySmall,
                            color = PrimaryGreen,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Set your daily solar generation goal",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            // Strip " kWh" suffix sebelum masuk dialog
                            editDailyTarget = userData.dailyTarget.replace(" kWh", "")
                            showTargetDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) { Text("Set", color = TextWhite) }
                }
            }

            // ── 3. NOTIFICATIONS ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "🔔 Notifications",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Receive daily solar alerts",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { viewModel.toggleNotifications(it) },
                        colors = SwitchDefaults.colors(checkedThumbColor = PrimaryGreen)
                    )
                }
            }

            // ── 4. EXPORT / SHARE ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "📤 Export & Share",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Share your energy summary as a text report",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )

                    // Preview box
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color  = LightGreen.copy(alpha = 0.1f),
                        shape  = MaterialTheme.shapes.small
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "📊 Report Preview",
                                style = MaterialTheme.typography.labelMedium,
                                color = DarkGreen,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("User      : ${userData.username} (House ${userData.houseNo})", style = MaterialTheme.typography.bodySmall)
                            Text("Target    : ${userData.dailyTarget}",                          style = MaterialTheme.typography.bodySmall)
                            Text("Solar     : ${userData.solarGenerated}",                       style = MaterialTheme.typography.bodySmall)
                            Text("Used      : ${userData.energyUsed}",                           style = MaterialTheme.typography.bodySmall)
                            Text("Battery   : ${userData.battery}",                              style = MaterialTheme.typography.bodySmall)
                            Text("CO₂ Saved : ${userData.co2Saved}",                             style = MaterialTheme.typography.bodySmall)
                            Text("Logs      : ${energyLogs.size} entries recorded",              style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    Button(
                        onClick = {
                            // Build full report string
                            val report = buildString {
                                appendLine("🌞 MotherFlame Energy Report")
                                appendLine("================================")
                                appendLine("User        : ${userData.username}")
                                appendLine("House No    : ${userData.houseNo}")
                                appendLine("Daily Target: ${userData.dailyTarget}")
                                appendLine()
                                appendLine("⚡ Energy Overview")
                                appendLine("Solar Generated : ${userData.solarGenerated}")
                                appendLine("Energy Used     : ${userData.energyUsed}")
                                appendLine("Battery         : ${userData.battery}")
                                appendLine("CO₂ Saved       : ${userData.co2Saved}")
                                appendLine()
                                if (energyLogs.isNotEmpty()) {
                                    appendLine("📋 Energy Logs (${energyLogs.size} entries)")
                                    appendLine("--------------------------------")
                                    energyLogs.forEach { log ->
                                        appendLine("📅 ${log.date}")
                                        appendLine("   Solar : ${log.solarGenerated}")
                                        appendLine("   Used  : ${log.energyUsed}")
                                        if (log.notes.isNotBlank()) appendLine("   Notes : ${log.notes}")
                                        appendLine()
                                    }
                                } else {
                                    appendLine("📋 No logs recorded yet.")
                                }
                                appendLine("SDG 7 – Affordable & Clean Energy")
                                appendLine("Generated by MotherFlame v1.0.0")
                            }

                            // Launch Android native share sheet
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "MotherFlame Energy Report – ${userData.username}")
                                putExtra(Intent.EXTRA_TEXT, report)
                            }
                            context.startActivity(Intent.createChooser(intent, "Share Energy Report via..."))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) { Text("📤 Share Report", color = TextWhite) }
                }
            }

            // ── 5. APP INFO ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("ℹ️ App Info", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text("App     : MotherFlame Clean Energy System")
                    Text("SDG     : SDG 7 – Affordable & Clean Energy")
                    Text("Version : 1.0.0")
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // ── 6. LOGOUT ──
            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) { Text("🚪 Logout", color = TextWhite) }
        }
    }
}