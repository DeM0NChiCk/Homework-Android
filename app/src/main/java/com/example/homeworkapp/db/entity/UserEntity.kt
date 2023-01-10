package com.example.homeworkapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// error: Invalid query argument: int. It must be a class or an interface.
//public final class UserEntity { -- ???
@Entity(tableName = "users",
    indices = [
        Index("userLogin", unique = true)
    ])
data class UserEntity(
    var userLogin: String,
    var userPassword: String,
    var userName: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
)
