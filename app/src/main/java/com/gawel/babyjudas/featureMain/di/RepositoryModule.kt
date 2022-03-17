package com.gawel.babyjudas.featureMain.di

import android.content.Context
import com.gawel.babyjudas.core.utils.Constants
import com.gawel.babyjudas.featureMain.data.datasource.WikiService
import com.gawel.babyjudas.featureMain.data.repositories.WikiRepositoryImpl
import com.gawel.babyjudas.featureMain.domain.repositories.WikiRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideWikiRepository(wikiRepository: WikiRepositoryImpl) : WikiRepository


}