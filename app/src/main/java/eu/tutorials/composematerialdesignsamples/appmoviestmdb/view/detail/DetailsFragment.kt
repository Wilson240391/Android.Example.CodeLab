package eu.tutorials.composematerialdesignsamples.appmoviestmdb.view.detail


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.databinding.TmdbFragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.tmdb_fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var binding: TmdbFragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TmdbFragmentDetailsBinding.bind(view)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.posterImageUrl}").centerCrop().into(binding.imgMovie)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.backgroundImageUrl}").centerCrop().into(binding.imgBackground)
        binding.txtDescription.text = args.overview
        binding.txtMovieTitle.text = args.title
        binding.txtLanguage.text = "Language ${args.language}"
        binding.txtRating.text = "${args.voteAverage} (${args.voteCount} Reviews)"
        binding.txtReleased.text = "Released ${args.releaseDate}"
    }
}