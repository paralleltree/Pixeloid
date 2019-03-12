package net.paltee.pixeloid.ui.user_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import net.paltee.pixeloid.model.User
import net.paltee.pixeloid.repository.UserRepository
import javax.inject.Inject

class UserListViewModel @Inject constructor(
        userRepository: UserRepository
) : ViewModel() {
    private val trigger = MutableLiveData<Int>()

    val users: LiveData<List<User>> = Transformations.switchMap(trigger) { userRepository.loadUsers() }

    init {
        reload()
    }

    fun reload() {
        trigger.postValue(0)
    }
}
