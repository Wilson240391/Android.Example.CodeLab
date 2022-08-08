package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.databinding.FragmentFavoritesBinding
import eu.tutorials.composematerialdesignsamples.util.*
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.listeners.FavoriteListener
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.KoinComponent
import org.koin.core.get


class FavoritesFragment : Fragment(), FavoriteListener, KoinComponent,
    DiscreteScrollView.OnItemChangedListener<FavoritesAdapter.FavoriteViewHolder> {

    private lateinit var mbindig: FragmentFavoritesBinding
    private val favAdapter by lazy { FavoritesAdapter(this) }
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbindig = FragmentFavoritesBinding.inflate(inflater, container, false)
        return mbindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        initRecyclerView()
        observeObservers()
    }

    private fun observeObservers() {
        viewModel.observeFavMovies().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    mbindig.favMovieProgress.show()
                    mbindig.noMoviesContainer.gone()
                    mbindig.favoriteRV.gone()
                    mbindig.favMovieBackground.gone()
                }
                is Resource.Loaded -> {
                    mbindig.favMovieProgress.gone()
                    mbindig.noMoviesContainer.gone()
                    mbindig.favoriteRV.show()
                    mbindig.favMovieBackground.show()
                    favAdapter.apply {
                        addFavList(it.data!!)
                        mbindig.favMovieBackground.downloadImage(getMovieCover(lastPosition))
                    }
                }
                is Resource.Error -> {
                    mbindig.favMovieProgress.gone()
                    mbindig.noMoviesContainer.show()
                    mbindig.favoriteRV.gone()
                    mbindig.favMovieBackground.gone()
                }
            }
        })
    }

    private fun initRecyclerView() {
        mbindig.favoriteRV.apply {
            adapter = favAdapter
            addOnItemChangedListener(this@FavoritesFragment)
        }
        mbindig.favoriteRV.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.9f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build()
        )
    }

    override fun onDeleteFavMovie(id: Int) {
        viewModel.deleteSpecificMovie(id)
    }

    override fun onMovieClicked(id: Int, imageView: ImageView) {
        val extras =
            FragmentNavigatorExtras(imageView to resources.getString(R.string.transitionName))
        val action =
            FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(id)
        findNavController().navigate(action, extras)
        activity?.hideBottomNav()
    }

    override fun onResume() {
        super.onResume()
        activity?.showBottomNav()
    }



    override fun onCurrentItemChanged(
        viewHolder: FavoritesAdapter.FavoriteViewHolder?,
        adapterPosition: Int
    ) {
        mbindig.favMovieBackground.downloadImage(favAdapter.getMovieCover(adapterPosition))
    }
}