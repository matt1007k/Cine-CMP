package dev.maxmeza.cineapp.ui.screens.graphic

import androidx.lifecycle.ViewModel
import dev.maxmeza.cineapp.data.local.sleepData
import dev.maxmeza.cineapp.ui.screens.sleep.SleepGraphData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetLaggedHomeScreenViewModel : ViewModel() {

    val uiState: StateFlow<JetLaggedHomeScreenState> = MutableStateFlow(JetLaggedHomeScreenState())
}

data class JetLaggedHomeScreenState(
    val sleepGraphData: SleepGraphData = sleepData,
)

data class WellnessData(
    val snoring: Int,
    val coughing: Int,
    val respiration: Int
)
