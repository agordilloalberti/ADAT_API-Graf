package ADAT_API_GRAF.externalApi

data class DatosMunicipios(
    val update_date: String,
    val size: Int,
    val data: List<Municipio>?,
)