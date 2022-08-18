package eu.tutorials.composematerialdesignsamples.appmoviestmdb.view.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.demo.ui.main.adapters.MoviesAdapter
import com.example.demo.ui.main.adapters.concat.PopularConcatAdapter
import com.example.demo.ui.main.adapters.concat.TopRatedConcatAdapter
import com.example.demo.ui.main.adapters.concat.UpcomingConcatAdapter
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.data.model.Movie
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.data.remote.MovieDataSource
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.domain.MovieRepositoryImpl
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.domain.RetrofitClient
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.viewmodel.MovieViewModel
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.viewmodel.MovieViewModelFactory
import eu.tutorials.composematerialdesignsamples.databinding.TmdbFragmentMovieBinding
import eu.tutorials.composematerialdesignsamples.util.tmdb.Resource

class MovieFragment : Fragment(R.layout.tmdb_fragment_movie), MoviesAdapter.OnMovieClickListener {

    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var binding: TmdbFragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> { MovieViewModelFactory(MovieRepositoryImpl(MovieDataSource(RetrofitClient.webservice))) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TmdbFragmentMovieBinding.bind(view)
        concatAdapter = ConcatAdapter()
        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0, UpcomingConcatAdapter(MoviesAdapter(it.data.first.results,this@MovieFragment)))
                        addAdapter(1, TopRatedConcatAdapter(MoviesAdapter(it.data.second.results,this@MovieFragment)))
                        addAdapter(2, PopularConcatAdapter(MoviesAdapter(it.data.third.results,this@MovieFragment)))

                    }
                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("FetchError", "Error: $it.exception ")
                    Toast.makeText(requireContext(), "Error: ${it.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movie.poster_path,movie.backdrop_path,movie.vote_average.toFloat(),movie.vote_count,movie.overview,movie.title,movie.original_language,movie.release_date)
        findNavController().navigate(action)
    }
}