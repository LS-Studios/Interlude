package de.stubbe.interlude.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.stubbe.interlude.model.HistoryItem
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.Constants
import de.stubbe.interlude.util.shareSong
import de.stubbe.interlude.view.components.LoadingAsyncImage
import de.stubbe.interlude.viewmodel.HistoryScreenViewModel
import interlude.composeapp.generated.resources.Res
import interlude.composeapp.generated.resources.ic_image_error
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HistoryScreen() {
    val viewModel: HistoryScreenViewModel = koinViewModel()

    val history by viewModel.history.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(Constants.PaddingLarge),
        verticalArrangement = Arrangement.spacedBy(Constants.SpacerLarge)
    ) {
        items(history) { historyItem ->
            HistoryRow(
                historyItem = historyItem,
                onDelete = {
                    viewModel.deleteHistoryItem(historyItem)
                }
            )
        }
    }
}

@Composable
private fun HistoryRow(
    historyItem: HistoryItem,
    onClick: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable(
                interactionSource = null,
                indication = ripple()
            ) {
                onClick()
            }
            .padding(Constants.PaddingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Constants.SpacerLarge)
    ) {
        LoadingAsyncImage(
            imageUrl = historyItem.baseSong.artwork,
            contentDescription = historyItem.baseSong.provider.name,
            nonImageColor = Colors.Text,
            error = painterResource(Res.drawable.ic_image_error),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(Constants.Shape.Rounded.Small)
        )

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = historyItem.baseSong.displayName,
                fontSize = Constants.FontSizeMedium,
                color = Colors.Text,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                softWrap = false
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Constants.SpacerMedium)
            ) {
                LoadingAsyncImage(
                    imageUrl = historyItem.baseSong.provider.iconUrl,
                    contentDescription = historyItem.baseSong.provider.name,
                    nonImageColor = Colors.Text,
                    error = painterResource(Res.drawable.ic_image_error),
                    alwaysUseColorFilter = true,
                    modifier = Modifier
                        .size(20.dp)
                )

                Text(
                    text = historyItem.baseSong.provider.getFormattedName(),
                    fontSize = Constants.FontSizeMedium,
                    color = Colors.Text,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    fontWeight = FontWeight.Light
                )
            }
        }

        IconButton(
            onClick = onDelete
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete history item",
                tint = Colors.Text
            )
        }
    }
}