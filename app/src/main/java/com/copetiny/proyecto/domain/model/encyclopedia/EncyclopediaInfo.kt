package com.copetiny.proyecto.domain.model.encyclopedia

import com.copetiny.proyecto.R

sealed class EncyclopediaInfo(val img: Int, val name: Int, val isReciclable: Boolean, val isEspecial: Boolean) {

    object Papel : EncyclopediaInfo(R.drawable.papel, R.string.encyclopedia_Papel, isReciclable = true, isEspecial = false)
    object Aluminio : EncyclopediaInfo(R.drawable.aluminio, R.string.encyclopedia_Aluminio, isReciclable = true, isEspecial = false)
    object Carton : EncyclopediaInfo(R.drawable.carton, R.string.encyclopedia_Carton, isReciclable = true, isEspecial = false)
    object Vidrio : EncyclopediaInfo(R.drawable.vidrio, R.string.encyclopedia_Vidrio, isReciclable = true, isEspecial = false)
    object Madera : EncyclopediaInfo(R.drawable.madera, R.string.encyclopedia_Madera, isReciclable = true, isEspecial = false)
    object Lata : EncyclopediaInfo(R.drawable.lata, R.string.encyclopedia_Lata, isReciclable = true, isEspecial = false)
    object Bolsas : EncyclopediaInfo(R.drawable.bolsa, R.string.encyclopedia_Bolsa, isReciclable = true, isEspecial = false)
    object Chatarra : EncyclopediaInfo(R.drawable.chatarra, R.string.encyclopedia_Chatarra, isReciclable = true, isEspecial = false)
    object PET : EncyclopediaInfo(R.drawable.pet, R.string.encyclopedia_PET, isReciclable = true, isEspecial = false)
    object Revistas : EncyclopediaInfo(R.drawable.revistas, R.string.encyclopedia_Revistas, isReciclable = true, isEspecial = false)
    object Aceite_Vegetal : EncyclopediaInfo(R.drawable.aceite, R.string.encyclopedia_Aceite_Vegetal, isReciclable = false, isEspecial = true)
    object Cables : EncyclopediaInfo(R.drawable.cables, R.string.encyclopedia_Cables, isReciclable = false, isEspecial = true)
    object Celulares : EncyclopediaInfo(R.drawable.celulares, R.string.encyclopedia_Celulares, isReciclable = false, isEspecial = true)
    object Pilas : EncyclopediaInfo(R.drawable.pilas, R.string.encyclopedia_Pilas, isReciclable = false, isEspecial = true)
    object Sanitarios : EncyclopediaInfo(R.drawable.sanitario, R.string.encyclopedia_Sanitarios, isReciclable = false, isEspecial = false)
    object PlasticoN7 : EncyclopediaInfo(R.drawable.plasticon7, R.string.encyclopedia_Plastico_N7, isReciclable = false, isEspecial = false)
}