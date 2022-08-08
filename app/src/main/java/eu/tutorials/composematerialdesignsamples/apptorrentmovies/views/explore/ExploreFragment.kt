package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.explore

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.GridLayoutAnimationController
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.databinding.FragmentExploreBinding
import eu.tutorials.composematerialdesignsamples.util.*
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.listeners.AdapterListener
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.KoinComponent
import org.koin.core.get


class ExploreFragment : Fragment(), AdapterListener, IOnBackPressed, KoinComponent {

    private lateinit var mbindig: FragmentExploreBinding
    private lateinit var viewModel: ExploreViewModel
    private val exploreAdapter by lazy { ExploreAdapter(this) }
    private val categoryList by lazy { Constants.getMoviesGenre() }
    private val categoryAdapter by lazy { CategoryAdapter(categoryList, this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbindig = FragmentExploreBinding.inflate(inflater, container, false)
        return mbindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        initRecyclerViews()
        viewsListener(view.context)
        observeObservers()
    }

    private fun observeObservers() {
        viewModel.getMovies().observe(viewLifecycleOwner, Observer { dataObserve ->
            when (dataObserve) {
                is Resource.Error -> {
                    mbindig.refreshMoviesList.isRefreshing = false
                    if (exploreAdapter.itemCount < 10) {
                        mbindig.homeInternetConnection.root.show()
                        mbindig.exploreRV.gone()
                    }
                }
                is Resource.Loading -> mbindig.refreshMoviesList.isRefreshing = true
                is Resource.Loaded -> {
                    mbindig.refreshMoviesList.isRefreshing = false
                    mbindig.homeInternetConnection.root.gone()
                    mbindig.exploreRV.show()
                    with(dataObserve.data) {
                        exploreAdapter.addList(this!!)
                        animationRV()
                        for(i in this)
                            println("Loaded======> ${i.timeSaved}")
                    }
                }
                is Resource.NewData -> {
                    mbindig.refreshMoviesList.isRefreshing = false
                    with(dataObserve.data) {
                        exploreAdapter.updateList(this!!)
                        for(i in this)
                            println("NewData======> ${i.timeSaved}")
                    }
                }
            }
        })
    }

    private fun viewsListener(context: Context) {
        mbindig.searchMovie.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH && mbindig.searchMovie.text.isNotEmpty()) {
                viewModel.searchMovie(mbindig.searchMovie.text.toString())
                closeSearch(context)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        mbindig.refreshMoviesList.setOnRefreshListener { viewModel.refreshData() }
    }

    private fun closeSearch(context: Context) {
        mbindig.searchMovie.text.clear()
        mbindig.searchMovie.clearFocus()
        resetCategoryRV()
        val input = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun resetCategoryRV() {
        if (categoryAdapter.selectedIndex > 0) {
            mbindig.categoryRV.smoothScrollToPosition(0)
            categoryAdapter.reset()
        }
    }

    private fun animationRV() {
        val controller: GridLayoutAnimationController = get()
        mbindig.exploreRV.layoutAnimation = controller
        mbindig.exploreRV.startLayoutAnimation()
    }

    private fun initRecyclerViews() {
        mbindig.exploreRV.apply {
            adapter = exploreAdapter
            itemAnimator = null
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(
                    recyclerView: RecyclerView,
                    newState: Int
                ) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1))
                        viewModel.loadMoreData()
                }
            })
        }
        mbindig.categoryRV.apply {
            adapter = categoryAdapter
        }
    }


    override fun itemClicked(pos: Int) {
        viewModel.moviesCategoryList(categoryList[pos].second)
        exploreAdapter.clearList()
    }

    override fun openMovie(movieID: Int, imageView: ImageView) {
        val extras = FragmentNavigatorExtras(imageView to resources.getString(R.string.transitionName))
        val action = ExploreFragmentDirections.actionExploreFragmentToDetailsFragment(movieID)
        findNavController().navigate(action, extras)
        activity?.hideBottomNav()
    }

    override fun onResume() {
        super.onResume()
        activity?.showBottomNav()
    }

    override fun onBackPressed(): Boolean {
        if (exploreAdapter.currentPosition > 10) {
            exploreAdapter.clearList()
            viewModel.refreshData()
            return false
        }
        return true
    }

}