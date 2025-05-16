package at.uastw.androidfirstlesson.magiccard.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


data class UserSettings(
    val page: Int,
    val showImages: Boolean
)

class UserSettingsRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val PAGE = intPreferencesKey("page")
        val SHOW_IMAGES = booleanPreferencesKey("show_images")
        val LOG_TAG = "UserSettingsRepository"
    }

    suspend fun saveSettings(userSettings: UserSettings) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PAGE] = userSettings.page
            mutablePreferences[SHOW_IMAGES] = userSettings.showImages
        }
    }

    val userSettings: Flow<UserSettings> = dataStore.data
        .catch { exception ->
            Log.e(LOG_TAG, "Something went wrong", exception)
            emit(emptyPreferences())
        }
        .map { preferences ->
            UserSettings(
                page = preferences[PAGE] ?: 1,
                showImages = preferences[SHOW_IMAGES] ?: true
            )
        }
}