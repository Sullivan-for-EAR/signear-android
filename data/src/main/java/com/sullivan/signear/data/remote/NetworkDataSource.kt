package com.sullivan.signear.data.remote

import com.sullivan.common.core.DataState
import com.sullivan.signear.data.model.*
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

    suspend fun createUser(
        email: String,
        password: String,
        phone: String
    ): Flow<DataState<ResponseLogin>> =
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

    suspend fun getUserInfo(id: Int): Flow<DataState<UserProfile>> =
        callbackFlow {
            offer(
                DataState.Success(
                    apiService.getUserInfo(id)
                )
            )
            awaitClose { close() }
        }

    suspend fun getReservationList(id: Int): Flow<DataState<List<ReservationData>>> =
        callbackFlow {
            offer(
                DataState.Success(
                    apiService.getReservationList(id)
                )
            )
            awaitClose { close() }
        }

    suspend fun applyReservation(newReservation: NewReservationRequest): Flow<DataState<NewReservation>> =
        callbackFlow {
            offer(
                DataState.Success(
                    apiService.createReservation(
                        hashMapOf(
                            "date" to newReservation.date,
                            "start_time" to newReservation.startTime,
                            "end_time" to newReservation.endTime,
                            "area" to newReservation.center,
                            "address" to newReservation.place,
                            "method" to newReservation.method,
                            "type" to newReservation.type,
                            "request" to newReservation.request,
                            "customerUser" to newReservation.userInfo
                        )
                    )
                )
            )
            awaitClose { close() }
        }

    suspend fun getReservationDetailInfo(id: Int): Flow<DataState<ReservationDetailInfo>> =
        callbackFlow {
            offer(
                DataState.Success(
                    apiService.getReservationDetailInfo(id)
                )
            )
            awaitClose { close() }
        }
}