package eu.tutorials.composematerialdesignsamples.apptorrentmovies.player

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import eu.tutorials.composematerialdesignsamples.databinding.ActivityPlayerBinding
import eu.tutorials.composematerialdesignsamples.databinding.FragmentExploreBinding
import eu.tutorials.composematerialdesignsamples.util.WindowUtils

class PlayerActivity : AppCompatActivity() {

    companion object {
        const val VIDEO_ID = "com.kpstv,yts.video_id"
        const val LAST_PLAYED = "com.kpstv,yts.last_played"
    }

    private var youTubePlayerCurrentPosition = 0f
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.youtubePlayerView.enterFullScreen()
        binding.youtubePlayerView.initialize(object : YouTubePlayerListener {

            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(intent.extras?.getString(VIDEO_ID)!!, intent.extras?.getFloat(LAST_PLAYED) ?: 0f)
            }

            override fun onApiChange(youTubePlayer: YouTubePlayer) {}

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                youTubePlayerCurrentPosition = second
            }

            override fun onError(
                youTubePlayer: YouTubePlayer,
                error: PlayerConstants.PlayerError
            ) {
            }

            override fun onPlaybackQualityChange(
                youTubePlayer: YouTubePlayer,
                playbackQuality: PlayerConstants.PlaybackQuality
            ) {
            }

            override fun onPlaybackRateChange(
                youTubePlayer: YouTubePlayer,
                playbackRate: PlayerConstants.PlaybackRate
            ) {
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {}

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {}

            override fun onVideoLoadedFraction(
                youTubePlayer: YouTubePlayer,
                loadedFraction: Float
            ) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        WindowUtils.activateFullScreen(this)
    }

    override fun onBackPressed() {
        this.setResult(RESULT_OK, Intent().apply { putExtra(LAST_PLAYED, youTubePlayerCurrentPosition) })
        super.onBackPressed()
    }

    override fun onDestroy() {
        binding.youtubePlayerView.release()
        super.onDestroy()
    }
}
