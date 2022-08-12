package eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.cache.Dao


import androidx.room.*
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesSuggest

@Dao
interface MoviesSuggestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSuggestMovie(movies: List<MoviesSuggest>)

    @Query("SELECT * FROM MoviesSuggest WHERE movieId= :id ORDER BY timeSaved")
    suspend fun getSuggestMovie(id: Int): List<MoviesSuggest>

}