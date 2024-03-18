package com.copetiny.proyecto.domain

import com.copetiny.proyecto.data.network.response.MaterialResponse
import com.copetiny.proyecto.domain.model.MaterialModel

interface Repository {
    suspend fun getEncyclopedia(id:String):MaterialModel?
}