package eu.tutorials.composematerialdesignsamples._Movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.tutorials.composematerialdesignsamples._Movies.data.dao.MovieRepository
import eu.tutorials.composematerialdesignsamples._Movies.data.api.OfflineFirstMovieRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsMoviesRepository(moviesRepository: OfflineFirstMovieRepository): MovieRepository
}