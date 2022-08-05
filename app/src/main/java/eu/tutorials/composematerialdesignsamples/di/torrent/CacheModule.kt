package eu.tutorials.composematerialdesignsamples.di.torrent

import androidx.room.Room
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.cache.MoviesDatabase
import eu.tutorials.composematerialdesignsamples.util.Constants
import org.koin.dsl.module

val cacheModule = module {

    single {
        Room.databaseBuilder(get(), MoviesDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }


    single { get<MoviesDatabase>().getMoviesDao() }

    single { get<MoviesDatabase>().getFavoriteDao() }
}