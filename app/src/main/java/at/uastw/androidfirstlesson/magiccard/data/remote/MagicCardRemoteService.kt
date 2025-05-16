package at.uastw.androidfirstlesson.magiccard.data.remote

import at.uastw.androidfirstlesson.magiccard.data.dto.MagicCardListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MagicCardRemoteService {
    @GET("cards")
    suspend fun getCardsPage(@Query("page") page: Int): MagicCardListDto
}