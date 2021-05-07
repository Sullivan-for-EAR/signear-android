package com.sullivan.signear.data.remote

import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun requestLogin() {

    }
}