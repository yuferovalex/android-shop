package edu.yuferov.shop.app.di

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import edu.yuferov.shop.data.repository.CartRepository
import edu.yuferov.shop.data.repository.MainApi
import edu.yuferov.shop.data.repository.MainRepositoryImpl
import edu.yuferov.shop.data.repository.SharedPreferencesCartRepository

@Module
class RepositoryModule {


    @Provides
    fun repository(): MainApi = MainRepositoryImpl()

    @Provides
    fun cartRepository(context: Context, api: MainApi): CartRepository =
        SharedPreferencesCartRepository(PreferenceManager.getDefaultSharedPreferences(context), api)
}