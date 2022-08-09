package eu.tutorials.composematerialdesignsamples.di.torrent

import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.details.DetailsViewModel
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.explore.ExploreViewModel
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.favorites.FavoriteViewModel
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.rank.RankViewModel
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.stream.StreamViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ExploreViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { StreamViewModel(get(), get()) }
    viewModel { RankViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}