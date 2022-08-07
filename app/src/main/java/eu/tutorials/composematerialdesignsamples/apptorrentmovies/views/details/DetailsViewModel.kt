package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.Movie
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.MainRepository
import eu.tutorials.composematerialdesignsamples.util.Resource
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: MainRepository): ViewModel() {
    private val movieDetails = MutableLiveData<Resource<Movie>>()
    private val favMovieExist =  MutableLiveData<Boolean>()
    fun observeFavMovieExist() = favMovieExist as LiveData<Boolean>
    fun observeMovieDetails() = movieDetails as LiveData<Resource<Movie>>
    fun observeMovieDetails(id: Int) {
        movieDetails.postValue(Resource.Loading())
        viewModelScope.launch {
            val result = kotlin.runCatching { repository.getCacheDetails(id) }
            result.onSuccess {
                movieDetails.postValue(Resource.Loaded(it))
            }
            result.onFailure {
                movieDetails.postValue(Resource.Error("Error: $it", null))
            }
        }
    }

    fun checkMovieFav(id: Int){
        viewModelScope.launch {
            favMovieExist.postValue(repository.checkFavMovieExist(id))
        }
    }

    fun addMovieToFavorite(movie: Movie){
        viewModelScope.launch {
            repository.saveMovieToFav(movie)
        }
    }

    fun deleteMovieFromFavorite(id: Int){
        viewModelScope.launch {
            repository.deleteSpecificFavMovie(id)
        }
    }
}