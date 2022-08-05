package eu.tutorials.composematerialdesignsamples.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.tutorials.composematerialdesignsamples.appmovies.data.dao.MovieRepository
import eu.tutorials.composematerialdesignsamples.appmovies.data.network.OfflineFirstMovieRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsMoviesRepository(moviesRepository: OfflineFirstMovieRepository): MovieRepository
}