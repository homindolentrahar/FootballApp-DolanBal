package com.dumb.projects.kadefinalsubmission.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class LeagueResponse(
    @SerializedName("leagues")
    val leagues: List<League>
)

data class League(
    @SerializedName("idLeague")
    val idLeague: String,
    @SerializedName("strLeague")
    val leagueName: String,
    @SerializedName("intFormedYear")
    val leagueFormed: String,
    @SerializedName("dateFirstEvent")
    val leagueFirstEvent: String,
    @SerializedName("strCountry")
    val country: String,
    @SerializedName("strWebsite")
    val website: String,
    @SerializedName("strTwitter")
    val twitter: String,
    @SerializedName("strYoutube")
    val youtube: String,
    @SerializedName("strDescriptionEN")
    val description: String,
    @SerializedName("strBadge")
    val badge: String,
    @SerializedName("strFanart1")
    val fanart: String
)

@Parcelize
data class LeagueLocal(
    val idLeague: String,
    val leagueName: String,
    val country: String,
    val badge: Int
) : Parcelable