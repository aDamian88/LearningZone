package com.adamian.learningzone.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamian.learningzone.domain.model.AppStats
import com.adamian.learningzone.domain.usecase.GetAppStatsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val getAppStatsUC: GetAppStatsUC
) : ViewModel() {

    private val _appStats = MutableStateFlow<AppStats?>(null)
    val appStats: StateFlow<AppStats?> = _appStats.asStateFlow()

    init {
        loadAppStats()
    }

    private fun loadAppStats() {
        viewModelScope.launch {
            val stats = getAppStatsUC()
            _appStats.value = stats
        }
    }
}
