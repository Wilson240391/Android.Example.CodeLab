package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.stream

import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.github.se_bastiaan.torrentstream.StreamStatus
import com.github.se_bastiaan.torrentstream.Torrent
import com.github.se_bastiaan.torrentstream.TorrentStream
import com.github.se_bastiaan.torrentstream.listeners.TorrentListener
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.masterwok.opensubtitlesandroid.models.OpenSubtitleItem
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.util.torrents.listeners.SubtitleListener
import eu.tutorials.composematerialdesignsamples.databinding.ExoPlayerControlViewBinding
import eu.tutorials.composematerialdesignsamples.databinding.FragmentStreamBinding
import eu.tutorials.composematerialdesignsamples.databinding.MovieDialogBinding
import eu.tutorials.composematerialdesignsamples.util.*
import eu.tutorials.composematerialdesignsamples.util.torrents.Resource
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.KoinComponent
import org.koin.core.get
import java.io.File



class StreamFragment : Fragment(), KoinComponent, TorrentListener, SubtitleListener, Player.EventListener {

    private lateinit var mbindig: FragmentStreamBinding
    private lateinit var mbindigMovieovieDialog: MovieDialogBinding
    private lateinit var mbindigExoPlayer: ExoPlayerControlViewBinding
    private val args: StreamFragmentArgs by navArgs()
    private lateinit var simplePlayer: SimpleExoPlayer
    private lateinit var torrentStream: TorrentStream
    private lateinit var mergeMediaSource: MergingMediaSource
    private lateinit var mediaSource: ExtractorMediaSource
    private lateinit var alertDialog: AlertDialog
    private lateinit var viewModel: StreamViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbindig = FragmentStreamBinding.inflate(inflater, container, false)
        mbindigMovieovieDialog = MovieDialogBinding.inflate(inflater, container, false)
        mbindigExoPlayer = ExoPlayerControlViewBinding.inflate(inflater, container, false)
        return mbindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        viewModel = getViewModel()
        initTorrentStream()
        observeObservers()
        viewsListener()
    }

    private fun initTorrentStream() {
        torrentStream = get()
        torrentStream.startStream(args.movieUrl)
        torrentStream.addListener(this)
    }

    private fun observeObservers() {
        viewModel.getSubtitlesData().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    mbindig.progressContainer.show()
                }
                is Resource.Loaded -> {
                    if (it.data!!.isNotEmpty())
                        showMovieSubtitlesDialog(it.data, requireView())
                    else
                        showToast(resources.getString(R.string.noSubtitle))
                    mbindig.progressContainer.gone()
                }
                is Resource.Error -> {
                    mbindig.progressContainer.gone()
                    showToast(it.msg!!)
                }
            }
            simplePlayer.stopPlayer()
        })

        viewModel.getSubtitleStatus().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loaded -> {
                    alertDialog.dismiss()
                    addSubtitleToPlayer(it.data)
                }
                is Resource.Error -> showToast(resources.getString(R.string.subtitleError))
            }
        })
    }

    private fun viewsListener() {
        mbindig.moviePlayer.setControllerVisibilityListener {
            with(activity?.window?.decorView) {
                if (it == 0)
                    this?.systemUiVisibility = showSystemUI()
                else
                    this?.systemUiVisibility = hideSystemUI()
            }
        }
        mbindigExoPlayer.movieSubtitle.setOnClickListener {
            viewModel.searchMovieSubtitle(args.movieName)
        }
    }

    private fun initPlayer(path: String) {
        simplePlayer = get()
        mbindig.moviePlayer.player = simplePlayer
        val factory: DefaultDataSourceFactory = get()
        mediaSource = ExtractorMediaSource.Factory(factory).createMediaSource(Uri.parse(path))
        mergeMediaSource = MergingMediaSource(mediaSource)
        simplePlayer.apply {
            startPlayer(mergeMediaSource)
            addListener(this@StreamFragment)
        }
    }

    private fun showMovieSubtitlesDialog(listOfSubtitles: Array<OpenSubtitleItem>, view: View) {
        AlertDialog.Builder(view.context).apply {
            setTitle(resources.getString(R.string.movieSubtitle))
            setView(mbindigMovieovieDialog.root)
            alertDialog = create()
        }
        mbindigMovieovieDialog.movieDialogRV.apply {
            addDividers()
            adapter = MovieSubtitlesAdapter(listOfSubtitles, this@StreamFragment)
        }
        alertDialog.show()
    }

    private fun addSubtitleToPlayer(data: Uri?) {
        val factory: DefaultDataSourceFactory = get()
        val textFormat: Format = get()
        val textMediaSource = SingleSampleMediaSource.Factory(factory)
            .createMediaSource(data, textFormat, C.TIME_UNSET)
        mergeMediaSource = MergingMediaSource(mediaSource, textMediaSource)
        simplePlayer.addingSubtitle(mergeMediaSource, simplePlayer.currentPosition)
    }

    override fun onSubtitleClicked(subtitle: OpenSubtitleItem) {
        viewModel.downloadSubtitle(
            subtitle,
            Uri.fromFile(File("${activity?.getExternalFilesDir(null)?.absolutePath}/${subtitle.SubFileName}"))
        )

    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        simplePlayer.seekPlayer(simplePlayer.contentPosition, mergeMediaSource)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playWhenReady && playbackState == Player.STATE_READY)
            mbindig.progressContainer.gone()
        else if (playWhenReady && playbackState == Player.STATE_BUFFERING)
            mbindig.progressContainer.show()
    }

    override fun onStreamReady(torrent: Torrent?) {
        initPlayer(torrent?.videoFile?.absolutePath!!)
    }

    override fun onStreamProgress(torrent: Torrent?, status: StreamStatus?) {
        try {
            mbindig.streamSeeds.formatText(R.string.streamSeeds, status?.seeds)
            mbindig.streamSpeed.formatText(R.string.streamDownloadSpeed, status?.downloadSpeed?.div(1024))
            mbindig.streamProgressTxt.formatText(R.string.streamProgress, status?.progress, "%")
        } catch (e: IllegalStateException) {
            println("ERROR: $e")
        }
    }

    override fun onPause() {
        super.onPause()
        if (torrentStream.currentTorrent != null)
            torrentStream.currentTorrent.pause()
        if (this::simplePlayer.isInitialized) simplePlayer.stopPlayer()

    }

    override fun onResume() {
        super.onResume()
        if (torrentStream.currentTorrent != null)
            torrentStream.currentTorrent.resume()
        if (this::simplePlayer.isInitialized) simplePlayer.resumePlayer()
    }


    override fun onDestroy() {
        super.onDestroy()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        torrentStream.removeListener(this)
        torrentStream.stopStream()
        if (this::simplePlayer.isInitialized)
            simplePlayer.release()
    }

    override fun onStreamPrepared(torrent: Torrent?) {
    }

    override fun onStreamStopped() {
    }

    override fun onStreamStarted(torrent: Torrent?) {
    }

    override fun onStreamError(torrent: Torrent?, e: Exception?) {
        println("Error: $e")
    }
}