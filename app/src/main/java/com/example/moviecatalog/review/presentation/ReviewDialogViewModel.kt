package com.example.moviecatalog.review.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.review.data.mapper.toReviewUiState
import com.example.moviecatalog.common.review.domain.model.ReviewModel
import com.example.moviecatalog.common.review.domain.model.UserShortModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewDialogViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()

    private val scope = viewModelScope


    fun onUserReviewChanged(userReview: ReviewModel) {
        scope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                userReview.toReviewUiState().copy(
                    isSaveButtonEnabled = currentState.isSaveButtonEnabled,
                    userReview = userReview
                )
            }
        }
    }

    fun onRatingChanged(newRating: Int) {
        scope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(rating = newRating)
            }
        }
        updateSaveButtonState()
    }

    fun onReviewTextChanged(newReviewText: String) {
        scope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(reviewText = newReviewText)
            }
        }
        updateSaveButtonState()
    }

    fun onAnonymousChanged(isAnonymous: Boolean) {
        scope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(isAnonymous = isAnonymous)
            }
        }
        updateSaveButtonState()
    }
    fun onButtonPressed(){
        scope.launch(Dispatchers.Default) {
            _uiState.update {
                ReviewUiState()
            }
        }
    }

    private fun updateSaveButtonState() {
        scope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                val userReview = currentState.userReview
                if (userReview != null) {
                    val isSomeFieldUpdated = isSomeFieldUpdated(currentState, userReview)
                    currentState.copy(isSaveButtonEnabled = isSomeFieldUpdated)
                } else {
                    currentState.copy(isSaveButtonEnabled = true)
                }
            }
        }
    }
    private fun isSomeFieldUpdated(currentState: ReviewUiState, userReview: ReviewModel): Boolean {
        return !(currentState.isAnonymous == userReview.isAnonymous &&
                currentState.rating == userReview.rating && currentState.reviewText == userReview.reviewText)
    }


}


data class ReviewUiState(
    val id: String? = null,
    val rating: Int = 0,
    val reviewText: String = "",
    val isAnonymous: Boolean = false,
    val isSaveButtonEnabled: Boolean = false,
    val author: UserShortModel? = null,
    val userReview: ReviewModel? = null
)
