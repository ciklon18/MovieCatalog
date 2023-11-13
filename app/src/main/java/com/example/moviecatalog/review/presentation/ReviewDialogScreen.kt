package com.example.moviecatalog.review.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviecatalog.R
import com.example.moviecatalog.common.review.data.mapper.toReviewModel
import com.example.moviecatalog.common.review.domain.model.ReviewModel
import com.example.moviecatalog.common.ui.component.AnonymousReviewCheckBox
import com.example.moviecatalog.common.ui.component.LeaveReviewText
import com.example.moviecatalog.common.ui.component.ReviewButtons
import com.example.moviecatalog.common.ui.component.ReviewTextFiled


@Composable
fun ReviewDialogScreen(
    userReview: ReviewModel?,
    onSaveClick: (ReviewModel) -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReviewDialogViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userReview) {
        if (userReview != null) {
            viewModel.onUserReviewChanged(userReview = userReview)
        }
    }

    Dialog(onDismissRequest = { onDismissClick() }) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(colorResource(R.color.gray_900), RoundedCornerShape(5.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                LeaveReviewText()
                Spacer(modifier = Modifier.height(15.dp))

                RatingReviewBar(
                    rating = uiState.rating,
                    onClick = { viewModel.onRatingChanged(it) }
                )

                Spacer(modifier = Modifier.height(8.dp))
                ReviewTextFiled(value = uiState.reviewText,
                    onValueChange = { viewModel.onReviewTextChanged(it) })

                Spacer(modifier = Modifier.height(14.dp))
                AnonymousReviewCheckBox(
                    isAnonymous = uiState.isAnonymous,
                    onCheckedChange = { viewModel.onAnonymousChanged(it) }
                )
                Spacer(modifier = Modifier.height(25.dp))

                ReviewButtons(isAccentButtonEnabled = uiState.isSaveButtonEnabled,
                    onSaveClick = { viewModel.onButtonPressed(); onSaveClick(uiState.toReviewModel()) },
                    onDismissClick = { viewModel.onButtonPressed(); onDismissClick() })
            }
        }
    }
}


@Composable
fun RatingReviewBar(rating: Int, onClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        (1..10).map { index ->
            Icon(
                painter = painterResource(if (index <= rating) R.drawable.flled_star else R.drawable.unfilled_star),
                contentDescription = stringResource(
                    if (index <= rating) R.string.filled_star else R.string.unfilled_star
                ),
                modifier = Modifier
                    .height(25.dp)
                    .width(25.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onClick(index) },
                tint = colorResource(if (index <= rating) R.color.yellow else (R.color.gray_400))
            )
        }
    }
}
