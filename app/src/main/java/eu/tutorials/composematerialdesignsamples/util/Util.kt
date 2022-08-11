package eu.tutorials.composematerialdesignsamples.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.details.DetailsFragment

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun RecyclerView.setHorizontalRecyclerView(context: Context, recyclerView: RecyclerView){
    recyclerView.layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL ,false)
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

private val PUNCTUATION = listOf(", ", "; ", ": ", " ")
fun String.smartTruncate(length: Int): String {
    val words = split(" ")
    var added = 0
    var hasMore = false
    val builder = StringBuilder()
    for (word in words) {
        if (builder.length > length) {
            hasMore = true
            break
        }
        builder.append(word)
        builder.append(" ")
        added += 1
    }
    PUNCTUATION.map {
        if (builder.endsWith(it)) {
            builder.replace(builder.length - it.length, builder.length, "")
        }
    }
    if (hasMore) {
        builder.append("...")
    }
    return builder.toString()
}

//Binding adapter used to hide the spinner once data is available.
@BindingAdapter("isNetworkError", "playlist")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, playlist: Any?) {
    view.visibility = if (playlist != null) View.GONE else View.VISIBLE

    if(isNetWorkError) {
        view.visibility = View.GONE
    }
}

//Binding adapter used to display images from URL using Glide
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}