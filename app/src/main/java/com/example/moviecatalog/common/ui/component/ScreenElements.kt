package com.example.moviecatalog.common.ui.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.example.moviecatalog.R
import com.example.moviecatalog.common.main.data.mapper.toReviewShortModel
import com.example.moviecatalog.common.main.domain.model.GenreModel
import com.example.moviecatalog.common.review.domain.model.ReviewModel
import com.example.moviecatalog.common.review.domain.model.ReviewShortModel
import com.example.moviecatalog.common.ui.theme.label11RTextStyle
import com.example.moviecatalog.common.ui.theme.label13TextStyle
import com.example.moviecatalog.common.ui.theme.label15MTextStyle
import com.example.moviecatalog.common.ui.theme.label17SBTextStyle
import com.example.moviecatalog.common.ui.theme.text14RTextStyle
import com.example.moviecatalog.common.ui.theme.title20B2TextStyle
import com.example.moviecatalog.common.ui.theme.title24BTextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    navigateUp: () -> Unit, modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(R.string.top_app_text), style = label17SBTextStyle
        )
    }, navigationIcon = {
        IconButton(
            onClick = navigateUp,
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
            )
        }
    }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        titleContentColor = colorResource(R.color.accent),
        navigationIconContentColor = colorResource(R.color.white)
    ), modifier = modifier
    )
}

@Composable
fun MyBottomBar(
    onMainClicked: () -> Unit,
    onFavoriteClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    myTab: MyTab,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Divider(
            color = colorResource(R.color.divider_color),
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp, top = 13.dp, bottom = 13.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyButtonItem(
                text = stringResource(R.string.main),
                painter = painterResource(R.drawable.home),
                onClick = onMainClicked,
                isSelected = myTab == MyTab.Main
            )
            MyButtonItem(
                text = stringResource(R.string.favorite),
                painter = painterResource(R.drawable.unfilled_favorite),
                onClick = onFavoriteClicked,
                isSelected = myTab == MyTab.Favorite
            )
            MyButtonItem(
                text = stringResource(R.string.profile),
                painter = painterResource(R.drawable.user),
                onClick = onProfileClicked,
                isSelected = myTab == MyTab.Profile
            )
        }
    }
}

@Composable
fun MyButtonItem(
    text: String,
    painter: Painter,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val buttonColor =
        if (isSelected) colorResource(R.color.accent) else colorResource(R.color.gray_400)
    Column(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = text,
            tint = buttonColor,
            modifier = Modifier.height(28.dp)
        )
        Text(
            text = text, style = label11RTextStyle, color = buttonColor
        )
    }
}

@Composable
fun MyProfileCard(
    link: String, nickname: String, modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(link)
                .decoderFactory(GifDecoder.Factory())
                .error(R.drawable.user)
                .placeholder(R.drawable.user)
                .build(),
            contentDescription = stringResource(R.string.profile_icon),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(88.dp)
                .width(88.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = nickname, style = title24BTextStyle, color = colorResource(R.color.white)
        )
    }
}

@Composable
fun PageTitleText(
    text: String, modifier: Modifier = Modifier
) {
    Text(
        text = text, style = title20B2TextStyle, modifier = modifier
    )
}


@Composable
fun ErrorText(
    text: String, modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = text14RTextStyle,
        color = colorResource(R.color.light_accent),
        modifier = modifier
    )
}

@Preview
@Composable
fun PreviewMyBottomBar() {
    MyBottomBar(
        onMainClicked = { /*TODO*/ },
        onFavoriteClicked = { /*TODO*/ },
        onProfileClicked = { /*TODO*/ },
        myTab = MyTab.Favorite
    )
}


@Composable
fun ReviewElement(
    rating: Int, modifier: Modifier = Modifier
) {
    val elementColor = when (rating) {
        in 9..10 -> colorResource(R.color.green)
        in 8..9 -> colorResource(R.color.light_green)
        in 6..8 -> colorResource(R.color.yellow)
        in 4..6 -> colorResource(R.color.orange)
        in 3..4 -> colorResource(R.color.orange_fire)
        in 0..3 -> colorResource(R.color.red)
        else -> colorResource(R.color.red)
    }
    Box(
        modifier = modifier
            .background(
                color = elementColor, shape = RoundedCornerShape(35.dp)
            )
            .padding(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.flled_star),
                contentDescription = stringResource(R.string.star),
                tint = colorResource(R.color.white),
                modifier = Modifier
                    .height(16.dp)
                    .width(16.dp)
            )
            Text(text = "$rating", color = colorResource(R.color.white))
        }
    }
}

