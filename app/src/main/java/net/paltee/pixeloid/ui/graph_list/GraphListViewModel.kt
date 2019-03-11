package net.paltee.pixeloid.ui.graph_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import net.paltee.pixeloid.api.QueryResponse
import net.paltee.pixeloid.db.PreferenceDao
import net.paltee.pixeloid.model.Graph
import net.paltee.pixeloid.model.Resource
import net.paltee.pixeloid.model.User
import net.paltee.pixeloid.repository.GraphRepository
import net.paltee.pixeloid.repository.UserRepository
import net.paltee.pixeloid.util.AbsentLiveData
import javax.inject.Inject

class GraphListViewModel @Inject constructor(
        userRepository: UserRepository,
        graphRepository: GraphRepository,
        preferenceDao: PreferenceDao
) : ViewModel() {
    private val username = MutableLiveData<String>()
    val user: LiveData<User> = Transformations
            .switchMap(username) { name ->
                userRepository.loadUser(name)
            }

    val graphs: LiveData<Resource<List<Graph>>> = Transformations
            .switchMap(user) { user ->
                if (user == null) {
                    AbsentLiveData.create()
                } else {
                    graphRepository.loadGraphs(user)
                }
            }

    private val pixelQuery = MutableLiveData<Graph>()
    val pixelResponse: LiveData<Resource<QueryResponse>> = Transformations
            .switchMap(pixelQuery) { graph ->
                graphRepository.incrementPixel(user.value!!, graph.id)
            }

    init {
        username.postValue(preferenceDao.currentUserName)
    }

    fun incrementGraph(graph: Graph) {
        pixelQuery.postValue(graph)
    }

    fun retry() {
        username.postValue(user.value?.name)
    }
}
