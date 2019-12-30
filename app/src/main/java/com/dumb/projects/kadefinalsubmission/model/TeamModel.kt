package com.dumb.projects.kadefinalsubmission.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TeamResponse(
    @SerializedName("teams")
    val teams: List<Team>
)

@Parcelize
data class Team(
    @SerializedName("idTeam")
    val idTeam: String,
    @SerializedName("strTeam")
    val teamName: String,
    @SerializedName("intFormedYear")
    val teamFormed: String,
    @SerializedName("strSport")
    val typeSport: String,
    @SerializedName("strLeague")
    val leagueName: String,
    @SerializedName("strStadium")
    val teamStadium: String,
    @SerializedName("strStadiumThumb")
    val stadiumThumbnail: String,
    @SerializedName("strStadiumDescription")
    val stadiumDescription: String,
    @SerializedName("strStadiumLocation")
    val stadiumLocation: String,
    @SerializedName("intStadiumCapacity")
    val stadiumCapacity: String,
    @SerializedName("strWebsite")
    val teamWebsite: String,
    @SerializedName("strTwitter")
    val teamTwitter: String,
    @SerializedName("strInstagram")
    val teamInstagram: String,
    @SerializedName("strDescriptionEN")
    val teamDescription: String,
    @SerializedName("strCountry")
    val teamCountry: String,
    @SerializedName("strTeamBadge")
    val teamBadge: String,
    @SerializedName("strTeamFanart1")
    val teamFanart: String
) : Parcelable