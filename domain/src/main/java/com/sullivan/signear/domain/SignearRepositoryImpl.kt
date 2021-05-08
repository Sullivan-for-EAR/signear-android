package com.sullivan.signear.domain

import com.sullivan.signear.core.DataState
import com.sullivan.signear.data.model.RankingInfo
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
                when(it) {
                    is DataState.Success -> {
                        offer(it)
                    }
                    is DataState.Error -> {
                        Timber.e("DataState.Error")
                    }
                }
            }
        }
}