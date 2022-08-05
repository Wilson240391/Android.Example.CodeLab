package eu.tutorials.composematerialdesignsamples.views.screens.Movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.composematerialdesignsamples._Movies.data.dao.MovieRepository
import eu.tutorials.composematerialdesignsamples._Movies.domain.Movie
import eu.tutorials.composematerialdesignsamples._Movies.domain.MovieGenre
import eu.tutorials.composematerialdesignsamples.Util.asResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import eu.tutorials.composematerialdesignsamples.Util.Result

sealed interface GenreUiState {
    data class Success(val movies: List<Movie>) : GenreUiState
    object Error : GenreUiState
    object Loading : GenreUiState
}

data class GenreScreenUiState(
    val genreState: GenreUiState
)

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GenreScreenUiState(GenreUiState.Loading))
    val uiState = _uiState.asStateFlow()

    fun fetchMovies(genre: MovieGenre) {
        viewModelScope.launch {
            movieRepository.getMoviesStream(genre).asResult()
                .collect { result ->
                    val genreUiState = when (result) {
                        is Result.Success -> GenreUiState.Success(result.data)
                        is Result.Loading -> GenreUiState.Loading
                        is Result.Error -> GenreUiState.Error
                    }

                    _uiState.value = GenreScreenUiState(genreUiState)
                }
        }
    }
}