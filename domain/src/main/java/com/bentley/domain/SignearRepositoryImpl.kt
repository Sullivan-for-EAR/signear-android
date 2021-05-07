package com.bentley.domain

import com.sullivan.signear.data.remote.NetworkDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SignearRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : SignearRepository {

}