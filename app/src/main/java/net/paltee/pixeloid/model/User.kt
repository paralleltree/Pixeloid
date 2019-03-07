package net.paltee.pixeloid.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(@PrimaryKey
                @ColumnInfo(name = "name")
                val name: String,
                @ColumnInfo(name = "token")
                val token: String)
