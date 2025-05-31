package com.animeviewer.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimeDao {
    @Query("SELECT * FROM favorite_animes ORDER BY timestamp DESC")
    fun getAllFavorites(): Flow<List<FavoriteAnime>>

    @Query("SELECT * FROM favorite_animes WHERE id = :id")
    suspend fun getFavoriteById(id: String): FavoriteAnime?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteAnime)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteAnime)

    @Query("DELETE FROM favorite_animes WHERE id = :id")
    suspend fun deleteFavoriteById(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_animes WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
} 