package at.uastw.androidfirstlesson.magiccard.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import at.uastw.androidfirstlesson.MainApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val application = this[APPLICATION_KEY] as MainApplication
            SettingsViewModel(application.settingsRepository)
        }

        initializer {
            val application = this[APPLICATION_KEY] as MainApplication
            MagicCardViewModel(settingsRepository = application.settingsRepository)
        }
    }
}