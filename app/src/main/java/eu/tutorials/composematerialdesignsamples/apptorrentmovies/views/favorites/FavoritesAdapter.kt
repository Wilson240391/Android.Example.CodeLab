package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.FavoriteMovie
import eu.tutorials.composematerialdesignsamples.util.torrents.listeners.FavoriteListener
import eu.tutorials.composematerialdesignsamples.databinding.FavoriteLayoutRvBinding
import eu.tutorials.composematerialdesignsamples.util.downloadImage


class FavoritesAdapter(private val favoriteListener: FavoriteListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val favoritesList: MutableList<FavoriteMovie> = mutableListOf()
    var lastPosition = 0
    private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding: FavoriteLayoutRvBinding = FavoriteLayoutRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteViewHolder -> {
                holder.bind(favoritesList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    fun addFavList(list: List<FavoriteMovie>) {
        favoritesList.apply {
            clear()
            addAll(list)
            notifyItemRangeChanged(lastPosition, favoritesList.size)
        }
    }

    fun getMovieCover(pos: Int): String {
        return try {
            favoritesList[pos].mediumCoverImage!!
        }catch (e: IndexOutOfBoundsException){
            favoritesList[0].mediumCoverImage!!
        }
    }

    inner class FavoriteViewHolder(itemView: FavoriteLayoutRvBinding) : RecyclerView.ViewHolder(itemView.root) {
        val favMovieCover = itemView.favMovieCover
        val favMovieName = itemView.favMovieName
        val favMovie = itemView.favMovie
        fun bind(item: FavoriteMovie) = with(itemView) {
            favMovieCover.apply {
                downloadImage(item.mediumCoverImage)
                transitionName = item.backgroundImageOriginal
            }
            favMovieName.text = item.titleEnglish
            favMovie.setOnClickListener {
                lastPosition = adapterPosition
                favoriteListener.onDeleteFavMovie(item.id!!)
            }
            setOnClickListener { favoriteListener.onMovieClicked(item.id!!, favMovieCover) }
        }
    }
}


