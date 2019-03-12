package net.paltee.pixeloid.api

import androidx.lifecycle.LiveData
import net.paltee.pixeloid.model.Graph
import retrofit2.http.*

interface PixelaService {
    @GET("users/{username}/graphs")
    fun getGraphs(
            @Header("X-USER-TOKEN") token: String,
            @Path("username") username: String
    ): LiveData<ApiResponse<List<Graph>>>

    @POST("users/{username}/graphs")
    fun createGraph(
            @Header("X-USER-TOKEN") token: String,
            @Path("username") username: String,
            @Body graph: Graph
    ): LiveData<ApiResponse<QueryResponse>>

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
