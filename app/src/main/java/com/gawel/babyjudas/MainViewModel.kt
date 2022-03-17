package com.gawel.babyjudas

import androidx.lifecycle.ViewModel
import com.gawel.babyjudas.featureBaby.domain.repositories.NsdService
import com.gawel.babyjudas.featureParent.domain.repositories.NsdDiscover
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val nsdService: NsdService,
    private val nsdDiscover: NsdDiscover
): ViewModel() {

    init {
//        nsdService.registerService(4009)
        nsdDiscover.discoverService()
    }

    override fun onCleared() {
        super.onCleared()
        nsdService.unregister()
        nsdDiscover.stopDiscover()
    }
}