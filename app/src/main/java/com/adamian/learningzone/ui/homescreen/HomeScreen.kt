package com.adamian.learningzone.ui.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToChapters: (Int) -> Unit,
    viewModel: HomeScreenVM = hiltViewModel()
) {

    val sheetState = rememberModalBottomSheetState()
    var showStatsBottomSheet by remember { mutableStateOf(false) }
    var showSubscriptionBottomSheet by remember { mutableStateOf(false) }
    val appStats by viewModel.appStats.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAppStats()
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LearningZoneAppTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Καλώς ήρθες",
                        style = LearningZoneAppTheme.typography.labelLarge,
                        color = LearningZoneAppTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Καλή μάθηση!",
                        style = LearningZoneAppTheme.typography.titleLarge,
                        color = LearningZoneAppTheme.colorScheme.onBackground
                    )
                }

                appStats?.let {
                    AverageFrame(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth(),
                        completion = it.completionPercentage
                    )
                }

                HomeCard(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    navigateToChapters = navigateToChapters
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    SubscriptionCard(onCardClick = { showSubscriptionBottomSheet = true })
                    StatsCard(onCardClick = { showStatsBottomSheet = true })
                }

            }
        }

        if (showSubscriptionBottomSheet) {
            SubscriptionBottomSheet(
                onDismiss = { showSubscriptionBottomSheet = false },
                sheetState = sheetState
            )
        }

        if (showStatsBottomSheet) {
            StatsBottomSheet(
                appStats = appStats,
                onDismiss = { showStatsBottomSheet = false },
                sheetState = sheetState
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
    }
}

