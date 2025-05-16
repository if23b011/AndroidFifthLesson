package at.uastw.androidfirstlesson.magiccard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.uastw.androidfirstlesson.magiccard.data.UserSettings
import at.uastw.androidfirstlesson.magiccard.data.UserSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Constructor with a parameter that does not have any default value
class SettingsViewModel(
    private val settingsRepository: UserSettingsRepository
): ViewModel() {

    fun saveChanges(page: Int, showImages: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveSettings(UserSettings(page, showImages))
        }
    }

    // Convert "cold" Flow into "hot" StateFlow
    // a hot flow always has a value ready, that's why we need to provide an
    // initial value
    // the started parameter defined how long this "hot" Flow should live
    // while subscribed means until the viewModelScope is closed
    // and an additional 5 seconds (stopTimeoutMillis 5000)
    val settingsUiState = settingsRepository.userSettings.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserSettings(1, true)
    )

}