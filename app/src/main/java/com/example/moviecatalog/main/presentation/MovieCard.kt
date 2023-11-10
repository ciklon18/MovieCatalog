package com.example.moviecatalog.main.presentation

import android.content.Context
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviecatalog.R
import com.example.moviecatalog.common.main.domain.model.UpdatedMovieElementModel
import com.example.moviecatalog.common.ui.component.AverageRatingIcon
import com.example.moviecatalog.common.ui.component.GenreItem
import com.example.moviecatalog.common.ui.component.ReviewElement
import com.example.moviecatalog.common.ui.theme.label12TextStyle
import com.example.moviecatalog.common.ui.theme.label16BTextStyle


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieCard(
    context: Context,
    movie: UpdatedMovieElementModel,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .height(130.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.width(95.dp), contentAlignment = Alignment.TopStart
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context).data(movie.poster).build(),
                contentDescription = stringResource(R.string.image_description),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(3.dp)),
                contentScale = ContentScale.FillBounds
            )
            if (movie.reviews != null) {
                AverageRatingIcon(ratings = movie.reviews, modifier = Modifier.padding(2.dp))
            }

        }


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = movie.name ?: "",
                        color = colorResource(R.color.white),
                        style = label16BTextStyle,
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .wrapContentWidth(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    if (movie.userReview != null){
                        ReviewElement(rating = movie.userReview.rating)
                    }
                }
                Text(
                    text = "${movie.year} · ${movie.country}",
                    color = colorResource(
                        R.color.white
                    ),
                    style = label12TextStyle
                )
            }
            if (movie.genres != null) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxSize(),
                    maxItemsInEachRow = 3
                ) {
                    movie.genres.forEach { genreModel ->
                        GenreItem(genre = genreModel)
                    }
                }
            }

        }
    }
}



