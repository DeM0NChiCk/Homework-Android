package com.example.homeworkapp.db.dao

import androidx.room.*
import com.example.homeworkapp.db.entity.UserEntity
import com.example.homeworkapp.db.model.UpdateUserModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: UserEntity)

    @Update(entity = UserEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun updateUserLogin(newUser: UpdateUserModel)

    @Query("SElECT * FROM users WHERE id = :id")
    suspend fun findById(id: Int): UserEntity?

    @Query("SElECT * FROM users WHERE userLogin = :userLogin AND userPassword = :userPassword")
    suspend fun findByLoginAndPassword(userLogin: String, userPassword: String): UserEntity?
}