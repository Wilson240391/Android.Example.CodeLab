package eu.tutorials.composematerialdesignsamples.di.torrent

import eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain.MainRepository
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain.cache.CacheSourceImpl
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain.cache.ICacheSource
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain.network.INetworkSource
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain.network.NetworkSourceImpl
import org.koin.dsl.module

val repoModule = module {

    single {
        MainRepository(
            get(),
            get()
        )
    }

    factory<INetworkSource> {
        NetworkSourceImpl(
            get()
        )
    }

    factory<ICacheSource> {
        CacheSourceImpl(
            get(),
            get(),
            get()
        )
    }


}