package com.gawel.babyjudas.featureBaby.domain.repositories

interface NsdService {

    fun registerService(port: Int)

    fun unregister()
}