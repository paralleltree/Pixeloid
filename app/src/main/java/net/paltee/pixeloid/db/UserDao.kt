package net.paltee.pixeloid.db

import androidx.lifecycle.LiveData
import androidx.room.*

import net.paltee.pixeloid.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE name = :name")
    fun getUserByName(name: String): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("DELETE FROM users")
    fun deleteAllUsers()
}
