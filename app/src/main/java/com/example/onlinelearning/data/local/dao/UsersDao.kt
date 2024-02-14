package com.example.onlinelearning.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.onlinelearning.data.local.entity.UserEntity

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(userEntity: UserEntity): Long

    @Query("SELECT * FROM userData WHERE username = :username")
    fun getUser(username: String): UserEntity?

    @Query("SELECT * FROM userData WHERE email = :email")
    fun getUserWithEmail(email: String): UserEntity?

    @Query("SELECT * FROM userData WHERE id = :id")
    fun getUserWithId(id: Int): UserEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(userEntity: UserEntity)
}