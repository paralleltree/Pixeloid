package net.paltee.pixeloid.api

import androidx.lifecycle.LiveData
import net.paltee.pixeloid.model.Graph
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface PixelaService {
    @GET("users/{username}/graphs")
    fun getGraphs(
            @Header("X-USER-TOKEN") token: String,
            @Path("username") username: String
    ): LiveData<ApiResponse<List<Graph>>>

    @PUT("users/{username}/graphs/{graphId}/increment")
    fun incrementPixel(
            @Header("X-USER-TOKEN") token: String,
            @Path("username") username: String,
            @Path("graphId") graphId: String
    ): LiveData<ApiResponse<QueryResponse>>

    @PUT("users/{username}/graphs/{graphId}/decrement")
    fun decrementPixel(
            @Header("X-USER-TOKEN") token: String,
            @Path("username") username: String,
            @Path("graphId") graphId: String
    ): LiveData<ApiResponse<QueryResponse>>

}
