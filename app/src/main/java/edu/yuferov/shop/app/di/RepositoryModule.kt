package edu.yuferov.shop.app.di

import dagger.Module
import dagger.Provides
import edu.yuferov.shop.data.repository.IMainRepository
import edu.yuferov.shop.data.repository.MainRepositoryImpl

@Module
class RepositoryModule {
    @Provides
    fun repository(): IMainRepository = MainRepositoryImpl()
}