package net.paltee.pixeloid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import net.paltee.pixeloid.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class PixelaDb : RoomDatabase() {
    abstract fun userDao(): UserDao
}
