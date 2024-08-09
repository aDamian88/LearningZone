package com.adamian.learningzone.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.usecase.GetAllQuestionsUC
import com.adamian.learningzone.domain.usecase.SaveQuestionUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val saveQuestionUC: SaveQuestionUC,
    private val getAllQuestionsUC: GetAllQuestionsUC
) : ViewModel() {
    val allQuestionItems = MutableStateFlow<List<QuestionItem?>>(emptyList())
    val questionState: StateFlow<List<QuestionItem?>> = allQuestionItems.asStateFlow()

    fun getAllQuestions() {
//        viewModelScope.launch {
//            getAllQuestionsUC.invoke().collectLatest { result ->
//                allQuestionItems.value = result
//            }
//        }
    }

    fun saveQuestion(questionItem: QuestionItem) {
        viewModelScope.launch {
            saveQuestionUC.invoke(questionItem).collect()
        }
    }
}