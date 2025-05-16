package at.uastw.androidfirstlesson.magiccard.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.uastw.androidfirstlesson.magiccard.data.MagicCardRepository
import at.uastw.androidfirstlesson.magiccard.data.UserSettings
import at.uastw.androidfirstlesson.magiccard.data.UserSettingsRepository
import at.uastw.androidfirstlesson.magiccard.data.dto.MagicCardDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MagicCardUiState(
    val cards: List<MagicCardDto>,
    val selectedCard: MagicCardDto? = null,
    val isLoading: Boolean,
    val isError: Boolean,
    val userSettings: UserSettings
)

class MagicCardViewModel(
    private val magicCardRepository: MagicCardRepository
    = MagicCardRepository(),
    private val settingsRepository: UserSettingsRepository
) : ViewModel() {

    private val _magicCardUiState = MutableStateFlow(
        MagicCardUiState(
            emptyList(),
            null,
            false,
            false,
            UserSettings(1, true)
        )
    )
    val magicCardUiState = _magicCardUiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.userSettings.collectLatest { userSettings ->
                _magicCardUiState.update {
                    it.copy(userSettings = userSettings)
                }
            }
        }
    }

    fun onCardClicked(card: MagicCardDto) {
        _magicCardUiState.update {
            it.copy(selectedCard = card)
        }
    }

    fun reset() {
        _magicCardUiState.update {
            it.copy(selectedCard = null)
        }
    }

    fun onLoadButtonClicked(page: Int) {
        _magicCardUiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            try {
                // instead of parameter page we can also use
                // _magicCardUiState.value.userSettings.page
                val cards = magicCardRepository.loadCardPage(page)
                _magicCardUiState.update {
                    it.copy(cards = cards, isLoading = false, isError = false)
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error", e)
                _magicCardUiState.update {
                    it.copy(isError = true, isLoading = false)
                }
            }
        }
    }
}