package com.gawel.babyjudas.featureBaby.di

import com.gawel.babyjudas.featureBaby.data.repositories.NsdServiceImpl
import com.gawel.babyjudas.featureBaby.domain.repositories.NsdService
import com.gawel.babyjudas.featureParent.data.repositories.NsdDiscoverImpl
import com.gawel.babyjudas.featureParent.domain.repositories.NsdDiscover
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun provideNsdService(nsdService: NsdServiceImpl) : NsdService

    @Binds
    abstract fun provideNsdDiscover(ndsDiscover: NsdDiscoverImpl) : NsdDiscover

}