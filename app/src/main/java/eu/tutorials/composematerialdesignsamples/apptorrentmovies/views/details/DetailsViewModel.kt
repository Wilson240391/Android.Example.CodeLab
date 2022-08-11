package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.Movie
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesItem
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.MainRepository
import eu.tutorials.composematerialdesignsamples.util.torrents.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: MainRepository): ViewModel() {
    private val movieDetails = MutableLiveData<Resource<Movie>>()
    private val favMovieExist =  MutableLiveData<Boolean>()

    fun getMovies() = moviesData as LiveData<Resource<List<MoviesItem>>>
    private var moviesData = MutableLiveData<Resource<List<MoviesItem>>>()
    private lateinit var lastDataToLoad: Pair<Boolean, String>
    private var pageNumber = 1
    private var cancelLoading = false

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

    init {
        moviesCategoryList()
    }

    fun loadMoreData() {
        if (!cancelLoading)
            if (lastDataToLoad.first)
                moviesCategoryList(lastDataToLoad.second, ++pageNumber)
    }

    fun moviesCategoryList(category: String = "", page: Int = 1) {
        lastDataToLoad = Pair(true, category)
        cancelLoading = true
        moviesData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = kotlin.runCatching { repository.getCacheCategory(category, page) }
            result.onSuccess {
                checkPageNum(page, result.getOrThrow())
            }
            result.onFailure {
                moviesData.postValue(Resource.Error("Error with loading data", null))
            }
        }
    }

    private fun checkPageNum(page: Int, result: List<MoviesItem>) {
        cancelLoading = false
        if (page == 1)
            moviesData.postValue(Resource.Loaded(result))
        else
            moviesData.postValue(Resource.NewData(result))
    }

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