package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.TorrentsDetails
import eu.tutorials.composematerialdesignsamples.databinding.MovieQualityDialogRvBinding
import eu.tutorials.composematerialdesignsamples.util.formatText
import eu.tutorials.composematerialdesignsamples.util.torrents.listeners.QualityListener

class MovieDialogAdapter(private val qualityList: List<TorrentsDetails>, private val movieName: String,
                         private val adapterListener: QualityListener
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val binding: MovieQualityDialogRvBinding = MovieQualityDialogRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DialogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DialogViewHolder -> {
                holder.bind(qualityList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return qualityList.size
    }

    inner class DialogViewHolder(itemView: MovieQualityDialogRvBinding) : RecyclerView.ViewHolder(itemView.root) {
        val firstQualityTxt = itemView.firstQualityTxt
        val qualityCard = itemView.qualityCard
        fun bind(item: TorrentsDetails) = with(itemView) {
            firstQualityTxt.formatText(R.string.movieQualityType,item.type, item.quality)
            qualityCard.setBackgroundResource(R.drawable.quality_card_view_shape)
            setOnClickListener { adapterListener.selectQuality(item.url!!, movieName)}
        }

    }
}