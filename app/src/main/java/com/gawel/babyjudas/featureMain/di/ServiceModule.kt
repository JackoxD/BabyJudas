package com.gawel.babyjudas.featureMain.di

import android.content.Context
import com.gawel.babyjudas.core.utils.Constants
import com.gawel.babyjudas.featureMain.data.datasource.WikiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {


    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context) : Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWikiService(retrofit: Retrofit) : WikiService {
        return retrofit.create(WikiService::class.java)
    }

}