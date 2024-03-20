package com.copetiny.proyecto.domain.model.encyclopedia

import com.copetiny.proyecto.R

sealed class EncyclopediaInfo(val img:Int, val name:Int){

    data object Papel: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Papel)
    data object Aluminio: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Aluminio)
    data object Carton: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Carton)
    data object Vidrio: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Vidrio)
    data object Madera: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Madera)
    data object Lata: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Lata)
    data object Bolsas: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Bolsa)
    data object Chatarra: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Chatarra)
    data object PET: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_PET)
    data object Revistas: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Revistas)
    data object Aceite_Vegetal: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Aceite_Vegetal)
    data object Cables: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Cables)
    data object Celulares: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Celulares)
    data object Pilas: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Pilas)
    data object Sanitarios: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Sanitarios)
    data object PlasticoN7: EncyclopediaInfo(R.drawable.aries, R.string.encyclopedia_Plastico_N7)

}