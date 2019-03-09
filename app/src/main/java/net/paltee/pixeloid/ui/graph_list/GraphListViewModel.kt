package net.paltee.pixeloid.ui.graph_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import net.paltee.pixeloid.model.Graph
import net.paltee.pixeloid.model.Resource
import net.paltee.pixeloid.model.User
import net.paltee.pixeloid.repository.GraphRepository
import net.paltee.pixeloid.util.AbsentLiveData
import javax.inject.Inject

class GraphListViewModel @Inject constructor(graphRepository: GraphRepository) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    val graphs: LiveData<Resource<List<Graph>>> = Transformations
            .switchMap(_user) { user ->
                if (user == null) {
                    AbsentLiveData.create()
                } else {
                    graphRepository.loadGraphs(user)
                }
            }

    init {
        _user.value = User("paralleltree", "dummy")
    }
}
