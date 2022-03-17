package com.gawel.babyjudas.core.utils

interface DomainMapper<DOMAIN> {

    fun mapToDomain() : DOMAIN
}