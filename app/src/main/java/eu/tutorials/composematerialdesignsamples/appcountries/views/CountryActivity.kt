package eu.tutorials.composematerialdesignsamples.appcountries.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import eu.tutorials.composematerialdesignsamples.databinding.ActivityCountryBinding

class CountryActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCountryBinding
    private lateinit var viewModel: CountryViewModel
    private var countriesAdapter = CountryListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.refresh()
        mBinding.countriesList.layoutManager = LinearLayoutManager(applicationContext)
        mBinding.countriesList.adapter = countriesAdapter
        observeViewModel()
        mBinding!!.swipeRefreshLayout.setOnRefreshListener {
            mBinding!!.swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
    }

    fun observeViewModel() {
        viewModel.countries.observe(this, Observer {countries ->
            countries?.let {
                mBinding.countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it) }
        })
        viewModel.countryLoadError.observe(this, Observer { isError ->
            isError?.let { mBinding.listError.visibility = if(it) View.VISIBLE else View.GONE }
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                mBinding.loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    mBinding.listError.visibility = View.GONE
                    mBinding.countriesList.visibility = View.GONE
                }
            }
        })
    }
}