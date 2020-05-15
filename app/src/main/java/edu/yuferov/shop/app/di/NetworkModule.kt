package edu.yuferov.shop.app.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import edu.yuferov.shop.data.repository.MainApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesGson(): Gson =
        GsonBuilder()
            .create()

    @Provides
    @Singleton
    fun providesRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://192.168.1.11:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun providesMainApi(retrofit: Retrofit): MainApi =
        retrofit.create(MainApi::class.java)
}