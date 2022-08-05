package eu.tutorials.composematerialdesignsamples.appmovies.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.tutorials.composematerialdesignsamples.appmovies.data.dao.MovieDao

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
