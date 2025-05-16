package at.uastw.androidfirstlesson.magiccard.data

import at.uastw.androidfirstlesson.magiccard.data.dto.MagicCardDto
import at.uastw.androidfirstlesson.magiccard.data.remote.MagicCardRemoteService
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class MagicCardRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.magicthegathering.io/v1/")
        .addConverterFactory(json
            .asConverterFactory("application/json".toMediaType())
        )
        .build()

    private val magicCardRemoteService = retrofit.create(MagicCardRemoteService::class.java)

    suspend fun loadCardPage(page: Int): List<MagicCardDto> {
        return magicCardRemoteService.getCardsPage(page).cards
    }
}