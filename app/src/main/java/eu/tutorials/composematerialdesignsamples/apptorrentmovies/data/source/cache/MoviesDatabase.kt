package eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.*

@Database(entities = [MoviesItem::class, Movie::class, FavoriteMovie::class], version = 1, exportSchema = false)
@TypeConverters(
    StringTypeConverter::class, CastTypeConverter::class,
    TorrentTypeConverter::class, TorrentsDetailsTypeConverter::class)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao
    abstract fun getFavoriteDao(): FavoriteDao

}