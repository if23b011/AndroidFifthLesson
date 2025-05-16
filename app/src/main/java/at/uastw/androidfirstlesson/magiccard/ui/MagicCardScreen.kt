package at.uastw.androidfirstlesson.magiccard.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import at.uastw.androidfirstlesson.R
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlin.random.Random

@Composable
fun MagicCardScreen(
    modifier: Modifier = Modifier,
    magicCardViewModel: MagicCardViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {

    val state by magicCardViewModel
        .magicCardUiState.collectAsStateWithLifecycle()
    BackHandler(state.selectedCard != null) {
        magicCardViewModel.reset()
    }
    Column(modifier = modifier) {
        Button(
            onClick = { magicCardViewModel.onLoadButtonClicked(state.userSettings.page) },
            enabled = !state.isLoading
        ) {
            Text("Load cards page: ${state.userSettings.page}")
        }
        if (state.isError) {
            Text("Something went wrong", color = Color.Red)
        } else {
            if (state.selectedCard != null) {
                CardDetail(card = state.selectedCard!!)
            } else {
                LazyColumn {
                    itemsIndexed(state.cards) { index, card ->
                        if (index == state.cards.size - 1) {
                            MagicCardBigItem(
                                card = card,
                                onCardClick = {
                                    magicCardViewModel.onCardClicked(card)
                                }
                            )
                        } else {
                            MagicCardListItem(
                                card = card,
                                onCardClick = {
                                    magicCardViewModel.onCardClicked(card)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MagicCardBigItem(
    modifier: Modifier = Modifier,
    card: MagicCardDto,
    onCardClick: (MagicCardDto) -> Unit
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { onCardClick(card) }) {
        Column(Modifier.padding(16.dp)) {
            Text(card.name, style = MaterialTheme.typography.titleLarge)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    // It is okay for the homework if you change the api for the pictures
                    // however it should be different pictures if possible
                    .data(
                        "https://www.mtgpics.com/pics/big/mat/${
                            Random.Default.nextInt(
                                100,
                                223
                            )
                        }.jpg"
                    )
                    .crossfade(500)
                    .size(1000)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = card.name
            )

        }
    }
}

@Composable
fun MagicCardListItem(
    modifier: Modifier = Modifier,
    card: MagicCardDto,
    onCardClick: (MagicCardDto) -> Unit
) {
    ElevatedCard(
        modifier = modifier,
        onClick = { onCardClick(card) }) {
        Column(Modifier.padding(16.dp)) {
            Text(card.name)
            if (card.imageUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        // It is okay for the homework if you change the api for the pictures
                        // however it should be different pictures if possible
                        .data(
                            "https://www.mtgpics.com/pics/big/mat/${
                                Random.Default.nextInt(
                                    100,
                                    223
                                )
                            }.jpg"
                        )
                        .build(), contentDescription = card.name
                )

            }
        }
    }
}

@Composable
fun CardDetail(modifier: Modifier = Modifier, card: MagicCardDto) {
    Column(modifier = modifier) {
        Text(card.name)
        card.text?.let { Text(it) }
        card.imageUrl?.let { Text(it) }
        for (color in card.colors) {
            Text(color)
        }
    }
}