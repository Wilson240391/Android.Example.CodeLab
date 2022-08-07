package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.details

import android.app.AlertDialog
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.youtube.player.YouTubePlayer
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.CastItem
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.Movie
import eu.tutorials.composematerialdesignsamples.databinding.FragmentDetailsBinding
import eu.tutorials.composematerialdesignsamples.databinding.MovieDialogBinding
import eu.tutorials.composematerialdesignsamples.util.*
import eu.tutorials.composematerialdesignsamples.util.listeners.QualityListener
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.KoinComponent
import org.koin.core.get


class DetailsFragment : Fragment(), YouTubePlayer.OnFullscreenListener, KoinComponent,
    IOnBackPressed, QualityListener {

    private lateinit var mbindig: FragmentDetailsBinding
    private lateinit var mbindigMovieovieDialog: MovieDialogBinding
    private lateinit var ytPlayer: YouTubePlayer
    private var ytFullScreen = false
    private lateinit var viewModel: DetailsViewModel
    private val args: DetailsFragmentArgs by navArgs()
    //private val ytFragment by lazy { childFragmentManager.findFragmentById(R.id.trailerView) as YouTubePlayerSupportFragmentX? }
    private lateinit var alertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbindig = FragmentDetailsBinding.inflate(inflater, container, false)
        mbindigMovieovieDialog = MovieDialogBinding.inflate(inflater, container, false)
        return mbindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        viewModel = getViewModel()
        viewModel.observeMovieDetails(args.movieId)
        viewModel.checkMovieFav(args.movieId)
        mbindig.detailsMovieCover.transitionName = resources.getString(R.string.transitionName)
        observeObservers()
    }


    private fun observeObservers() {

        viewModel.observeFavMovieExist().observe(viewLifecycleOwner, Observer {
            //favMovie.isLiked = it
        })

        viewModel.observeMovieDetails().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    //mbindig.detailsNoInternet.gone()
                    mbindig.detailsContainer.show()
                }
                is Resource.Loaded -> {
                    mbindig.detailsContainer.show()
                    //mbindig.detailsNoInternet.gone()
                    showMovieDetails(it.data!!)
                }
                is Resource.Error -> {
                    mbindig.detailsContainer.gone()
                    //mbindig.detailsNoInternet.show()
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
            mbindig.initYoutube.apply {
                setOnClickListener {
                    initYoutubePlayer(ytTrailerCode!!)
                    gone()
                }
            }
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
//        mbindig.favMovie.setOnLikeListener(object : OnLikeListener {
//            override fun liked(likeButton: LikeButton?) {
//                viewModel.addMovieToFavorite(this@with)
//            }
//
//            override fun unLiked(likeButton: LikeButton?) {
//                viewModel.deleteMovieFromFavorite(id!!)
//            }
//        })
    }

    private fun initRecyclerViews(castList: List<CastItem>, screenShotImages: List<String>) {
        if (castList.isEmpty())
            mbindig.castLayout.gone()
        else
            mbindig.castRV.adapter = MovieCastAdapter(castList)
        mbindig.screenShotsRV.apply {
            adapter = ScreenShotsAdapter(screenShotImages)
//            val transformer: ScaleTransformer = get()
//            setItemTransformer(transformer)
        }
    }

    private fun initYoutubePlayer(ytTrailerCode: String) {
//        mbindig.ytFragment?.initialize(
//            DeveloperKey.DEVELOPER_API,
//            object : YouTubePlayer.OnInitializedListener {
//
//                override fun onInitializationSuccess(
//                    provider: YouTubePlayer.Provider?,
//                    player: YouTubePlayer?,
//                    wasRestored: Boolean
//                ) {
//                    try {
//                        if (ytTrailerCode.isEmpty())
//                            throw IllegalArgumentException()
//                        ytPlayer = player!!
//                        ytPlayer.setOnFullscreenListener(this@DetailsFragment)
//                        if (!wasRestored)
//                            ytPlayer.cueVideo(ytTrailerCode)
//                    } catch (e: IllegalArgumentException) {
//                        youtubeLayout.gone()
//                    }
//                }
//
//                override fun onInitializationFailure(
//                    p0: YouTubePlayer.Provider?,
//                    p1: YouTubeInitializationResult?
//                ) {
//                    showToast(resources.getString(R.string.checkYoutube))
//                }
//            })

    }

    private fun showMovieQualityDialog(movieData: Movie?, view: View) {



        val viewGroup: ViewGroup? = view.findViewById(android.R.id.content)

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
            ytPlayer.setFullscreen(false)
        else
            findNavController().popBackStack()
        return false
    }

    override fun selectQuality(movieUrl: String, movieName: String) {
//        findNavController().navigate(
//            DetailsFragmentDirections.actionDetailsFragmentToStreamFragment(
//                movieUrl,
//                movieName
//            )
//        )
//        alertDialog.dismiss()
    }
}