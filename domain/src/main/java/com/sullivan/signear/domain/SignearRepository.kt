package com.sullivan.signear.domain

import com.sullivan.common.core.DataState
import com.sullivan.signear.data.model.*
import kotlinx.coroutines.flow.Flow

interface SignearRepository {
    suspend fun fetchRankInfo(): Flow<DataState<RankingInfo>>

    suspend fun checkEmail(email: String): Flow<ResponseCheckEmail>

    suspend fun login(email: String, password: String): Flow<ResponseLogin>

    suspend fun checkAccessToken(): Flow<ResponseCheckAccessToken>

    suspend fun createUser(email: String, password: String, phone: String): Flow<ResponseLogin>

    suspend fun getUserInfo(id: Int): Flow<UserProfile>
}