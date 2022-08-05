package eu.tutorials.composematerialdesignsamples.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.tutorials.composematerialdesignsamples.appmovies.domain.UiStatePlaygroundDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): UiStatePlaygroundDatabase = Room.databaseBuilder(
        context,
        UiStatePlaygroundDatabase::class.java,
        "ui-state-playground-database"
    ).build()
}
