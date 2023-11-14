package com.example.moviecatalog.common.review.data.mapper

import com.example.moviecatalog.common.review.domain.model.ReviewModel
import com.example.moviecatalog.common.review.domain.model.UserShortModel
import com.example.moviecatalog.common.profile.domain.model.Profile
import com.example.moviecatalog.common.review.domain.model.ReviewModifyModel
import com.example.moviecatalog.common.util.toDateString
import com.example.moviecatalog.review.presentation.ReviewUiState
import java.time.LocalDate

fun ReviewModel.toReviewUiState(): ReviewUiState {
    return ReviewUiState(
        id = this.id,
        rating = this.rating ?: 0,
        reviewText = this.reviewText ?: "",
        isAnonymous = this.isAnonymous ?: false,
        author = this.author
    )
}

fun ReviewUiState.toReviewModel(): ReviewModel {
    return ReviewModel(
        id = this.id ?: "",
        rating = this.rating,
        isAnonymous = this.isAnonymous,
        createDateTime = LocalDate.now().toDateString(),
        reviewText = this.reviewText,
        author = this.author ?: UserShortModel("", "", "")
    )
}

fun Profile.toUserShortModel(): UserShortModel{
    return UserShortModel(
        userId = this.id,
        nickName = this.nickName,
        avatar = this.avatarLink
    )
}

fun ReviewModel.toReviewModifyModel(): ReviewModifyModel {
    return ReviewModifyModel(
        reviewText = this.reviewText ?: "",
        rating = this.rating ?: 0,
        isAnonymous = this.isAnonymous ?: false
    )
}