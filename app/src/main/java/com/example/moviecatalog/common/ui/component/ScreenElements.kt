package com.example.moviecatalog.common.ui.component

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviecatalog.R
import com.example.moviecatalog.common.ui.theme.label11RTextStyle
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
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
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
        modifier = modifier.clickable { onClick() },
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
            text = nickname,
            style = title24BTextStyle,
            color = colorResource(R.color.white)
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