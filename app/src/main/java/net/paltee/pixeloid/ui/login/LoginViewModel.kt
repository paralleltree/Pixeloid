package net.paltee.pixeloid.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import net.paltee.pixeloid.db.PreferenceDao
import net.paltee.pixeloid.model.User
import net.paltee.pixeloid.repository.GraphRepository
import net.paltee.pixeloid.repository.UserRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        private val userRepository: UserRepository,
        graphRepository: GraphRepository,
        private val preferenceDao: PreferenceDao
) : ViewModel() {
    private val user = MutableLiveData<User>()
    val loginResult = Transformations
            .switchMap(user) { user ->
                graphRepository.loadGraphs(user)
            }

    fun login(name: String, token: String) {
        user.postValue(User(name, token))
    }

    fun storeCredential() {
        val item = User(user.value!!.name, user.value!!.token)
        userRepository.insertUser(item)
        preferenceDao.currentUserName = item.name
    }
}
