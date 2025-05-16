package at.uastw.androidfirstlesson.magiccard.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MagicCardEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    // abstract methods for all Dao classes
    abstract fun magicCardDao(): MagicCardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE?: synchronized(this) {
                val tempInstance = INSTANCE
                if (tempInstance != null) {
                    return tempInstance
                }
                val instance = createInstance(context)
                INSTANCE = instance
                return instance
            }
        }

        private fun createInstance(context: Context): AppDatabase {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).fallbackToDestructiveMigration(true).build()
            return instance 
        }
    }
}