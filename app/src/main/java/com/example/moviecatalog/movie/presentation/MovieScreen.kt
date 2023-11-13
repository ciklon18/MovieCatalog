package com.example.moviecatalog.movie.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviecatalog.R
import com.example.moviecatalog.common.main.data.mapper.toShortMovieDetails
import com.example.moviecatalog.common.main.domain.model.GenreModel
import com.example.moviecatalog.common.main.domain.model.ShortMovieDetails
import com.example.moviecatalog.common.review.domain.model.ReviewModel
import com.example.moviecatalog.common.ui.component.AddReviewButton
import com.example.moviecatalog.common.ui.component.BasicDetailsText
import com.example.moviecatalog.common.ui.component.FavoriteButton
import com.example.moviecatalog.common.ui.component.MoreDetailsButton
import com.example.moviecatalog.common.ui.component.MovieAverageRatingIcon
import com.example.moviecatalog.common.ui.component.MovieDescriptionText
import com.example.moviecatalog.common.ui.component.MovieGenreItem
import com.example.moviecatalog.common.ui.component.MovieGradientElement
import com.example.moviecatalog.common.ui.component.MovieNameText
import com.example.moviecatalog.common.ui.component.MovieSubtitleText
import com.example.moviecatalog.common.ui.component.MovieTopAppBar
import com.example.moviecatalog.common.ui.component.StyledDetailsText
import com.example.moviecatalog.common.ui.component.ValueType
import com.example.moviecatalog.review.presentation.ReviewDialogScreen
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


@Composable
fun MovieScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = hiltViewModel(),

    ) {
    val uiState by viewModel.uiState.collectAsState()


    Scaffold(
        topBar = {
            MovieTopAppBar(
                navigateUp = { navController.navigateUp() },
                isFavorite = uiState.isFavorite,
                onFavoriteClicked = { viewModel.onFavoriteButtonPressed() },
                isVisibleActionButtons = uiState.isVisibleActionButtons
            )
        }, modifier = modifier
    ) { innerPadding ->
        MovieContent(
            innerPadding = innerPadding,
            uiState = uiState,
            onFavoriteButtonClicked = { viewModel.onFavoriteButtonPressed() },
            onAddReviewClicked = { viewModel.onAddReviewButtonPressed() },
            onEditButtonClicked = { viewModel.onEditReviewPressed() },
            onDeleteButtonClicked = { viewModel.onDeleteReviewPressed() }
        )
        ReviewDialogContent(
            isReviewDialogVisible = uiState.isReviewDialogVisible,
            onSaveClick = { viewModel.onSaveReviewButtonPressed(it) },
            onDismissClick = { viewModel.onDismissReviewButtonPressed() },
            userReview = uiState.userReview
        )

    }


}

@Composable
fun ReviewDialogContent(
    isReviewDialogVisible: Boolean,
    userReview: ReviewModel?,
    onSaveClick: (ReviewModel?) -> Unit,
    onDismissClick: () -> Unit
) {
    if (isReviewDialogVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(R.color.review_background)
                )
                .alpha(0.35f)
        )
        ReviewDialogScreen(
            userReview = userReview,
            onSaveClick = onSaveClick,
            onDismissClick = onDismissClick
        )
    }
}

@Composable
fun MovieContent(
    innerPadding: PaddingValues,
    uiState: MovieUIState,
    onFavoriteButtonClicked: () -> Unit,
    onAddReviewClicked: () -> Unit,
    onEditButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(innerPadding)
    ) {
        item {
            PosterSection(posterLink = uiState.poster)
        }
        item {
            MovieDetailsSection(
                uiState = uiState,
                onFavoriteButtonClicked = onFavoriteButtonClicked
            )
        }
        reviewsSection(
            reviews = uiState.reviews,
            userReview = uiState.userReview,
            onAddReviewClicked = onAddReviewClicked,
            onEditButtonClicked = onEditButtonClicked,
            onDeleteButtonClicked = onDeleteButtonClicked
        )
    }
}

@Composable
fun PosterSection(posterLink: String?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(posterLink ?: "").build(),
            contentDescription = stringResource(R.string.movie_poster),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        MovieGradientElement(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun MovieDetailsSection(uiState: MovieUIState, onFavoriteButtonClicked: () -> Unit) {
    Column(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        NameSection(
            reviews = uiState.reviews,
            movieName = uiState.name,
            isFavorite = uiState.isFavorite,
            onButtonClicked = onFavoriteButtonClicked
        )
        DescriptionSection(description = uiState.description)
        GenreSection(genres = uiState.genres)
        AboutMovieSection(movieDetails = uiState.toShortMovieDetails())
    }
}

@Composable
fun NameSection(
    reviews: List<ReviewModel>?,
    movieName: String?,
    isFavorite: Boolean,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MovieAverageRatingIcon(reviews ?: emptyList())
        MovieNameText(
            text = movieName ?: "", modifier = Modifier
                .weight(1f, fill = false)
                .wrapContentWidth(Alignment.Start)
        )
        FavoriteButton(isFavorite = isFavorite, onClick = onButtonClicked)
    }

}


@Composable
fun DescriptionSection(description: String?, modifier: Modifier = Modifier) {
    if (!description.isNullOrBlank()) {
        var areMoreDetailsShowed by remember { mutableStateOf(false) }
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            MovieDescriptionText(
                areMoreDetailsShowed = areMoreDetailsShowed, description = description
            )
            MoreDetailsButton(areMoreDetailsShowed = areMoreDetailsShowed,
                onClick = { areMoreDetailsShowed = !areMoreDetailsShowed })
        }
    }
}


