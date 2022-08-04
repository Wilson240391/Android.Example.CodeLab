package eu.tutorials.composematerialdesignsamples.di.movies

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.tutorials.composematerialdesignsamples.data.room.MovieDao
import eu.tutorials.composematerialdesignsamples.domain.models.movies.UiStatePlaygroundDatabase

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providesAuthorDao(
        database: UiStatePlaygroundDatabase,
    ): MovieDao = database.movieDao()
}