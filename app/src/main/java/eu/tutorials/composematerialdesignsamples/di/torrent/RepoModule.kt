package eu.tutorials.composematerialdesignsamples.di.torrent

import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.MainRepository
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.cache.CacheSourceImpl
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.cache.ICacheSource
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.network.INetworkSource
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.network.NetworkSourceImpl
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
            get()
        )
    }


}