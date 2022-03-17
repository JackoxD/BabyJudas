package com.gawel.babyjudas.featureMain.domain.repositories

import com.gawel.babyjudas.core.models.Result
import com.gawel.babyjudas.featureMain.domain.models.Sample

interface WikiRepository {

    suspend fun getDataAboutKids(): Result<Sample>
}