package com.example.homeworkapp.db.dao

import androidx.room.*
import com.example.homeworkapp.db.entity.SettingsEntity

@Dao
interface SettingDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addSettingsUser(settings: SettingsEntity)

    @Update
    suspend fun updateUserSettings(settings: SettingsEntity)

    @Query("SELECT * FROM settings WHERE user_id = :id")
    suspend fun findById(id: Int): SettingsEntity?
}