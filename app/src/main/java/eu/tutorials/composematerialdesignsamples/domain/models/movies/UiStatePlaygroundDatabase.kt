package eu.tutorials.composematerialdesignsamples.domain.models.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.tutorials.composematerialdesignsamples.data.room.MovieDao

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class UiStatePlaygroundDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
