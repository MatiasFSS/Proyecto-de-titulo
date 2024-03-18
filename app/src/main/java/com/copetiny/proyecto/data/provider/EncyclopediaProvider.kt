package com.copetiny.proyecto.data.provider

import com.copetiny.proyecto.domain.model.EncyclopediaInfo
import com.copetiny.proyecto.domain.model.EncyclopediaInfo.*
import javax.inject.Inject

class EncyclopediaProvider @Inject constructor() {
    fun getEncyclopedia():List<EncyclopediaInfo>{
        return listOf(
            Papel,
            Aluminio,
            Carton,
            Vidrio,
            Lata,
            Madera,
            Bolsas,
            Chatarra,
            PET,
            Revistas,
            Aceite_Vegetal,
            Cables,
            Celulares,
            Pilas,
            Sanitarios,
            PlasticoN7

        )
    }
}