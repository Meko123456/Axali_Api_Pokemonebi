package com.merabk.axaliapipokemonebi.di

import com.merabk.axaliapipokemonebi.data.service.PokemonApi
import com.merabk.axaliapipokemonebi.util.Constants.API_BASE_URL
import com.merabk.axaliapipokemonebi.util.Constants.BASE_URL_NAME
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named(BASE_URL_NAME)
    fun provideBaseUrl(): String = API_BASE_URL

    /* @OptIn(ExperimentalSerializationApi::class)
     @Provides
     @Singleton
     fun provideJson(): Json =
         Json {
             ignoreUnknownKeys = true
             encodeDefaults = true
             explicitNulls = false
         }*/
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRetrofitBuilder(
        @Named(BASE_URL_NAME) url: String,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)

    @Provides
    fun provideMoviesService(
        retrofit: Retrofit
    ): PokemonApi = retrofit.create()

    @Provides
    fun provideInterceptor(
    ): Interceptor =
        Interceptor { chain ->
            val original = chain.request()
            val httpUrl = original.url.newBuilder()
                .build()
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(httpUrl)
            chain.proceed(requestBuilder.build())
        }

    @Provides
    fun provideOkHttpClient(header: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                addInterceptor(header)
            }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        builder: Retrofit.Builder,
    ): Retrofit = builder
        .client(
            client.newBuilder()
                .build()
        ).build()

}