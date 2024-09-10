package com.githubproject.favorite.di

import android.content.Context
import com.githubproject.core.domain.di.FavoriteModuleDependencies
import com.githubproject.favorite.FavoriteUserActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {
    fun inject(activity: FavoriteUserActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun favoriteModuleDependencies(dependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}
