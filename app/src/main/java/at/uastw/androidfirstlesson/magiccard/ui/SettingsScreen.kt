package at.uastw.androidfirstlesson.magiccard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )) {

    val state by settingsViewModel.settingsUiState.collectAsStateWithLifecycle()
    var pageText by rememberSaveable { mutableStateOf(""+state.page) }
    var showImages by rememberSaveable { mutableStateOf(state.showImages) }

    LaunchedEffect(state) {
        pageText = ""+state.page
        showImages = state.showImages
    }

    Column(modifier = modifier) {
        OutlinedTextField(value = pageText, onValueChange = {pageText = it},
            label = {
                Text("Magic Card Page to load")
            })
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Show images")
            Spacer(Modifier.width(20.dp))
            Switch(showImages, onCheckedChange = {showImages = it})
        }
        Button(onClick = {
            settingsViewModel.saveChanges(pageText.toInt(), showImages)
        }) { Text("Save changes") }
    }
}