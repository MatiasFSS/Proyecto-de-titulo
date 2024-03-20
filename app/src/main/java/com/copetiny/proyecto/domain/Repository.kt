package com.copetiny.proyecto.domain

import com.copetiny.proyecto.domain.model.encyclopedia.MaterialModel

interface Repository {
    suspend fun getEncyclopedia(id:String): MaterialModel?
}