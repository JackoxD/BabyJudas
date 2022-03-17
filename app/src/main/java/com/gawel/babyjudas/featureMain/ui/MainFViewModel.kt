package com.gawel.babyjudas.featureMain.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gawel.babyjudas.core.models.State
import com.gawel.babyjudas.featureMain.domain.usecases.GetRandomSampleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFViewModel @Inject constructor(
        private val getRandomSampleUseCase: GetRandomSampleUseCase
): ViewModel() {

    private val _sampleData = MutableStateFlow<State<String>>(State.Empty())
    var sampleData : StateFlow<State<String>> = _sampleData

    init {
        getSample()
    }

    fun getSample() {
        viewModelScope.launch {
        getRandomSampleUseCase()
            .catch {
                e -> _sampleData.value = State.Error(e.localizedMessage ?: "An error occurred")
            }
            .collect {
                _sampleData.value = it
            }
        }
    }
}