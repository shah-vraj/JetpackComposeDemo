package com.example.onlinelearning.data.local.repository

import com.example.onlinelearning.data.local.dao.UsersDao
import com.example.onlinelearning.data.local.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val mUsersDao: UsersDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun addUser(userEntity: UserEntity) = withContext(ioDispatcher) {
        mUsersDao.addUser(userEntity) != NO_USER_ADDED_FLAG
    }

    suspend fun getUser(username: String) = withContext(ioDispatcher) {
        mUsersDao.getUser(username)
    }

    suspend fun getUserId(username: String) = withContext(ioDispatcher) {
        mUsersDao.getUser(username)?.id
    }

    suspend fun isUserExists(username: String) = withContext(ioDispatcher) {
        mUsersDao.getUser(username) != null
    }

    suspend fun getUserWithEmail(email: String) = withContext(ioDispatcher) {
        mUsersDao.getUserWithEmail(email)
    }

    suspend fun getUserWithId(id: Int) = withContext(ioDispatcher) {
        mUsersDao.getUserWithId(id)
    }

    suspend fun updateUser(userEntity: UserEntity) = withContext(ioDispatcher) {
        mUsersDao.updateUser(userEntity)
    }

    companion object {
        private const val NO_USER_ADDED_FLAG = -1L
    }
}