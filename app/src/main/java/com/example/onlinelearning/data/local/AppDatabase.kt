package com.example.onlinelearning.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlinelearning.data.local.dao.UsersDao
import com.example.onlinelearning.data.local.entity.UserEntity

/**
 * Central class for holding all the tables dao stored in application database
 */
@Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    /**
     * User data table dao
     */
    abstract fun usersDao(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Singleton instance
         */
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(Any()) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "online.learning.database"
                ).build()
            }
    }
}