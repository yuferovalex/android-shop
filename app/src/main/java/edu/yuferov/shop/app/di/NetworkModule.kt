package edu.yuferov.shop.app.di

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import edu.yuferov.shop.data.mappers.*
import edu.yuferov.shop.data.repository.MainApi
import edu.yuferov.shop.domain.Percent
import edu.yuferov.shop.domain.Price
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesGson(): Gson =
        GsonBuilder()
            .registerTypeAdapter(Percent::class.java, PercentDeserializer())
            .registerTypeAdapter(Percent::class.java, PercentSerializer())
            .registerTypeAdapter(Uri::class.java, UriDeserializer())
            .registerTypeAdapter(Uri::class.java, UriSerializer())
            .registerTypeAdapter(Price::class.java, PriceDeserializer())
            .registerTypeAdapter(Price::class.java, PriceSerializer())
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