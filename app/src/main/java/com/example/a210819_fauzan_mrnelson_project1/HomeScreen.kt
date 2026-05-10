package com.example.a210819_fauzan_mrnelson_project1

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a210819_fauzan_mrnelson_project1.ui.theme.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun HomeScreen(navController: NavHostController, viewModel: SolarViewModel) {
    val userData by viewModel.userData.collectAsState()
    val energyLogs by viewModel.energyLogs.collectAsState()   // NEW: observe logs count

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            BottomNavBar(
                onStatsClick   = { navController.navigate("stats") },
                onLogListClick = { navController.navigate("log_list") },   // NEW
                onSettingsClick = { navController.navigate("settings") }   // FIXED (was empty before)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection(userData.username, userData.houseNo)
            StatsSection()
            TabSection()
            DashboardSection(viewModel)

            // ---- NEW: Quick action buttons ----
            QuickActionsSection(
                logsCount  = energyLogs.size,
                onAddLog   = { navController.navigate("add_log") },
                onViewLogs = { navController.navigate("log_list") }
            )

            ExtraSection()
        }
    }
}

// ---- NEW: Quick action section ----
@Composable
fun QuickActionsSection(logsCount: Int, onAddLog: () -> Unit, onViewLogs: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Quick Actions", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onAddLog,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("➕ Add Log", color = TextWhite)
            }

            OutlinedButton(
                onClick = onViewLogs,
                modifier = Modifier.weight(1f)
            ) {
                Text("📋 View Logs ($logsCount)")
            }
        }
    }
}

@Composable
fun HeaderSection(username: String, houseNo: String) {
    Box(modifier = Modifier.fillMaxWidth().height(260.dp)) {
        Image(
            painter = painterResource(id = R.drawable.solar_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, DarkGreen.copy(alpha = 0.7f))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Welcome, $username (House $houseNo)", color = TextWhite)
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .background(PrimaryGreen),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("60 W", fontSize = 22.sp, color = TextWhite)
                    Text("Solar Now", fontSize = 12.sp, color = TextWhite)
                }
            }
        }
    }
}

@Composable
fun StatsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGreen.copy(alpha = 0.2f))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatItem("Month", "0 Wh")
        StatItem("Year", "0 Wh")
        StatItem("Total", "40.4 MWh")
    }
}

@Composable
fun StatItem(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title)
        Text(value, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
    }
}

@Composable
fun TabSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryGreen)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("Day", "Week", "Month", "Year", "Billing").forEach {
            Text(it, color = TextWhite)
        }
    }
}

@Composable
fun DashboardSection(viewModel: SolarViewModel) {
    val userData by viewModel.userData.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Energy Overview", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            ExpandableCard("Energy Used", userData.energyUsed) {}
            ExpandableCard("Solar Generated", userData.solarGenerated) {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            ExpandableCard("Battery", userData.battery) {}
            ExpandableCard("CO₂ Saved", userData.co2Saved) {}
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Set Daily Target", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = userData.dailyTarget,
            onValueChange = viewModel::updateDailyTarget,
            label = { Text("Daily Solar Target (kWh)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun ExpandableCard(title: String, value: String, onClick: () -> Unit = {}) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable {
                expanded = !expanded
                onClick()
            }
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = LightGreen.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title)
            Text(value, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

            if (expanded) {
                Text("Extra solar info...")
            }
        }
    }
}

@Composable
fun ExtraSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tips 🌱", style = MaterialTheme.typography.titleLarge)
        Text("Use solar during peak sunlight for max efficiency.")
    }
}

// UPDATED: Settings button now works
@Composable
fun BottomNavBar(
    onStatsClick: () -> Unit,
    onLogListClick: () -> Unit,   // NEW
    onSettingsClick: () -> Unit   // FIXED
) {
    NavigationBar(containerColor = PrimaryGreen) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Home, null, tint = TextWhite) },
            label = { Text("Home", color = TextWhite) }
        )
        NavigationBarItem(
            selected = false,
            onClick = onStatsClick,
            icon = { Icon(Icons.Default.Star, null, tint = TextWhite) },
            label = { Text("Stats", color = TextWhite) }
        )
        NavigationBarItem(
            selected = false,
            onClick = onLogListClick,          // NEW
            icon = { Icon(Icons.Default.List, null, tint = TextWhite) },
            label = { Text("Logs", color = TextWhite) }
        )
        NavigationBarItem(
            selected = false,
            onClick = onSettingsClick,         // FIXED
            icon = { Icon(Icons.Default.Settings, null, tint = TextWhite) },
            label = { Text("Settings", color = TextWhite) }
        )
    }
}