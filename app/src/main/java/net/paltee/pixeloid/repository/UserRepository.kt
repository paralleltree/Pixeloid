package net.paltee.pixeloid.repository

import androidx.lifecycle.LiveData
import net.paltee.pixeloid.AppExecutors
import net.paltee.pixeloid.api.ApiResponse
import net.paltee.pixeloid.db.UserDao
import net.paltee.pixeloid.model.Resource
import net.paltee.pixeloid.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val userDao: UserDao
) {
    fun loadUser(name: String): LiveData<User> = userDao.getUserByName(name)

    fun insertUser(user: User) = appExecutors.diskIO().execute {
        userDao.insertUser(user)
    }
}
