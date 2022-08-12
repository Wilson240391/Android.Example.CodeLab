package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.details

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnAttach
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.squareup.picasso.Picasso
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesItem
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesSuggest
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.explore.ExploreAdapter
import eu.tutorials.composematerialdesignsamples.databinding.ExploreLayoutRvBinding
import eu.tutorials.composematerialdesignsamples.databinding.ItemMovieBinding
import eu.tutorials.composematerialdesignsamples.util.*
import eu.tutorials.composematerialdesignsamples.util.torrents.listeners.AdapterListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SimilarAdapter (private val adapterListener: AdapterListener):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val moviesList: MutableList<MoviesSuggest> = mutableListOf()
    var currentPosition = 0
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExploreViewHolder(binding)
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExploreViewHolder -> {
                if (position < moviesList.size) {
                    holder.ivMovie.isShimmering = true
                    holder.bind(moviesList[position])
                }
            }
        }
    }

    inner class ExploreViewHolder(itemView: ItemMovieBinding) : RecyclerView.ViewHolder(itemView.root) {
        val nameMovieTv = itemView.nameMovieTv
        var ivMovie = itemView.ivMovie
        fun bind(movie: MoviesSuggest) = with(itemView) {
            with(movie) {
                nameMovieTv.text = movie.title
                ivMovie.apply {
                    itemView.doOnAttach { view ->
                        ivMovie.isShimmering = false
                    }
                    Picasso.get().load(mediumCoverImage).into(this)
                    transitionName = movie.backgroundImageOriginal
                }
                ivMovie.setOnClickListener { view ->
                    adapterListener.openMovie(id!!, ivMovie)
                }
            }
        }
    }

    fun addList(list: List<MoviesSuggest>) {
        this.moviesList.addListSugg(list)
        notifyDataSetChanged()
    }

    fun updateList(list: List<MoviesSuggest>) {
        GlobalScope.launch { moviesList.distinctListSugge(list) }
        notifyItemInserted(moviesList.size)
    }

    fun clearList(){
        moviesList.clear()
        notifyDataSetChanged()

    }

}