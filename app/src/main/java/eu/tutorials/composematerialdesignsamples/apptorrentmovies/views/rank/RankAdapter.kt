package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.rank

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesItem
import eu.tutorials.composematerialdesignsamples.databinding.MoviesRankRvBinding
import eu.tutorials.composematerialdesignsamples.util.addList
import eu.tutorials.composematerialdesignsamples.util.distinctList
import eu.tutorials.composematerialdesignsamples.util.downloadImage
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.listeners.AdapterListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RankAdapter(private val adapterListener: AdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val moviesList:MutableList<MoviesItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val binding: MoviesRankRvBinding = MoviesRankRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RankViewHolder -> {
                holder.bind(moviesList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }


    fun addList(list: List<MoviesItem>) {
        this.moviesList.addList(list)
        notifyDataSetChanged()
    }

    fun updateList(list: List<MoviesItem>) {
        GlobalScope.launch { moviesList.distinctList(list) }
        notifyItemInserted(moviesList.size)
    }

    inner class RankViewHolder(itemView: MoviesRankRvBinding) : RecyclerView.ViewHolder(itemView.root) {
        val rankMovieSort = itemView.rankMovieSort
        val rankMovieName = itemView.rankMovieName
        val rankMovieRating = itemView.rankMovieRating
        val rankMovieRatingTxt = itemView.rankMovieRatingTxt
        val rankMovieCategory = itemView.rankMovieCategory
        val rankMovieYear = itemView.rankMovieYear
        val rankMovieImg = itemView.rankMovieImg
        fun bind(item: MoviesItem) = with(itemView) {
            startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.slide_right_to_left))
            rankMovieSort.text = if(adapterPosition > 98) "99+" else (adapterPosition+1).toString().format(2)
            rankMovieName.text = item.titleEnglish
            rankMovieRating.rating = (item.rating?.div(2))?.toFloat()!!
            rankMovieRatingTxt.text = item.rating.toString()
            rankMovieCategory.text = item.genres?.get(0)
            rankMovieYear.text = item.year.toString()
            rankMovieImg.apply {
                downloadImage(item.mediumCoverImage)
                transitionName = item.backgroundImageOriginal
            }
            setOnClickListener {
                adapterListener.openMovie(item.id!!, rankMovieImg)
            }
        }
    }

}