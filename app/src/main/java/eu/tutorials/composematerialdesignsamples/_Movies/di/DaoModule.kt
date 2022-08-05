package eu.tutorials.composematerialdesignsamples._Movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.tutorials.composematerialdesignsamples._Movies.data.dao.MovieDao
import eu.tutorials.composematerialdesignsamples._Movies.domain.UiStatePlaygroundDatabase

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providesAuthorDao(
        database: UiStatePlaygroundDatabase,
    ): MovieDao = database.movieDao()
}