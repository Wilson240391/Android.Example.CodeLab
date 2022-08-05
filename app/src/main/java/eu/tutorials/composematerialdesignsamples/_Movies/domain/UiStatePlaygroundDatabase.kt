package eu.tutorials.composematerialdesignsamples._Movies.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.tutorials.composematerialdesignsamples._Movies.data.dao.MovieDao

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
