package at.uastw.androidfirstlesson.magiccard.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@TypeConverters(StringListTypeConverter::class)
@Entity(tableName = "magic_card")
data class MagicCardEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val types: List<String>,
    val colors: List<String> = emptyList(),
    val imageUrl: String?,
    val text: String?
)