package com.sullivan.signear.data.model

import com.google.gson.annotations.SerializedName

data class RankingInfo(
    @SerializedName("location")
    val location: String,
    @SerializedName("my_profile_image")
    val profileImg: String,
    @SerializedName("my_rank")
    val myRank: Int,
    @SerializedName("percent")
    val percent: String,
    @SerializedName("rank_list")
    val totalRank: List<Rank>,
)

data class Rank(
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("profile_image")
    val profileImg: String,
    @SerializedName("nick_name")
    val nickName: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("is_me")
    val isMe: Boolean,
    @SerializedName("diff_rank")
    val diffRank: Int,
)
