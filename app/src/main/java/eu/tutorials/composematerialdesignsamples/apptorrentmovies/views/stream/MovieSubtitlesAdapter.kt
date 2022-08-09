package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.stream

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.masterwok.opensubtitlesandroid.models.OpenSubtitleItem
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.listeners.SubtitleListener
import eu.tutorials.composematerialdesignsamples.databinding.MovieSubtitleDialogRvBinding

class MovieSubtitlesAdapter(private val subtitleList: Array<OpenSubtitleItem>,
                            private val adapterListener: SubtitleListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtitlesViewHolder {
        val binding: MovieSubtitleDialogRvBinding = MovieSubtitleDialogRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubtitlesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SubtitlesViewHolder -> {
                holder.bind(subtitleList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return subtitleList.size
    }

    inner class SubtitlesViewHolder(itemView: MovieSubtitleDialogRvBinding) : RecyclerView.ViewHolder(itemView.root) {
        val movieSubName = itemView.movieSubName
        val movieSubLanguage = itemView.movieSubLanguage
        fun bind(item: OpenSubtitleItem) = with(itemView) {
            movieSubName.text = item.SubFileName
            movieSubLanguage.text = item.LanguageName
            setOnClickListener {
                adapterListener.onSubtitleClicked(item)
            }
        }
    }

}