package net.paltee.pixeloid.ui.edit_graph

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import net.paltee.pixeloid.api.QueryResponse
import net.paltee.pixeloid.db.PreferenceDao
import net.paltee.pixeloid.model.Graph
import net.paltee.pixeloid.model.Resource
import net.paltee.pixeloid.repository.GraphRepository
import net.paltee.pixeloid.repository.UserRepository
import javax.inject.Inject

class EditGraphViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val graphRepository: GraphRepository,
        private val preferenceDao: PreferenceDao
) : ViewModel() {
    private val username = MutableLiveData<String>()
    private val user = Transformations
            .switchMap(username) { name ->
                userRepository.loadUser(name)
            }

    private val query = MutableLiveData<Query>()
    val response: LiveData<Resource<QueryResponse>> = Transformations
            .switchMap(query) { q ->
                if (query.value!!.isNew) {
                    graphRepository.createGraph(user.value!!, q.graph)
                }
                throw NotImplementedError()
            }

    init {
        username.value = preferenceDao.currentUserName
    }

    fun send(isNew: Boolean, graph: Graph) {
        query.postValue(Query(isNew, graph))
    }

    data class Query(val isNew: Boolean, val graph: Graph)
}
