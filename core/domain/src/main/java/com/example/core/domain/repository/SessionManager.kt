package com.example.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionManager {
    fun getProfileId(): Flow<String>
    fun getAuthToken(): Flow<String>
    fun getDeviceToken(): Flow<String>
    suspend fun setAuthToken(value: String)
    suspend fun setProfileId(value: String)
    suspend fun setProfileTime(value: Int)
    suspend fun setDeviceToken(value: String)
    suspend fun setTimeTimestampDeviceToken(value: Int)

}