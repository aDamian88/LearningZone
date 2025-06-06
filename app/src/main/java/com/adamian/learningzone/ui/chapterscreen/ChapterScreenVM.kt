package com.adamian.learningzone.ui.chapterscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamian.learningzone.domain.usecase.GetChapterStatusFirstPassUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChapterScreenVM @Inject constructor(
    private val getChapterStatusUC: GetChapterStatusFirstPassUC
) : ViewModel() {
    private val _chapterLearningProgress = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val chapterLearningProgress: StateFlow<Map<Int, Int>> = _chapterLearningProgress.asStateFlow()

    init {
        loadChapterLearningProgress()
    }

    fun loadChapterLearningProgress() {
        viewModelScope.launch {
            val progressMap = getChapterStatusUC()
            _chapterLearningProgress.value = progressMap
        }
    }
}