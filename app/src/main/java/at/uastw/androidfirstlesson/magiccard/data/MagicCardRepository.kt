package at.uastw.androidfirstlesson.magiccard.data

import at.uastw.androidfirstlesson.magiccard.data.db.MagicCardDao
import at.uastw.androidfirstlesson.magiccard.data.db.MagicCardEntity
import at.uastw.androidfirstlesson.magiccard.data.dto.MagicCardDto
import at.uastw.androidfirstlesson.magiccard.data.remote.MagicCardRemoteService
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class MagicCardRepository(
    private val magicCardDao: MagicCardDao,
    private val magicCardRemoteService: MagicCardRemoteService
) {

    suspend fun loadCardPage(page: Int) {
        val cardsFromRemote = magicCardRemoteService.getCardsPage(page).cards
        val cardEntities = cardsFromRemote.map {
            MagicCardEntity(
                id = 0,
                name = it.name,
                types = it.types,
                colors = it.colors,
                imageUrl = it.imageUrl,
                text = it.text
            )
        }
        magicCardDao.insertCards(cardEntities)
    }

    val magicCardsFromDb = magicCardDao.loadAllCards().map { list ->
        list.map { entity ->
            MagicCardDto(
                name = entity.name,
                types = entity.types,
                colors = entity.colors,
                imageUrl = entity.imageUrl,
                text = entity.text
            )
        }
    }
}