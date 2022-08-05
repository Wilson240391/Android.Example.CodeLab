package eu.tutorials.composematerialdesignsamples.views.screens.Movies

import android.app.Application
import androidx.compose.runtime.Immutable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.composematerialdesignsamples.Util.WhileUiSubscribed
import eu.tutorials.composematerialdesignsamples._Movies.data.dao.MovieRepository
import eu.tutorials.composematerialdesignsamples._Movies.domain.Movie
import eu.tutorials.composematerialdesignsamples._Movies.domain.MovieGenre
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import eu.tutorials.composematerialdesignsamples.Util.Result
import eu.tutorials.composematerialdesignsamples.Util.asResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

data class HomeUiState(
    val topRatedMovies: TopRatedMoviesUiState,
    val actionMovies: ActionMoviesUiState,
    val animationMovies: AnimationMoviesUiState,
    val isRefreshing: Boolean,
    val isError: Boolean
)

@Immutable
sealed interface TopRatedMoviesUiState {
    data class Success(val movies: List<Movie>) : TopRatedMoviesUiState
    object Error : TopRatedMoviesUiState
    object Loading : TopRatedMoviesUiState
}

@Immutable
sealed interface ActionMoviesUiState {
    data class Success(val movies: List<Movie>) : ActionMoviesUiState
    object Error : ActionMoviesUiState
    object Loading : ActionMoviesUiState
}

@Immutable
sealed interface AnimationMoviesUiState {
    data class Success(val movies: List<Movie>) : AnimationMoviesUiState
    object Error : AnimationMoviesUiState
    object Loading : AnimationMoviesUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val movieRepository: MovieRepository
) : AndroidViewModel(application) {

    val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        viewModelScope.launch {
            isError.emit(true)
        }
    }

    private val topRatedMovies: Flow<Result<List<Movie>>> =
        movieRepository.getTopRatedMoviesStream().asResult()

    private val actionMovies: Flow<Result<List<Movie>>> =
        movieRepository.getMoviesStream(MovieGenre.ACTION).asResult()

    private val animationMovies: Flow<Result<List<Movie>>> =
        movieRepository.getMoviesStream(MovieGenre.ANIMATION).asResult()

    private val isRefreshing = MutableStateFlow(false)

    private val isError = MutableStateFlow(false)

    val uiState: StateFlow<HomeUiState> = combine(
        topRatedMovies,
        actionMovies,
        animationMovies,
        isRefreshing,
        isError
    ) { topRatedResult, actionMoviesResult, animationMoviesResult, refreshing, errorOccurred ->

        val topRated: TopRatedMoviesUiState = when (topRatedResult) {
            is Result.Success -> TopRatedMoviesUiState.Success(topRatedResult.data)
            is Result.Loading -> TopRatedMoviesUiState.Loading
            is Result.Error -> TopRatedMoviesUiState.Error
        }

        val action: ActionMoviesUiState = when (actionMoviesResult) {
            is Result.Success -> ActionMoviesUiState.Success(actionMoviesResult.data)
            is Result.Loading -> ActionMoviesUiState.Loading
            is Result.Error -> ActionMoviesUiState.Error
        }

        val animation: AnimationMoviesUiState = when (animationMoviesResult) {
            is Result.Success -> AnimationMoviesUiState.Success(animationMoviesResult.data)
            is Result.Loading -> AnimationMoviesUiState.Loading
            is Result.Error -> AnimationMoviesUiState.Error
        }

        HomeUiState(
            topRated,
            action,
            animation,
            refreshing,
            errorOccurred
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = HomeUiState(
                TopRatedMoviesUiState.Loading,
                ActionMoviesUiState.Loading,
                AnimationMoviesUiState.Loading,
                isRefreshing = false,
                isError = false
            )
        )

    fun onRefresh() {
        viewModelScope.launch(exceptionHandler) {
            with(movieRepository) {
                val refreshTopRatedDeferred = async { refreshTopRated() }
                val refreshActionMoviesDeferred = async { refreshGenre(MovieGenre.ACTION) }
                val refreshAnimationMoviesDeferred = async { refreshGenre(MovieGenre.ANIMATION) }
                isRefreshing.emit(true)
                try {
                    awaitAll(
                        refreshTopRatedDeferred,
                        refreshActionMoviesDeferred,
                        refreshAnimationMoviesDeferred
                    )
                } finally {
                    isRefreshing.emit(false)
                }
            }
        }
    }

    fun onErrorConsumed() {
        viewModelScope.launch {
            isError.emit(false)
        }
    }
}