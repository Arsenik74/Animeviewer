package com.animeviewer.di

import android.content.Context
import androidx.room.Room
import com.animeviewer.data.local.AppDatabase
import com.animeviewer.data.local.FavoriteAnimeDao
import com.animeviewer.parser.AnimeParser
import com.animeviewer.parser.AnimeSamaParser
import com.animeviewer.parser.FranimeParser
import com.animeviewer.parser.VoirAnimeParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "anime_viewer.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteAnimeDao(database: AppDatabase): FavoriteAnimeDao {
        return database.favoriteAnimeDao()
    }

    @Provides
    @Singleton
    fun provideAnimeSamaParser(): AnimeParser {
        return AnimeSamaParser()
    }

    @Provides
    @Singleton
    fun provideVoirAnimeParser(): AnimeParser {
        return VoirAnimeParser()
    }

    @Provides
    @Singleton
    fun provideFranimeParser(): AnimeParser {
        return FranimeParser()
    }
} 