package com.example.moviecatalog.movie.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviecatalog.R
import com.example.moviecatalog.common.review.domain.model.ReviewModel
import com.example.moviecatalog.common.ui.component.ReviewElement
import com.example.moviecatalog.common.ui.component.ReviewManagementButton
import com.example.moviecatalog.common.ui.theme.label12TextStyle
import com.example.moviecatalog.common.ui.theme.label13TextStyle
import com.example.moviecatalog.common.ui.theme.label14MTextStyle
import com.example.moviecatalog.common.ui.theme.text14RTextStyle
import java.text.ParseException
import java.text.SimpleDateFormat


@Composable
fun ReviewCard(
    review: ReviewModel,
    modifier: Modifier = Modifier,
    isUserReview: Boolean,
    onEditButtonClicked: () -> Unit = {},
    onDeleteButtonClicked: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isUserReview) {
            ReviewAuthorSection(
                review = review,
                isUserReview = true,
                onEditButtonClicked = onEditButtonClicked,
                onDeleteButtonClicked = onDeleteButtonClicked
            )
        } else {
            ReviewAuthorSection(
                review = review,
                isUserReview = false
            )
        }


        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            ReviewTextSection(review.reviewText)
            ReviewDateSection(review.createDateTime)
        }
    }
}


@Composable
fun ReviewAuthorSection(
    review: ReviewModel,
    isUserReview: Boolean,
    onEditButtonClicked: () -> Unit = {},
    onDeleteButtonClicked: () -> Unit = {}
) {
    var isReviewManagerShowed by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ReviewAuthorImage(
                    isAnonymous = review.isAnonymous ?: false,
                    avatarLink = review.author?.avatar
                )
                AuthorName(
                    isAnonymous = if (isUserReview || review.isAnonymous == null) false else review.isAnonymous,
                    isUserReview = isUserReview,
                    nickname = review.author?.nickName
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                review.rating?.let { ReviewElement(rating = it) }
                if (isUserReview) {
                    ReviewManagementButton({ isReviewManagerShowed = true; })
                }

            }
        }
        if (isReviewManagerShowed) {

            ReviewManagerDialog(
                isExpended = true,
                onEditButtonClick = { onEditButtonClicked(); isReviewManagerShowed = false },
                onDeleteButtonClick = { onDeleteButtonClicked(); isReviewManagerShowed = false },
                onDismissRequest = { isReviewManagerShowed = false }
            )

        }
    }

}


@Composable
fun ReviewAuthorImage(isAnonymous: Boolean, avatarLink: String?) {
    if (isAnonymous) {
        AnonymousImage()
    } else {
        if (avatarLink != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarLink)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .build(),
                contentDescription = stringResource(
                    R.string.user_avatar
                ),
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clip(RoundedCornerShape(50.dp)),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun AnonymousImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(40.dp)
            .width(40.dp)
            .background(
                colorResource(R.color.anonymous_background), shape = RoundedCornerShape(50.dp)
            ), contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.user),
            contentDescription = stringResource(R.string.anonymous),
            tint = colorResource(R.color.black)
        )
    }
}

@Composable
fun AuthorName(isAnonymous: Boolean, isUserReview: Boolean, nickname: String?) {
    Column {
        Text(
            text = if (nickname != null && !isAnonymous) nickname else stringResource(
                R.string.anonymous
            ),
            style = label14MTextStyle,
            color = colorResource(R.color.white),
            textAlign = TextAlign.Start
        )
        if (isUserReview) {
            Text(
                text = stringResource(R.string.my_review),
                style = label13TextStyle,
                color = colorResource(R.color.gray_400),
                textAlign = TextAlign.Start
            )
        }
    }

}

@Composable
fun ReviewTextSection(reviewText: String?) {
    if (reviewText != null) {
        Text(
            text = reviewText,
            color = colorResource(R.color.white),
            style = text14RTextStyle,
            textAlign = TextAlign.Start
        )
    }
}


@Composable
fun ReviewDateSection(createDateTime: String?) {
    if (createDateTime == null) return
    val formattedDate = formatDate(dateString = createDateTime)
    if (formattedDate != null) {
        Text(
            text = formattedDate,
            color = colorResource(R.color.gray_400),
            style = label12TextStyle,
            textAlign = TextAlign.Start
        )
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun formatDate(dateString: String): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val outputFormat = SimpleDateFormat("dd.MM.yyyy")
    return try {
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) }
    } catch (e: ParseException) {
        null
    }
}

