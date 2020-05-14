package edu.yuferov.shop.app.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import edu.yuferov.shop.data.repository.*

@Module
class RepositoryModule {

    @Provides
    fun providesSharedPreferences(context: Context) =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    fun providesCartRepository(sharedPreferences: SharedPreferences, api: MainApi): CartRepository =
        SharedPreferencesCartRepository(sharedPreferences, api)

    @Provides
    fun providesUserInfoRepository(sharedPreferences: SharedPreferences): UserInfoRepository =
        SharedPreferencesUserInfoRepository(sharedPreferences)

}