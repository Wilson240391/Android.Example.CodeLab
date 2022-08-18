package eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain

import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.*
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain.cache.ICacheSource
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain.network.INetworkSource
import eu.tutorials.composematerialdesignsamples.util.changeCategory
import eu.tutorials.composematerialdesignsamples.util.convertToFavorite

class MainRepository(
    private val networkSource: INetworkSource,
    private val cacheSource: ICacheSource
) {
    private var firstNetworkLoad = true

    private suspend fun getNetworkCategory(category: String, page: Int) {
        val result = networkSource.getMoviesCategory(category, page)
        result.data?.movies?.apply {
            if (this.isNotEmpty()) {
                deleteLastCache()
                changeCategory(category)
                cacheSource.saveCacheMoviesList(this)
            }
        }
    }

    suspend fun getCacheCategory(category: String, page: Int): List<MoviesItem> {
        try {
            getNetworkCategory(category, page)
        } finally {
            val result = cacheSource.getCacheMoviesList(category, 20, page.times(10))
            if (result.isEmpty())
                throw IllegalArgumentException()
            return result
        }
    }

    suspend fun getNetworkSearch(search: String, page: Int): MoviesResponse {
        return networkSource.searchMovie(search, page)
    }

    //Suggestion
    suspend fun getSuggestionsCache(movieid: Int): List<MoviesSuggest> {
        var result = cacheSource.getSuggestionsMovie(movieid)
        if(result.isEmpty()) {
            result = getSuggestionsNetwork(movieid)
        }
        return result
    }

    private suspend fun getSuggestionsNetwork(movieid: Int): List<MoviesSuggest> {
        val result = networkSource.Suggestions(movieid)
        result.data?.movies!!.filter { it.movieId == null }.forEach { it.movieId = movieid}
        with(result.data?.movies!!) {
            cacheSource.saveSuggestMovie(this)
            return this
        }
    }

    //Detail
    suspend fun getCacheDetails(id: Int): Movie {
        var result: Movie? = cacheSource.getSpecificMovie(id)
        if (result == null)
            result = getNetworkDetails(id)
        return result
    }

    private suspend fun getNetworkDetails(id: Int): Movie {
        val result = networkSource.movieDetails(id)
        with(result.data?.movie!!) {
            if (this.cast == null)
                this.cast = emptyList()
            cacheSource.saveSpecificMovie(this)
            return this
        }
    }

    //Ranking
    private suspend fun getCacheRanking(page: Int): List<MoviesItem> =
        cacheSource.getRankMovies(20, page.times(10))

    suspend fun getNetworkRanking(page: Int): List<MoviesItem> {
        var result = emptyList<MoviesItem>()
        try {
            result = networkSource.rankMovies(page).data!!.movies
        } finally {
            if (result.isEmpty())
                result = getCacheRanking(page)
            if (result.isEmpty())
                throw IllegalArgumentException()
            else
                return result
        }
    }

    //FavMovie
    suspend fun checkFavMovieExist(id: Int): Boolean =
        cacheSource.checkFavMovieExist(id)

    suspend fun getAllFavMovie(): List<FavoriteMovie> {
        val result = cacheSource.getAllFavMovies()
        if (result.isEmpty())
            throw IllegalArgumentException()
        return result
    }

    suspend fun saveMovieToFav(movie: Movie) {
        cacheSource.saveFavMovie(movie.convertToFavorite())
        checkFavMovieExist(movie.id!!)
    }

    suspend fun deleteSpecificFavMovie(id: Int) {
        cacheSource.deleteFavMovie(id)
    }

    private suspend fun deleteLastCache(){
        if(firstNetworkLoad){
            cacheSource.deleteAllCacheMovies()
            firstNetworkLoad = false
        }
    }
}