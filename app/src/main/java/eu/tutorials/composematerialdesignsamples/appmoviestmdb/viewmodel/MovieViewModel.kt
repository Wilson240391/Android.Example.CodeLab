package eu.tutorials.composematerialdesignsamples.appmoviestmdb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.domain.MovieRepository
import eu.tutorials.composematerialdesignsamples.apprepositoryvideos.views.VideosViewModel
import eu.tutorials.composematerialdesignsamples.util.tmdb.Resource
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val repo: MovieRepository): ViewModel() {

    fun fetchMainScreenMovies() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(Triple(repo.getUpcomingMovies(),repo.getTopRatedMovies(),repo.getPopularMovies())))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class MovieViewModelFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }
}