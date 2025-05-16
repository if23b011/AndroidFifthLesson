package at.uastw.androidfirstlesson

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import at.uastw.androidfirstlesson.magiccard.data.MagicCardRepository
import at.uastw.androidfirstlesson.magiccard.data.UserSettingsRepository
import at.uastw.androidfirstlesson.magiccard.data.db.AppDatabase
import at.uastw.androidfirstlesson.magiccard.data.remote.MagicCardRemoteService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


private val Context.dataStore by preferencesDataStore("user_settings")

// The application class exists only once per App
// we can have multiple activities, but only one Application
class MainApplication : Application() {
    lateinit var settingsRepository: UserSettingsRepository
    lateinit var magicCardRepository: MagicCardRepository

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.magicthegathering.io/v1/")
        .addConverterFactory(
            json
                .asConverterFactory("application/json".toMediaType())
        )
        .build()

    override fun onCreate() {
        super.onCreate()
        settingsRepository = UserSettingsRepository(dataStore = dataStore)
        val database = AppDatabase.getDatabase(context = this)
        val magicCardDao = database.magicCardDao()
        val magicCardRemoteService = retrofit.create(MagicCardRemoteService::class.java)
        magicCardRepository = MagicCardRepository(magicCardDao, magicCardRemoteService)
    }

}