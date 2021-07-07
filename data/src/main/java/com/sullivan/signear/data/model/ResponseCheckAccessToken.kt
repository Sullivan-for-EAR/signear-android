package com.sullivan.signear.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseCheckAccessToken(
    @SerializedName("isValidToken")
    val result: Boolean
)
