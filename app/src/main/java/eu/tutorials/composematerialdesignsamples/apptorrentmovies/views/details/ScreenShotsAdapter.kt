package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.details

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import eu.tutorials.composematerialdesignsamples.databinding.ScreenShotMovieRvBinding

class ScreenShotsAdapter(private val screenShotList: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenShotsViewHolder {
        val binding: ScreenShotMovieRvBinding =
            ScreenShotMovieRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScreenShotsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScreenShotsViewHolder -> {
                holder.bind(screenShotList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return screenShotList.size
    }


    inner class ScreenShotsViewHolder(itemView: ScreenShotMovieRvBinding) : RecyclerView.ViewHolder(itemView.root) {
        val screenShotImage = itemView.screenShotImage
        fun bind(item: String) = with(itemView) {
            Picasso.get().load(item).into(screenShotImage)
        }
    }

}