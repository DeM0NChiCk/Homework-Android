package com.example.homeworkapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homeworkapp.db.dao.SettingDao
import com.example.homeworkapp.db.dao.UserDao
import com.example.homeworkapp.db.entity.SettingsEntity
import com.example.homeworkapp.db.entity.UserEntity

@Database(
    entities = [UserEntity::class, SettingsEntity::class],
    version = DatabaseHandler.databaseVersion
)
abstract class InceptionDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getSettingsDao(): SettingDao
}