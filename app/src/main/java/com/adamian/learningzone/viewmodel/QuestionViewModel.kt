package com.adamian.learningzone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamian.learningzone.model.QuestionItem
import com.adamian.learningzone.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    fun getQuestions(): QuestionItem {
        return QuestionItem(
            question = "test question",
            answerDescription = "test answer description",

        )
    }

    fun saveQuestions(questionItem: QuestionItem) {

        viewModelScope.launch(Dispatchers.IO){
            questionRepository.saveQuestion(questionItem)
        }
    }
}