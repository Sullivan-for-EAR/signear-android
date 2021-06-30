package com.sullivan.signear.data.remote

import com.sullivan.common.core.DataState
import com.sullivan.signear.data.model.RankingInfo
import com.sullivan.signear.data.model.ResponseCheckAccessToken
import com.sullivan.signear.data.model.ResponseCheckEmail
import com.sullivan.signear.data.model.ResponseLogin
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NetworkDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun checkEmail(email: String): Flow<DataState<ResponseCheckEmail>> =
        callbackFlow {
            offer(DataState.Success(apiService.checkEmail(email)))
            awaitClose { close() }
        }

    suspend fun login(email: String, password: String): Flow<DataState<ResponseLogin>> =
        callbackFlow {
            offer(
                DataState.Success(
                    apiService.login(
                        hashMapOf(
                            "email" to email,
                            "password" to password
                        )
                    )
                )
            )
            awaitClose { close() }
        }

    suspend fun checkAccessToken(): Flow<DataState<ResponseCheckAccessToken>> =
        callbackFlow {
            offer(DataState.Success(apiService.checkAccessToken()))
            awaitClose { close() }
        }

    suspend fun createUser(email: String, password: String, phone: String): Flow<DataState<ResponseLogin>> =
        callbackFlow {
            offer(
                DataState.Success(
                    apiService.createUser(
                        hashMapOf(
                            "email" to email,
                            "password" to password,
                            "phone" to phone
                        )
                    )
                )
            )
            awaitClose { close() }
        }

    suspend fun fetchRankInfo(): Flow<DataState<RankingInfo>> =
        callbackFlow {

//            emit(DataState.Loading)
//            delay(1000)
//            try {
//                val resultList = apiService.fetchRankInfo()
//                emit(DataState.Success(resultList))
//            } catch (e: Exception) {
//                emit(DataState.Error(e))
//            }

            offer(DataState.Success(apiService.fetchRankInfo()))
            awaitClose { close() }
        }
}