package eu.tutorials.composematerialdesignsamples.views.activitys.countries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.databinding.ActivityCountryBinding
import eu.tutorials.composematerialdesignsamples.databinding.ItemCountryBinding

class CountryActivity : Fragment() {

    private lateinit var mBinding: ActivityCountryBinding
    private lateinit var viewModel: CountryViewModel
    private lateinit var countriesAdapter: CountryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ActivityCountryBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize the ViewModel variable.
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.refresh()
        mBinding.countriesList.adapter = CountryListAdapter(this@CountryActivity)
        observeViewModel()
        //        mBinding!!.swipeRefreshLayout.setOnRefreshListener {
//            mBinding!!.swipeRefreshLayout.isRefreshing = false
//            viewModel.refresh()
//        }
    }

    fun observeViewModel() {
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->
            countries?.let {
                mBinding.countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it) }
        })

        viewModel.countryLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let { mBinding.listError.visibility = if(it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                mBinding.loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    mBinding.listError.visibility = View.GONE
                    //mBinding.countriesList.visibility = View.GONE
                }
            }
        })
    }
}