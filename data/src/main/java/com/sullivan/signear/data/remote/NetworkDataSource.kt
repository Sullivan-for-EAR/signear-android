package com.sullivan.signear.data.remote

import com.sullivan.common.core.DataState
import com.sullivan.signear.data.model.RankingInfo
import com.sullivan.signear.data.model.ResponseCheckEmail
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