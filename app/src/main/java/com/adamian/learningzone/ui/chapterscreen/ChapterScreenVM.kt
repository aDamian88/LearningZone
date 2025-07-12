package com.adamian.learningzone.ui.chapterscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamian.learningzone.domain.model.AppStats
import com.adamian.learningzone.domain.usecase.GetAppStatsUC
import com.adamian.learningzone.domain.usecase.GetChapterStatusFirstPassUC
import com.adamian.learningzone.domain.usecase.GetChapterStatusFirstPassUC.ChapterStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChapterScreenVM @Inject constructor(
    private val getChapterStatusUC: GetChapterStatusFirstPassUC,
    private val getAppStatsUC: GetAppStatsUC
) : ViewModel() {
    private val _chapterLearningProgress = MutableStateFlow(
        value = listOf( ChapterStats(
            chapterId = 0,
            totalProgress = 0f,
            totalQuestions = 0,
            answeredQuestions = 0,
            totalQuizzes = 0,
            completedQuizzes = 0
        ))
    )
    val chapterLearningProgress: StateFlow<List<ChapterStats>> = _chapterLearningProgress.asStateFlow()
    private val _appStats = MutableStateFlow<AppStats?>(null)
    val appStats: StateFlow<AppStats?> = _appStats.asStateFlow()
    init {
        loadChapterLearningProgress()
    }

    fun loadChapterLearningProgress() {
        viewModelScope.launch {
            val progressMap = getChapterStatusUC()
            _chapterLearningProgress.value = progressMap
            val stats = getAppStatsUC()
            _appStats.value = stats
        }
    }

}