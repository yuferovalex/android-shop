package edu.yuferov.shop.app.di

import dagger.Module
import dagger.Provides
import edu.yuferov.shop.app.presenter.MainPresenter
import edu.yuferov.shop.data.repository.CartRepository
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun providesMainPresenter(repository: CartRepository): MainPresenter =
        MainPresenter(repository)
}