package com.sullivan.signear.domain

import com.sullivan.common.core.DataState
import com.sullivan.signear.data.model.*
import kotlinx.coroutines.flow.Flow

interface SignearRepository {

    suspend fun checkEmail(email: String): Flow<ResponseCheckEmail>

    suspend fun login(email: String, password: String): Flow<ResponseLogin>

    suspend fun checkAccessToken(): Flow<ResponseCheckAccessToken>

    suspend fun createUser(email: String, password: String, phone: String): Flow<ResponseLogin>

    suspend fun getUserInfo(id: Int): Flow<UserProfile>

    suspend fun getReservationList(id: Int): Flow<List<ReservationData>>

    suspend fun createNewReservation(newReservation: NewReservationRequest): Flow<NewReservation>

    suspend fun getReservationDetailInfo(id: Int): Flow<ReservationDetailInfo>

    suspend fun cancelReservation(id: Int): Flow<ReservationDetailInfo>

    suspend fun createEmergencyReservation(newEmergencyReservationRequest: NewEmergencyReservationRequest): Flow<NewReservation>

    suspend fun cancelEmergencyReservation(id: Int): Flow<ReservationDetailInfo>

    suspend fun getPrevReservationList(id: Int): Flow<List<ReservationData>>
}