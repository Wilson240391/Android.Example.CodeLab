package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.explore

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.tutorials.composematerialdesignsamples.databinding.CategoryLayoutRvBinding
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.listeners.AdapterListener

class CategoryAdapter(private val categoryList: MutableList<Pair<String,String>>, private var adapterListener: AdapterListener) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var selectedIndex = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding: CategoryLayoutRvBinding = CategoryLayoutRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position].first)
    }

    fun reset(){
        selectedIndex = 0
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: CategoryLayoutRvBinding) : RecyclerView.ViewHolder(itemView.root) {
        val categoryName = itemView.categoryName
        val categoryCardView = itemView.categoryCardView
        fun bind(name: String) {
            with(itemView) {
                categoryName.text = name
                categoryCardView.setCardBackgroundColor(
                    if (adapterPosition == selectedIndex)
                        Color.parseColor("#1750C4")
                    else
                        Color.parseColor("#1e2747")
                )
            }
            itemView.setOnClickListener {
                selectedIndex = adapterPosition
                notifyDataSetChanged()
                adapterListener.itemClicked(selectedIndex)
            }
        }

    }

}