@Composable
fun MovieAverageRatingIcon(ratings: List<ReviewModel>, modifier: Modifier = Modifier) {
    val movieRatings = ratings.map { rating -> rating.toReviewShortModel() }
    AverageRatingIcon(ratings = movieRatings, modifier = modifier)
}

@Composable
fun AverageRatingIcon(ratings: List<ReviewShortModel>, modifier: Modifier = Modifier) {
    val rating =
        if (ratings.isNotEmpty()) (ratings.sumOf { it.rating }.toDouble() / ratings.size) else 0.0
    val roundedRating = String.format("%.1f", rating)
    val elementColor = when (roundedRating.toDouble()) {
        in 9.0..10.0 -> colorResource(R.color.green)
        in 8.0..9.0 -> colorResource(R.color.light_green)
        in 6.0..8.0 -> colorResource(R.color.yellow)
        in 4.0..6.0 -> colorResource(R.color.orange)
        in 3.0..4.0 -> colorResource(R.color.orange_fire)
        in 0.0..3.0 -> colorResource(R.color.red)
        else -> colorResource(R.color.red)
    }
    if (roundedRating.isNotBlank()) {
        Row(
            modifier = modifier
                .background(elementColor, shape = RoundedCornerShape(5.dp))
                .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 2.dp)
        ) {
            Text(text = roundedRating, color = colorResource(R.color.black))
        }
    }
}


@Composable
fun GenreItem(genre: GenreModel) {
    Box(
        Modifier
            .background(
                color = colorResource(R.color.gray_750), shape = RoundedCornerShape(5.dp)
            )
            .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre.name ?: "", color = colorResource(R.color.white), style = label13TextStyle
        )
    }
}

@Composable
fun MovieGenreItem(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(
                color = colorResource(R.color.accent), shape = RoundedCornerShape(5.dp)
            )
            .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name, color = colorResource(R.color.white), style = label15MTextStyle
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier

            .background(
                color = colorResource(R.color.slider_pagination).copy(0.1f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(10.dp)

    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(if (isSelected) R.drawable.dot_filled else R.drawable.dot_unfilled),
            contentDescription = stringResource(R.string.dot),
            tint = colorResource(R.color.white)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(
    navigateUp: () -> Unit,
    isFavorite: Boolean,
    onFavoriteClicked: () -> Unit,
    isVisibleMovie: Boolean,
    movieName: String?,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(title = {
        if (isVisibleMovie){
            Text(text = movieName ?: "", style = title24BTextStyle, color = colorResource(R.color.white))
        }

    }, navigationIcon = {
        IconButton(
            onClick = navigateUp,
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
            )
        }
    },
        actions = {
            if (isVisibleMovie) {
                FavoriteButton(isFavorite = isFavorite, onClick = onFavoriteClicked)
            }

        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            titleContentColor = colorResource(R.color.accent),
            navigationIconContentColor = colorResource(R.color.white)
        ), modifier = modifier.height(45.dp)
    )
}

@Composable
fun MovieGradientElement(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        Color.Transparent,
                    ),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(0f, 0f)
                )
            )
    )
}


@Composable
fun ReviewCheckBox(isAnonymous: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Checkbox(
        checked = isAnonymous,
        onCheckedChange = onCheckedChange,
        modifier = Modifier
            .height(16.dp)
            .width(16.dp),
        colors = CheckboxDefaults.colors(
            checkedColor = colorResource(R.color.accent),
            uncheckedColor = colorResource(R.color.white),
            checkmarkColor = colorResource(R.color.white),
            disabledCheckedColor = colorResource(R.color.white),
            disabledIndeterminateColor = colorResource(R.color.white),
            disabledUncheckedColor = colorResource(R.color.white)
        )
    )
}

@Composable
fun AnonymousReviewCheckBox(isAnonymous: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ReviewCheckBox(
            isAnonymous = isAnonymous,
            onCheckedChange = onCheckedChange
        )
        AnonymousReviewText()
    }
}
