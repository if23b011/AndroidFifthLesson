package at.uastw.androidfirstlesson.magiccard.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MagicCardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCards(cards: List<MagicCardEntity>) : List<Long>

    @Query("SELECT * FROM magic_card")
    fun loadCards(): Flow<List<MagicCardEntity>>
}