package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.details

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.youtube.player.YouTubePlayer
import com.like.LikeButton
import com.like.OnLikeListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.yarolegovich.discretescrollview.transform.Pivot
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.CastItem
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.Movie
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.player.PlayerActivity
import eu.tutorials.composematerialdesignsamples.util.torrents.listeners.DelegatedYouTubePlayerListener
import eu.tutorials.composematerialdesignsamples.databinding.FragmentDetailsBinding
import eu.tutorials.composematerialdesignsamples.databinding.MovieDialogBinding
import eu.tutorials.composematerialdesignsamples.util.*
import eu.tutorials.composematerialdesignsamples.util.news.ScaleTransformer
import eu.tutorials.composematerialdesignsamples.util.torrents.IOnBackPressed
import eu.tutorials.composematerialdesignsamples.util.torrents.Resource
import eu.tutorials.composematerialdesignsamples.util.torrents.listeners.QualityListener
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.KoinComponent
import org.koin.core.get

class DetailsFragment : Fragment(), YouTubePlayer.OnFullscreenListener, KoinComponent,
    IOnBackPressed, QualityListener {

    private lateinit var mbindig: FragmentDetailsBinding
    private lateinit var mbindigMovieovieDialog: MovieDialogBinding
    private var ytFullScreen = false
    private lateinit var viewModel: DetailsViewModel
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var alertDialog: AlertDialog
    private var youTubePlayerCurrentPosition: Float = 0f
    private lateinit var player: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
    companion object {
        const val YOUTUBE_PLAYER_VIEW_REQUEST_CODE = 189
        private const val SCROLL_STATE = "com.kpstv.yts:detailfragment:scroll_state"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbindig = FragmentDetailsBinding.inflate(inflater, container, false)
        mbindigMovieovieDialog = MovieDialogBinding.inflate(inflater, container, false)
        initializeYoutubePlayer()
        return mbindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        viewModel = getViewModel()
        viewModel.observeMovieDetails(args.movieId)
        viewModel.checkMovieFav(args.movieId)
        mbindig.detailsMovieCover.transitionName = resources.getString(R.string.transitionName)
        observeObservers()
    }


    private fun observeObservers() {
        viewModel.observeFavMovieExist().observe(viewLifecycleOwner, Observer {
            mbindig.favMovie.isLiked = it
        })
        viewModel.observeMovieDetails().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    mbindig.detailsNoInternet.root.gone()
                    mbindig.detailsContainer.show()
                }
                is Resource.Loaded -> {
                    mbindig.detailsContainer.show()
                    mbindig.detailsNoInternet.root.gone()
                    showMovieDetails(it.data!!)
                }
                is Resource.Error -> {
                    mbindig.detailsContainer.gone()
                    mbindig.detailsNoInternet.root.show()
                }
            }
        })
    }

    private fun showMovieDetails(movie: Movie) {
        with(movie) {
            mbindig.detailsMovieBackground.downloadImage(backgroundImageOriginal)
            mbindig.detailsMovieCover.downloadImage(mediumCoverImage)
            mbindig.detailsMovieName.text = titleEnglish
            mbindig.movieCategory.addCategories(genres!!)
            mbindig.movieMpaRating.textOrGone(mpaRating)
            mbindig.movieDescription.text = descriptionFull
            mbindig.movieRatingTxt.formatText(R.string.mpaRating, rating.toString())
            mbindig.youtubeLayout.afYtPreviewImage.apply {
                setOnClickListener {
                    setPreviews(movie)
                    gone()
                }
            }
            setPreviews(movie)
            mbindig.shimmerFrame.visibility = View.GONE
            mbindig.youtubeLayout.shimmerFrameYT.visibility = View.GONE
            initRecyclerViews(
                cast!!,
                listOf(
                    mediumScreenshotImage1!!,
                    mediumScreenshotImage2!!,
                    mediumScreenshotImage3!!
                )
            )
            viewsListener(this)
        }
    }

    private fun viewsListener(movie: Movie) = with(movie) {

        mbindig.playMovieFAB.setOnClickListener {
            showMovieQualityDialog(this, requireView())
        }
        mbindig.detailsBackArrow.setOnClickListener {
            onBackPressed()
        }
        mbindig.favMovie.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                viewModel.addMovieToFavorite(this@with)
            }

            override fun unLiked(likeButton: LikeButton?) {
                viewModel.deleteMovieFromFavorite(id!!)
            }
        })
    }

    private fun initRecyclerViews(castList: List<CastItem>, screenShotImages: List<String>) {
        if (castList.isEmpty())
            mbindig.castLayout.gone()
        else
            mbindig.castRV.adapter = MovieCastAdapter(castList)
        mbindig.screenShotsRV.apply {
            val transformer: com.yarolegovich.discretescrollview.transform.ScaleTransformer = get()
            setItemTransformer(transformer)
            adapter = ScreenShotsAdapter(screenShotImages)
        }
    }

    private fun setPreviews(movie: Movie) {
        Glide.with(requireView()).asBitmap().load(movie.largeCoverImage)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    if (!isRemoving) {
                        //mbindig.root.enableDelayedTransition()
                        mbindig.youtubeLayout.afYtPreviewImage.setImageBitmap(resource)
                        mbindig.youtubeLayout.afYtPreviewImage.setOnClickListener {
                            if (::player.isInitialized) {
                                movie.ytTrailerCode?.let { it1 -> player.loadVideo(it1, 0f) }
                                mbindig.youtubeLayout.trailerView.show()
                            } else {
                                mbindig.youtubeLayout.afYtPreviewPlay.visibility = View.GONE
                                mbindig.youtubeLayout.afYtPreviewProgressBar.show()
                            }
                        }
                        mbindig.youtubeLayout.buttonFullscreen.setOnClickListener {
                            player.pause()
                            val i = Intent(requireContext(), PlayerActivity::class.java)
                            i.putExtra(PlayerActivity.VIDEO_ID, movie.ytTrailerCode)
                            i.putExtra(PlayerActivity.LAST_PLAYED, youTubePlayerCurrentPosition)
                            startActivityForResult(i, YOUTUBE_PLAYER_VIEW_REQUEST_CODE)
                        }
                        mbindig.youtubeLayout.afYtPreview.show()
                    }
                }
            })
        mbindig.root.visibility = View.VISIBLE
    }

    private fun initializeYoutubePlayer() {
        mbindig.youtubeLayout.trailerView.enableAutomaticInitialization = false;
        mbindig.youtubeLayout.trailerView.initialize(object :
            YouTubePlayerListener by DelegatedYouTubePlayerListener {
            override fun onCurrentSecond(
                youTubePlayer: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer,
                second: Float
            ) {
                youTubePlayerCurrentPosition = second
            }
            override fun onReady(youTubePlayer: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer) {
                player = youTubePlayer
                mbindig.youtubeLayout.afYtPreviewProgressBar.visibility = View.GONE
                mbindig.youtubeLayout.afYtPreviewPlay.visibility = View.VISIBLE
            }
            override fun onStateChange(
                youTubePlayer: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                when (state) {
                    PlayerConstants.PlayerState.PLAYING -> mbindig.youtubeLayout.buttonFullscreen.show()
                    PlayerConstants.PlayerState.PAUSED -> mbindig.youtubeLayout.buttonFullscreen.visibility = View.GONE
                    PlayerConstants.PlayerState.ENDED -> {
                        mbindig.youtubeLayout.afYtPreviewPlay.visibility = View.VISIBLE
                        mbindig.youtubeLayout.afYtPreviewProgressBar.visibility = View.GONE
                        mbindig.youtubeLayout.trailerView.visibility = View.GONE
                        mbindig.youtubeLayout.buttonFullscreen.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun showMovieQualityDialog(movieData: Movie?, view: View) {
        AlertDialog.Builder(view.context).apply {
            setTitle(resources.getString(R.string.movieQuality))
            setView(mbindigMovieovieDialog.root)
            alertDialog = create()
        }
        mbindigMovieovieDialog.movieDialogRV.adapter = MovieDialogAdapter(
            movieData?.torrents!!,
            "${movieData.titleEnglish} ${movieData.year}", this
        )
        alertDialog.show()
    }


    override fun onFullscreen(screen: Boolean) {
        ytFullScreen = screen
    }

    override fun onBackPressed(): Boolean {
        if (ytFullScreen)
            //player.setFullscreen(false)
        else
            findNavController().popBackStack()
        return false
    }

    override fun selectQuality(movieUrl: String, movieName: String) {
        Permissions.verifyStoragePermission(this) {
            findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToStreamFragment(
                    movieUrl,
                    movieName
                )
            )
            alertDialog.dismiss()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Permissions.processStoragePermission(requestCode, grantResults)
    }

    override fun onDestroyView() {
        if (::player.isInitialized) player.pause()
        requireView().findViewById<YouTubePlayerView>(R.id.trailerView).release()
        super.onDestroyView()
    }
}