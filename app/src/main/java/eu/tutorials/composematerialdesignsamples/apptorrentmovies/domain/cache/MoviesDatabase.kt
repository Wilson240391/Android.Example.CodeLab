package eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.*
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.FavoriteDao
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.MoviesDao
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.MoviesSuggestDao

@Database(entities = [MoviesItem::class, Movie::class, FavoriteMovie::class, MoviesSuggest::class], version = 2, exportSchema = false)
@TypeConverters(
    StringTypeConverter::class, CastTypeConverter::class,
    TorrentTypeConverter::class, TorrentsDetailsTypeConverter::class)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao
    abstract fun getFavoriteDao(): FavoriteDao
    abstract fun getMovieSuggestDao(): MoviesSuggestDao

}