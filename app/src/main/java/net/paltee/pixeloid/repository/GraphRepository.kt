package net.paltee.pixeloid.repository

import androidx.lifecycle.LiveData
import net.paltee.pixeloid.AppExecutors
import net.paltee.pixeloid.api.ApiResponse
import net.paltee.pixeloid.api.PixelaService
import net.paltee.pixeloid.api.QueryResponse
import net.paltee.pixeloid.model.Graph
import net.paltee.pixeloid.model.Resource
import net.paltee.pixeloid.model.User
import net.paltee.pixeloid.util.AbsentLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GraphRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val pixelaService: PixelaService
) {
    fun loadGraphs(user: User): LiveData<Resource<List<Graph>>> {
        return object : NetworkBoundResource<List<Graph>>(appExecutors) {
            override fun createCall() = pixelaService.getGraphs(user.token, user.name)
            override fun loadFromDb(): LiveData<List<Graph>> = AbsentLiveData.create()
            override fun saveCallResult(item: List<Graph>) = Unit
            override fun shouldFetch(data: List<Graph>?) = true
        }.asLiveData()
    }

    fun createGraph(user: User, graph: Graph): LiveData<Resource<QueryResponse>> {
        return object : NetworkBoundResource<QueryResponse>(appExecutors) {
            override fun createCall() = pixelaService.createGraph(user.token, user.name, graph)
            override fun loadFromDb(): LiveData<QueryResponse> = AbsentLiveData.create()
            override fun saveCallResult(item: QueryResponse) = Unit
            override fun shouldFetch(data: QueryResponse?) = true
        }.asLiveData()
    }

    fun incrementPixel(user: User, graphId: String): LiveData<Resource<QueryResponse>> {
        return object : NetworkBoundResource<QueryResponse>(appExecutors) {
            override fun createCall() = pixelaService.incrementPixel(user.token, user.name, graphId)
            override fun loadFromDb(): LiveData<QueryResponse> = AbsentLiveData.create()
            override fun saveCallResult(item: QueryResponse) = Unit
            override fun shouldFetch(data: QueryResponse?) = true
        }.asLiveData()
    }

    fun decrementPixel(user: User, graphId: String): LiveData<Resource<QueryResponse>> {
        return object : NetworkBoundResource<QueryResponse>(appExecutors) {
            override fun createCall() = pixelaService.decrementPixel(user.token, user.name, graphId)
            override fun loadFromDb(): LiveData<QueryResponse> = AbsentLiveData.create()
            override fun saveCallResult(item: QueryResponse) = Unit
            override fun shouldFetch(data: QueryResponse?) = true
        }.asLiveData()
    }
}
