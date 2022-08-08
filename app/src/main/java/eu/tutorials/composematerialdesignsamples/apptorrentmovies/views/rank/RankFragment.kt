package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.rank

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.databinding.FragmentRankBinding
import eu.tutorials.composematerialdesignsamples.util.*
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.listeners.AdapterListener
import org.koin.android.viewmodel.ext.android.getViewModel

class RankFragment : Fragment(),
    AdapterListener {

    private lateinit var mbindig: FragmentRankBinding
    private val rankAdapter by lazy { RankAdapter(this) }
    private lateinit var viewModel: RankViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbindig = FragmentRankBinding.inflate(inflater, container, false)
        return mbindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        initRecyclerView()
        viewsListener()
        observeObservers()
    }

    private fun viewsListener() {
        mbindig.rankSwipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun observeObservers() {
        viewModel.observeRankMovies().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> mbindig.rankSwipeRefresh.isRefreshing = true
                is Resource.Loaded -> {
                    rankAdapter.addList(it.data!!)
                    mbindig.rankSwipeRefresh.isRefreshing = false
                    mbindig.rankNoInternet.root.gone()
                    mbindig.rankRV.show()
                }
                is Resource.Error -> {
                    println("========== ${it.msg}")
                    mbindig.rankSwipeRefresh.isRefreshing = false
                    if(rankAdapter.itemCount < 10){
                        mbindig.rankNoInternet.root.show()
                        mbindig.rankRV.gone()
                    }
                }
                is Resource.NewData -> {
                    mbindig.rankSwipeRefresh.isRefreshing = false
                    rankAdapter.updateList(it.data!!)
                }
            }
        })
    }

    private fun initRecyclerView() {
        mbindig.rankRV.apply {
            adapter = rankAdapter
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
    }

    override fun openMovie(movieID: Int, imageView: ImageView) {
        val extras =
            FragmentNavigatorExtras(imageView to resources.getString(R.string.transitionName))
        val action =
            RankFragmentDirections.actionRankFragmentToDetailsFragment(
                movieID
            )
        findNavController().navigate(action, extras)
        activity?.hideBottomNav()
    }

    override fun onResume() {
        super.onResume()
        activity?.showBottomNav()
    }

    override fun itemClicked(pos: Int) {
        TODO("Not yet implemented")
    }
}