@Composable
fun GenreSection(genres: List<GenreModel>?, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        MovieSubtitleText(text = stringResource(R.string.genres))
        GenresRow(genres = genres)
    }
}


@Composable
private fun AboutMovieSection(
    movieDetails: ShortMovieDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        MovieSubtitleText(text = stringResource(R.string.about_movie))
        MovieDetailsContent(movieDetails)
    }
}

@Composable
fun MovieDetailsContent(movieDetails: ShortMovieDetails) {
    Column(
        horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        movieDetails.year?.let { DetailTextsRow(it.toString(), ValueType.Year) }
        movieDetails.country?.let { DetailTextsRow(it, ValueType.Country) }
        movieDetails.tagline?.let { DetailTextsRow(it, ValueType.Tagline) }
        movieDetails.director?.let { DetailTextsRow(it, ValueType.Director) }
        movieDetails.budget?.let { DetailTextsRow(it.toString(), ValueType.Budget) }
        movieDetails.fees?.let { DetailTextsRow(it.toString(), ValueType.Fees) }
        movieDetails.ageLimit?.let { DetailTextsRow(it.toString(), ValueType.AgeLimit) }
        movieDetails.time?.let { DetailTextsRow(it.toString(), ValueType.Time) }

    }
}


@Composable
fun DetailTextsRow(
    value: String,
    type: ValueType
) {
    if (value.isBlank()) return
    val styledText = getStyledText(type = type)
    val detailsText = getDetailedText(value = value, type = type)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StyledDetailsText(value = styledText, modifier = Modifier.width(100.dp))
        BasicDetailsText(value = detailsText, modifier = Modifier.weight(1f))
    }
}


fun LazyListScope.reviewsSection(
    reviews: List<ReviewModel>?,
    userReview: ReviewModel?,
    onAddReviewClicked: () -> Unit,
    onEditButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
) {
    val isUserReviewAdded = userReview != null

    item {
        ReviewSectionHeader(
            isUserReviewAdded = isUserReviewAdded, onAddReviewClicked = onAddReviewClicked
        )
    }

    userReview?.let {
        item {
            ReviewCard(
                review = it,
                isUserReview = true,
                onEditButtonClicked = onEditButtonClicked,
                onDeleteButtonClicked = onDeleteButtonClicked
            )
        }
    }

    reviews?.forEach { review ->
        item {
            ReviewCard(review = review, isUserReview = false)
        }
    }
}


@Composable
fun ReviewSectionHeader(isUserReviewAdded: Boolean, onAddReviewClicked: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 15.dp, start = 16.dp, end = 16.dp)
    ) {
        MovieSubtitleText(text = stringResource(R.string.reviews))
        if (!isUserReviewAdded) {
            AddReviewButton(onClick = onAddReviewClicked)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenresRow(genres: List<GenreModel>?, modifier: Modifier = Modifier) {
    if (!genres.isNullOrEmpty()) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier.fillMaxSize(),
            maxItemsInEachRow = 3
        ) {
            genres.forEach { genreModel ->
                if (genreModel.name != null) {
                    MovieGenreItem(name = genreModel.name)
                }
            }
        }
    }
}


@Composable
fun getStyledText(type: ValueType): String {
    return when (type) {
        ValueType.Year -> stringResource(R.string.year)
        ValueType.Country -> stringResource(R.string.country)
        ValueType.Tagline -> stringResource(R.string.tagline)
        ValueType.Director -> stringResource(R.string.director)
        ValueType.Budget -> stringResource(R.string.budget)
        ValueType.Fees -> stringResource(R.string.fees)
        ValueType.AgeLimit -> stringResource(R.string.ageLimit)
        ValueType.Time -> stringResource(R.string.time)
    }
}

@Composable
fun getDetailedText(value: String, type: ValueType): String {
    return when (type) {
        ValueType.Tagline -> if (value != "-") "«$value»" else ""
        ValueType.Budget, ValueType.Fees -> {
            val symbols = DecimalFormatSymbols()
            symbols.groupingSeparator = ' '
            val numberFormat = DecimalFormat("#,###", symbols)
            "$${numberFormat.format(value.toLong())}"
        }

        ValueType.AgeLimit -> "$value+"
        ValueType.Time -> "$value мин."
        else -> value
    }
}

//@Preview
//@Composable
//fun PreviewMovieScreen() {
//    MovieScreen(
//        navController = rememberNavController(), viewModel = hiltViewModel(), movieId = ""
//    )
//}