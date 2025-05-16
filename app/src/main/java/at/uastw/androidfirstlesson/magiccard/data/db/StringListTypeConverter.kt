package at.uastw.androidfirstlesson.magiccard.data.db

import androidx.room.TypeConverter

class StringListTypeConverter {

    companion object {
        const val SEPERATOR = ";;;;;"
    }

    @TypeConverter
    fun stringListToString(input: List<String>): String {
        return input.joinToString(SEPERATOR)
    }

    @TypeConverter
    fun fromStringToListString(input: String): List<String> {
        return input.split(SEPERATOR)
    }
}