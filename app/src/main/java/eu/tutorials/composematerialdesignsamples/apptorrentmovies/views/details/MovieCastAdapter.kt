package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.details

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.CastItem
import eu.tutorials.composematerialdesignsamples.databinding.MovieCastRvBinding
import eu.tutorials.composematerialdesignsamples.util.downloadImage

class MovieCastAdapter(private val castList: List<CastItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding: MovieCastRvBinding = MovieCastRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CastViewHolder -> {
                holder.bind(castList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    class CastViewHolder(itemView: MovieCastRvBinding) : RecyclerView.ViewHolder(itemView.root) {
        val castImage = itemView.castImage
        val movieCastCard = itemView.movieCastCard
        val castName = itemView.castName
        fun bind(item: CastItem) = with(itemView) {
            castImage.downloadImage(item.urlSmallImage, true)
            movieCastCard.setBackgroundResource(R.drawable.circle_shape)
            castName.text = item.name
        }
    }
}