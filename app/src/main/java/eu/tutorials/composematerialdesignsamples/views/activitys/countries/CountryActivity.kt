package eu.tutorials.composematerialdesignsamples.views.activitys.countries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.databinding.ActivityCountryBinding
import eu.tutorials.composematerialdesignsamples.databinding.ItemCountryBinding

class CountryActivity : AppCompatActivity() {

    private var mBinding: ActivityCountryBinding? = null
    lateinit var viewModel: CountryViewModel
    private val countriesAdapter = CountryListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        mBinding = ActivityCountryBinding.inflate(layoutInflater)

        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.refresh()

        mBinding!!.countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        mBinding!!.swipeRefreshLayout.setOnRefreshListener {
            mBinding!!.swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.countries.observe(this, Observer {countries ->
            countries?.let {
                mBinding!!.countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it) }
        })

        viewModel.countryLoadError.observe(this, Observer { isError ->
            isError?.let { mBinding!!.listError.visibility = if(it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                mBinding!!.loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    mBinding!!.listError.visibility = View.GONE
                    mBinding!!.countriesList.visibility = View.GONE
                }
            }
        })
    }
}