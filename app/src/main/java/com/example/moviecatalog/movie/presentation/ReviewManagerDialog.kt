package com.example.moviecatalog.movie.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.common.ui.theme.label14MTextStyle


@Composable
fun ReviewManagerDialog(
    isExpended: Boolean,
    onEditButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val widthInDp = configuration.screenWidthDp.dp
    val menuOffset = DpOffset(widthInDp - 209.dp, 0.dp)

    DropdownMenu(
        expanded = isExpended,
        onDismissRequest = onDismissRequest,
        offset = menuOffset,
        modifier = Modifier
            .background(color = colorResource(R.color.gray_750))
            .width(177.dp)
            .height(88.dp),
    ) {
        ReviewMenuItem(
            text = stringResource(R.string.edit),
            painter = painterResource(R.drawable.edit),
            onClick = onEditButtonClick,
            itemColor = colorResource(R.color.white),
            modifier = Modifier.weight(1f)
        )
        Divider()
        ReviewMenuItem(
            text = stringResource(R.string.delete),
            painter = painterResource(R.drawable.trash),
            onClick = onDeleteButtonClick,
            itemColor = colorResource(R.color.accent),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ReviewMenuItem(
    text: String,
    painter: Painter,
    onClick: () -> Unit,
    itemColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, style = label14MTextStyle, color = itemColor)
            Icon(
                painter = painter,
                contentDescription = text,
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
                tint = itemColor
            )
        }
    }
}


