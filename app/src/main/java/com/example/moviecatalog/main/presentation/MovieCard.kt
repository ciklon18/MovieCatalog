package com.example.moviecatalog.main.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviecatalog.R
import com.example.moviecatalog.common.main.domain.model.GenreModel
import com.example.moviecatalog.common.main.domain.model.UpdatedMovieElementModel
import com.example.moviecatalog.common.review.domain.model.ReviewShortModel
import com.example.moviecatalog.common.ui.component.AverageRatingIcon
import com.example.moviecatalog.common.ui.component.GenreItem
import com.example.moviecatalog.common.ui.component.MovieNameTextFromMain
import com.example.moviecatalog.common.ui.component.ReviewElement
import com.example.moviecatalog.common.ui.theme.label12TextStyle


@Composable
fun MovieCard(
    movie: UpdatedMovieElementModel, modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.Top,
        modifier = modifier

            .fillMaxWidth()
    ) {
        ImageSection(poster = movie.poster, reviews = movie.reviews)


        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MovieNameTextFromMain(
                        name = movie.name,
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .wrapContentWidth(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    if (movie.userReview?.rating != null) {
                        ReviewElement(rating = movie.userReview.rating)
                    }

                }
                DateAndCountrySection(year = movie.year, country = movie.country)

            }
            GenresSection(genres = movie.genres)
        }
    }
}

@Composable
fun ImageSection(
    poster: String?, reviews: List<ReviewShortModel>?
) {
    Box(
        modifier = Modifier
            .width(95.dp)
            .height(130.dp), contentAlignment = Alignment.TopStart
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(poster).build(),
            contentDescription = stringResource(R.string.image_description),
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(3.dp)),
            contentScale = ContentScale.FillBounds
        )
        if (reviews != null) {
            AverageRatingIcon(ratings = reviews, modifier = Modifier.padding(2.dp))
        }
    }
}

@Composable
fun DateAndCountrySection(
    year: Int?, country: String?
) {
    if (year != null && country != null) {
        Text(
            text = "$year · $country", color = colorResource(
                R.color.white
            ), style = label12TextStyle
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenresSection(
    genres: List<GenreModel>?

) {
    if (genres != null) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxSize(),
            maxItemsInEachRow = 3
        ) {
            genres.forEach { genreModel ->
                GenreItem(genre = genreModel)
            }
        }
    }
}
