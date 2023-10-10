package com.adamian.learningzone.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamian.learningzone.model.QuestionItem
import com.adamian.learningzone.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {
    private val TAG = "QuestionViewModel"
    val allQuestionItems = MutableStateFlow<List<QuestionItem?>>(emptyList())

    fun getAllQuestions() {
        viewModelScope.launch {
            val allQuestions = questionRepository.getAllQuestions()
                allQuestionItems.value = allQuestions
            Log.d(TAG, "getAllQuestions 1: $allQuestions")
            Log.d(TAG, "getAllQuestions 2: ${allQuestionItems.value}")
        }
    }

    fun saveQuestion(questionItem: QuestionItem) {
        viewModelScope.launch(Dispatchers.IO){
            questionRepository.saveQuestion(questionItem)
        }
    }
}