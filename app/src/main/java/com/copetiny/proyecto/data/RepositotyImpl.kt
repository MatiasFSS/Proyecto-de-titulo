package com.copetiny.proyecto.data

import android.util.Log
import com.copetiny.proyecto.data.network.ProyectoApiService
import com.copetiny.proyecto.domain.Repository
import com.copetiny.proyecto.domain.model.MaterialModel
import javax.inject.Inject

class RepositotyImpl @Inject constructor(private val apiService: ProyectoApiService) :Repository {
    override suspend fun getEncyclopedia(id:String):MaterialModel?
    {
        runCatching { apiService.getEncyclopedia(id) }
            .onSuccess { return  it.toDomain()}
            .onFailure { Log.i("Matias", "Ha ocurrido un error ${it.message}") }
        return null
    }
}