package eu.tutorials.composematerialdesignsamples.views.activitys.countries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import eu.tutorials.composematerialdesignsamples.Util.getProgressDrawable
import eu.tutorials.composematerialdesignsamples.Util.loadImage
import eu.tutorials.composematerialdesignsamples.databinding.ItemCountryBinding
import eu.tutorials.composematerialdesignsamples.domain.models.Country

class CountryListAdapter(private val fragment: Fragment): ListAdapter<Country, CountryListAdapter.ViewHolder>(DiffCallback) {

    private var countries: List<Country> = listOf()

    fun updateCountries(newCountries: List<Country>) {
        countries = newCountries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder{
        val binding: ItemCountryBinding = ItemCountryBinding.inflate(LayoutInflater.from(fragment.context), parent, false)
        return ViewHolder(binding, fragment)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country)
    }

    class ViewHolder(private var view: ItemCountryBinding, fragment: Fragment): RecyclerView.ViewHolder(view.root) {
        //val progressDrawable = getProgressDrawable(fragment.context)
        fun bind(country: Country) {
            view.apply {
                name.text = country.countryName
                capital.text = country.capital
                //imageView.loadImage(country.flag, progressDrawable)
            }
        }
    }

    companion object{
        private val DiffCallback = object  : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem.countryName == newItem.countryName
            }
        }
    }
}