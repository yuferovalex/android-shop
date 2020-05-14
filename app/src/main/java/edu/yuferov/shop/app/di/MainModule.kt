package edu.yuferov.shop.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule(private val context: Context) {
    @Provides
    @Singleton
    fun providesContext() = context
}