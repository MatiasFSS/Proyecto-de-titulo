package com.copetiny.proyecto.data.network

import com.copetiny.proyecto.data.network.response.MaterialResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProyectoApiService {

    @GET("/enciclopedia/nombre_material/{nombre_material}")
    suspend fun getEncyclopedia(@Path("nombre_material") id:String): MaterialResponse

}