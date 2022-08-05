package eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.cache

import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.FavoriteMovie
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.Movie
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesItem

interface ICacheSource {

    suspend fun getCacheMoviesList(category: String, limit: Int, page: Int):List<MoviesItem>
    suspend fun saveCacheMoviesList(list: List<MoviesItem>)
    suspend fun deleteAllCacheMovies()
    suspend fun getSpecificMovie(id: Int): Movie
    suspend fun saveSpecificMovie(movie: Movie)

    suspend fun saveFavMovie(movie: FavoriteMovie)
    suspend fun getAllFavMovies(): List<FavoriteMovie>
    suspend fun deleteFavMovie(id: Int)
    suspend fun checkFavMovieExist(id: Int): Boolean

    suspend fun getRankMovies(limit: Int, page: Int):List<MoviesItem>
}