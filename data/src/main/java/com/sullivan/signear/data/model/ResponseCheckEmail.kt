package com.sullivan.signear.data.model

import com.google.gson.annotations.SerializedName

data class ResponseCheckEmail(
    @SerializedName("isNext")
    val result: Boolean
)
