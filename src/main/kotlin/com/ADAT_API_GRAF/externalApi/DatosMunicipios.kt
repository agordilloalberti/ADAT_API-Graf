package com.AGS.ADAT_API.Graf.externalApi

data class DatosMunicipios(
    val update_date: String,
    val size: Int,
    val data: List<Provincia>?,
)