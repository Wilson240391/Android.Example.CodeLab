package eu.tutorials.composematerialdesignsamples.appmoviestmdb.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.databinding.ActivityTorrentBinding
import eu.tutorials.composematerialdesignsamples.databinding.TmdbActivityMovieBinding
import eu.tutorials.composematerialdesignsamples.databinding.TmdbFragmentMovieBinding

class TMDBMovieActivity : AppCompatActivity() {

    private lateinit var mBinding: TmdbActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = TmdbActivityMovieBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val navController = Navigation.findNavController(this,R.id.nav_host_fragment_tmdb)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }
}