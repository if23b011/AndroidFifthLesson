package at.uastw.androidfirstlesson

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import at.uastw.androidfirstlesson.magiccard.data.UserSettingsRepository


private val Context.dataStore by preferencesDataStore("user_settings")

// The application class exists only once per App
// we can have multiple activities, but only one Application
class MainApplication : Application() {
    lateinit var settingsRepository: UserSettingsRepository

    override fun onCreate() {
        super.onCreate()
        settingsRepository = UserSettingsRepository(dataStore = dataStore)
    }

}