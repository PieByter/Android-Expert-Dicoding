package com.githubproject.core.domain.di

import android.content.Context
import com.githubproject.core.domain.datastore.SettingPreferences
import com.githubproject.core.domain.data.local.room.FavoriteUserDao
import com.githubproject.core.domain.data.local.room.FavoriteUserRoomDatabase
import com.githubproject.core.domain.data.remote.retrofit.ApiConfig
import com.githubproject.core.domain.data.remote.retrofit.ApiService
import com.githubproject.core.domain.datastore.dataStore
import com.githubproject.core.domain.repository.UserRepositoryImpl
import com.githubproject.core.domain.repository.DetailRepository
import com.githubproject.core.domain.repository.FavoriteUserRepository
import com.githubproject.core.domain.utils.AppExecutors
import com.githubproject.core.domain.repository.interfaces.DetailUserRepository
import com.githubproject.core.domain.usecase.SearchUsersUseCase
import com.githubproject.core.domain.repository.interfaces.UserRepository
import com.githubproject.core.domain.repository.interfaces.IGithubRepository
import com.githubproject.core.domain.usecase.GithubInteractor
import com.githubproject.core.domain.usecase.GithubUseCase
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
    fun provideApiService(): ApiService {
        return ApiConfig.getApiService()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FavoriteUserRoomDatabase {
        return FavoriteUserRoomDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFavoriteUserDao(database: FavoriteUserRoomDatabase): FavoriteUserDao {
        return database.favoriteUserDao()
    }

    @Provides
    @Singleton
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors()
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSearchUserUseCase(userRepository: UserRepository): SearchUsersUseCase {
        return SearchUsersUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideSettingPreferences(@ApplicationContext context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideIGithubRepository(
        favoriteUserDao: FavoriteUserDao,
    ): IGithubRepository {
        return FavoriteUserRepository(favoriteUserDao)
    }

    @Provides
    @Singleton
    fun provideGithubUseCase(repository: IGithubRepository): GithubUseCase {
        return GithubInteractor(repository)
    }

    @Provides
    @Singleton
    fun bindDetailUserRepository(
        detailRepository: DetailRepository,
    ): DetailUserRepository {
        return detailRepository
    }
}
