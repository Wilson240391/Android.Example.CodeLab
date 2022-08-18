package eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain.cache

import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.*
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.FavoriteDao
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.MoviesDao
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.MoviesSuggestDao


class CacheSourceImpl(private val moviesDao: MoviesDao, private val favoriteDao: FavoriteDao, private val moviesSuggestDao: MoviesSuggestDao) :
    ICacheSource {

    override suspend fun getCacheMoviesList(category: String, limit: Int, page: Int): List<MoviesItem> =
        moviesDao.getAllMovies(category, limit, page)

    override suspend fun saveCacheMoviesList(list: List<MoviesItem>) =
        moviesDao.saveMovies(list)

    override suspend fun deleteAllCacheMovies() =
        moviesDao.deleteMoviesList()

    override suspend fun getSpecificMovie(id: Int): Movie =
        moviesDao.getSpecificMovie(id)

    override suspend fun saveSpecificMovie(movie: Movie) =
        moviesDao.saveSpecificMovie(movie)

    override suspend fun saveFavMovie(movie: FavoriteMovie) =
        favoriteDao.saveFavoriteMovie(movie)

    override suspend fun getAllFavMovies(): List<FavoriteMovie> =
        favoriteDao.getAllFavMovies()

    override suspend fun deleteFavMovie(id: Int) =
        favoriteDao.deleteFavMovie(id)

    override suspend fun checkFavMovieExist(id: Int) =
        favoriteDao.checkMovieExist(id)

    override suspend fun getRankMovies(limit: Int, page: Int): List<MoviesItem>  =
        moviesDao.getTopRankMovies(limit, page)

    override suspend fun saveSuggestMovie(list: List<MoviesSuggest>) =
        moviesSuggestDao.saveSuggestMovie(list)

    override suspend fun getSuggestionsMovie(id: Int): List<MoviesSuggest> =
        moviesSuggestDao.getSuggestMovie(id)
}