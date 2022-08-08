package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesItem
import eu.tutorials.composematerialdesignsamples.databinding.ExploreLayoutRvBinding
import eu.tutorials.composematerialdesignsamples.util.addList
import eu.tutorials.composematerialdesignsamples.util.distinctList
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.listeners.AdapterListener
import kotlinx.coroutines.*

class ExploreAdapter(private val adapterListener: AdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val moviesList: MutableList<MoviesItem> = mutableListOf()
    var currentPosition = 0
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val binding: ExploreLayoutRvBinding = ExploreLayoutRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExploreViewHolder(binding)
    }


    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExploreViewHolder -> {
                if (position < moviesList.size) {
                    holder.bind(moviesList[position])
                }
            }
        }
    }

    inner class ExploreViewHolder(itemView: ExploreLayoutRvBinding) : RecyclerView.ViewHolder(itemView.root) {
        val movieName = itemView.movieName
        val movieRating = itemView.movieRating
        val movieRatingNum = itemView.movieRatingNum
        val movieCover = itemView.movieCover
        val shimmerFrame = itemView.shimmerFrame
        fun bind(moviesItem: MoviesItem) = with(itemView) {
            with(moviesItem) {
                currentPosition = adapterPosition
                movieName.text = titleEnglish
                movieRating.rating = (rating?.div(2))?.toFloat()!!
                movieRatingNum.text = rating.toString()
                movieCover.apply {
                    Picasso.get().load(mediumCoverImage).into(this)
                    transitionName = backgroundImageOriginal
                }
                setOnClickListener {
                    adapterListener.openMovie(
                        id!!,
                        movieCover
                    )
                }
                shimmerFrame.visibility = View.GONE
            }
        }
    }

    fun addList(list: List<MoviesItem>) {
        this.moviesList.addList(list)
        notifyDataSetChanged()
    }

    fun updateList(list: List<MoviesItem>) {
        GlobalScope.launch { moviesList.distinctList(list) }
        notifyItemInserted(moviesList.size)
    }

    fun clearList(){
        moviesList.clear()
        notifyDataSetChanged()

    }
}