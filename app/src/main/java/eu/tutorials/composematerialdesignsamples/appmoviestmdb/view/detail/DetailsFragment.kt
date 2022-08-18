package eu.tutorials.composematerialdesignsamples.appmoviestmdb.view.detail

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.animation.GridLayoutAnimationController
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.youtube.player.YouTubePlayer
import com.like.LikeButton
import com.like.OnLikeListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.CastItem
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.Movie
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.player.PlayerActivity
import eu.tutorials.composematerialdesignsamples.util.torrents.listeners.DelegatedYouTubePlayerListener
import eu.tutorials.composematerialdesignsamples.databinding.TmdbFragmentDetailsBinding
import eu.tutorials.composematerialdesignsamples.util.*
import eu.tutorials.composematerialdesignsamples.util.torrents.IOnBackPressed
import eu.tutorials.composematerialdesignsamples.util.torrents.Resource
import eu.tutorials.composematerialdesignsamples.util.torrents.listeners.AdapterListener
import eu.tutorials.composematerialdesignsamples.util.torrents.listeners.QualityListener
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.KoinComponent
import org.koin.core.get

class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var binding: TmdbFragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TmdbFragmentDetailsBinding.bind(view)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.posterImageUrl}").centerCrop().into(binding.imgMovie)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.backgroundImageUrl}").centerCrop().into(binding.imgBackground)
        binding.txtDescription.text = args.overview
        binding.txtMovieTitle.text = args.title
        binding.txtLanguage.text = "Language ${args.language}"
        binding.txtRating.text = "${args.voteAverage} (${args.voteCount} Reviews)"
        binding.txtReleased.text = "Released ${args.releaseDate}"
    }
}