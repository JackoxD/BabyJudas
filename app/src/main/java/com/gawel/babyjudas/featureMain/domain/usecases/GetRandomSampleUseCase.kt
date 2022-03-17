package com.gawel.babyjudas.featureMain.domain.usecases

import com.gawel.babyjudas.core.models.Result
import com.gawel.babyjudas.core.models.State
import com.gawel.babyjudas.core.utils.DispatcherProvider
import com.gawel.babyjudas.featureMain.domain.repositories.WikiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRandomSampleUseCase @Inject constructor(
    private val repository: WikiRepository,
    private val dispatcher: DispatcherProvider
) {

    operator fun invoke() : Flow<State<String>> {
        return flow {
            emit(State.Loading())
            when (val result = repository.getDataAboutKids()) {
                is Result.Error -> emit(State.Error(result.message!!))
                is Result.Success -> emit(State.Success(result.data!!.title))
            }
        }
            .flowOn(dispatcher.main)
    }
}