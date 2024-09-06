package com.githubproject.core.domain.data.local.room

import androidx.room.*
import com.githubproject.core.domain.data.local.entity.FavoriteUser
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): Flow<FavoriteUser?>

    @Query("SELECT * FROM FavoriteUser ORDER BY username ASC")
    fun getAllUsers(): Flow<List<FavoriteUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteUser: FavoriteUser)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(favoriteUsers: List<FavoriteUser>)

    @Delete
    suspend fun delete(favoriteUser: FavoriteUser)

    @Query("DELETE FROM FavoriteUser WHERE username = :username")
    suspend fun deleteByUsername(username: String)

    @Query("SELECT * FROM FavoriteUser ORDER BY username ASC")
    fun getAllFavoriteUsers(): Flow<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE isFavorite = 1 ORDER BY username ASC")
    fun getFavoriteUsers(): Flow<List<FavoriteUser>>
}
