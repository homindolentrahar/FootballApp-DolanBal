package com.dumb.projects.kadefinalsubmission.model

import com.google.gson.annotations.SerializedName

data class ClassementResponse(
    @SerializedName("table")
    val table: List<Classement>
)

data class Classement(
    var rank: Int,
    var teamBadge: String,
    @SerializedName("name")
    val teamName: String,
    @SerializedName("teamid")
    val teamId: String,
    @SerializedName("played")
    val played: Int,
    @SerializedName("goalsfor")
    val goalsFor: Int,
    @SerializedName("goalsagainst")
    val goalsAgainst: Int,
    @SerializedName("win")
    val win: Int,
    @SerializedName("draw")
    val draw: Int,
    @SerializedName("loss")
    val loss: Int,
    @SerializedName("total")
    val total: Int
)