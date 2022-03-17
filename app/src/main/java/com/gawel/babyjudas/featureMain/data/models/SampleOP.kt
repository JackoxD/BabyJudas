package com.gawel.babyjudas.featureMain.data.models

import com.gawel.babyjudas.core.utils.DomainMapper
import com.gawel.babyjudas.featureMain.domain.models.Sample
import com.google.gson.annotations.SerializedName

data class SampleOP(
    @SerializedName("id")
    val id: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
) : DomainMapper<Sample> {

    override fun mapToDomain(): Sample {
        return Sample(
            id, userId, title, body
        )
    }
}