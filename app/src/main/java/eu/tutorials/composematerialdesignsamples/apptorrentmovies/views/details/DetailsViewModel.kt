package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.Movie
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesItem
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesSuggest
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.MainRepository
import eu.tutorials.composematerialdesignsamples.util.torrents.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: MainRepository): ViewModel() {
    private val movieDetails = MutableLiveData<Resource<Movie>>()
    private var moviesData = MutableLiveData<Resource<List<MoviesSuggest>>>()
    private val favMovieExist =  MutableLiveData<Boolean>()
    private var cancelLoading = false

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

    fun observeMoviesSuggestions() = moviesData as LiveData<Resource<List<MoviesSuggest>>>
    fun observeMoviesSuggestions(movieid: Int) {
        cancelLoading = true
        moviesData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = kotlin.runCatching { repository.getSuggestionsCache(movieid) }
            result.onSuccess {
                moviesData.postValue(Resource.Loaded(result.getOrThrow()))
            }
            result.onFailure {
                moviesData.postValue(Resource.Error("Error with loading data", null))
            }
        }
    }

    fun observeFavMovieExist() = favMovieExist as LiveData<Boolean>
    fun checkMovieFav(id: Int){
        viewModelScope.launch {
            favMovieExist.postValue(repository.checkFavMovieExist(id))
        }
    }

    fun addMovieToFavorite(movie: Movie){
        viewModelScope.launch {
            repository.saveMovieToFav(movie)
            favMovieExist.postValue(true)
        }
    }

    fun deleteMovieFromFavorite(id: Int){
        viewModelScope.launch {
            repository.deleteSpecificFavMovie(id)
            favMovieExist.postValue(false)
        }
    }
}