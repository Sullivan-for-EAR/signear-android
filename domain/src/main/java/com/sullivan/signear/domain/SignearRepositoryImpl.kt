package com.sullivan.signear.domain

import com.sullivan.common.core.DataState
import com.sullivan.signear.data.model.*
import com.sullivan.signear.data.remote.NetworkDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@ViewModelScoped
@ExperimentalCoroutinesApi
class SignearRepositoryImpl
@Inject constructor(
    private val networkDataSource: NetworkDataSource
) : SignearRepository {
    override suspend fun fetchRankInfo(): Flow<DataState<RankingInfo>> =
        callbackFlow {
            networkDataSource.fetchRankInfo().collect {
                when (it) {
                    is DataState.Success -> {
                        offer(it)
                    }
                    is DataState.Error -> {
                        Timber.e("DataState.Error")
                    }
                }
            }
        }

    override suspend fun checkEmail(email: String): Flow<ResponseCheckEmail> =
        callbackFlow {
            networkDataSource.checkEmail(email).collect {
                when (it) {
                    is DataState.Success -> {
                        offer(it.data)
                    }
                    is DataState.Error -> {
                        Timber.e("DataState.Error")
                    }
                }
            }
        }

    override suspend fun login(email: String, password: String): Flow<ResponseLogin> =
        callbackFlow {
            networkDataSource.login(email, password).collect {
                when (it) {
                    is DataState.Success -> {
                        offer(it.data)
                    }
                    is DataState.Error -> {
                        Timber.e("DataState.Error")
                    }
                }
            }
        }

    override suspend fun checkAccessToken(): Flow<ResponseCheckAccessToken> =
        callbackFlow {
            networkDataSource.checkAccessToken().collect {
                when (it) {
                    is DataState.Success -> {
                        offer(it.data)
                    }
                    is DataState.Error -> {
                        Timber.e("DataState.Error")
                    }
                }
            }
        }

    override suspend fun createUser(
        email: String,
        password: String,
        phone: String
    ): Flow<ResponseLogin> =
        callbackFlow {
            networkDataSource.createUser(email, password, phone).collect {
                when (it) {
                    is DataState.Success -> {
                        offer(it.data)
                    }
                    is DataState.Error -> {
                        Timber.e("DataState.Error")
                    }
                }
            }
        }

    override suspend fun getUserInfo(id: Int): Flow<UserProfile> =
        callbackFlow {
            networkDataSource.getUserInfo(id).collect {
                when (it) {
                    is DataState.Success -> {
                        offer(it.data)
                    }
                    is DataState.Error -> {
                        Timber.e("DataState.Error")
                    }
                }
            }
        }
}