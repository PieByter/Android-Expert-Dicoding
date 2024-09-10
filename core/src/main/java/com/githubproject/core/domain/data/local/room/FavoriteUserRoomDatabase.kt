package com.githubproject.core.domain.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.githubproject.core.BuildConfig
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import com.githubproject.core.domain.data.local.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = INSTANCE
                if (instance != null) {
                    instance
                } else {
                    val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.KEY.toCharArray())
                    val factory = SupportFactory(passphrase)

                    val newInstance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUserRoomDatabase::class.java,
                        "FavoriteUserDatabase.db"
                    )
                        .openHelperFactory(factory)
                        .build()

                    INSTANCE = newInstance
                    newInstance
                }
            }
        }
    }
}
