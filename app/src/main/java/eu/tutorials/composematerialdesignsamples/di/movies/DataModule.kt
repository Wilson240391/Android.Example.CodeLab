package eu.tutorials.composematerialdesignsamples.di.movies

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.tutorials.composematerialdesignsamples.data.repository.movies.MovieRepository
import eu.tutorials.composematerialdesignsamples.data.repository.movies.OfflineFirstMovieRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsMoviesRepository(moviesRepository: OfflineFirstMovieRepository): MovieRepository
}