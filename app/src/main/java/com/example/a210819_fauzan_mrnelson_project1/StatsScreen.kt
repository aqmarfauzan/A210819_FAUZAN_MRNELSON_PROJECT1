package com.example.a210819_fauzan_mrnelson_project1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a210819_fauzan_mrnelson_project1.ui.theme.*
import androidx.compose.ui.text.font.FontWeight
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(navController: NavHostController, viewModel: SolarViewModel) {
    val userData by viewModel.userData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Bar
        TopAppBar(
            title = { Text("📊 Detailed Stats") },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryGreen)
        )

        // User Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = LightGreen.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "👤 User Information",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryGreen
                )
                Text("Username: ${userData.username}")
                Text("House No: ${userData.houseNo}")
            }
        }

        // Energy Stats Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "⚡ Energy Statistics",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryGreen
                )

                // Row 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Energy Used:")
                    Text(userData.energyUsed, fontWeight = FontWeight.Bold)
                }

                // Row 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Solar Generated:")
                    Text(userData.solarGenerated, fontWeight = FontWeight.Bold)
                }

                // ✅ DAILY TARGET - NEW!
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Daily Target:")
                    Text(userData.dailyTarget, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                }

                // Row 4
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Battery:")
                    Text(userData.battery, fontWeight = FontWeight.Bold)
                }

                // Row 5
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("CO₂ Saved:")
                    Text(userData.co2Saved, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Back Button
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("← Back to Home")
        }
    }
}