package com.sullivan.signear.data.model

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("userProfile")
    val userProfile: UserProfile
)

data class UserProfile(
    @SerializedName("customerID")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String
)
