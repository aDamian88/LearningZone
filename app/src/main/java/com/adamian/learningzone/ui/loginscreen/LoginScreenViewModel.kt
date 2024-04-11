package com.adamian.learningzone.ui.loginscreen

import androidx.lifecycle.SavedStateHandle
import com.adamian.learningzone.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class LoginScreenViewModel(
    repo: QuestionRepository,
    savedStateHandle: SavedStateHandle
) {
    private val _state: MutableStateFlow<State> = MutableStateFlow(
        State(
            info = null, isLoading = true, error = null
        )
    )

    data class State(
        val info: Info?,
        val isLoading: Boolean,
        val error: String?
    ) {
        data class Info(
            val user: String
        )
    }
}