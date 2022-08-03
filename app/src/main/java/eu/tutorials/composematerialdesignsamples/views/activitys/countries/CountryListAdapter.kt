package eu.tutorials.composematerialdesignsamples.views.activitys.countries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import eu.tutorials.composematerialdesignsamples.Util.getProgressDrawable
import eu.tutorials.composematerialdesignsamples.Util.loadImage
import eu.tutorials.composematerialdesignsamples.databinding.ItemCountryBinding
import eu.tutorials.composematerialdesignsamples.domain.models.Country

class CountryListAdapter(): ListAdapter<Country, CountryListAdapter.ViewHolder>(DiffCallback) {

    private var countries: List<Country> = listOf()

    fun updateCountries(newCountries: List<Country>) {
        countries = newCountries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder{
        val binding: ItemCountryBinding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]
        holder.countryName.text = country.countryName
        holder.countryCapital.text = country.capital
        holder.imageView.loadImage(country.flag, holder.progressDrawable)
    }

    class ViewHolder(view: ItemCountryBinding, parent: ViewGroup): RecyclerView.ViewHolder(view.root) {
        val imageView = view.imageView
        val countryName = view.name
        val countryCapital = view.capital
        val progressDrawable = getProgressDrawable(parent.context)

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