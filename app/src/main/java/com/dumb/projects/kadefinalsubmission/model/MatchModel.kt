package com.dumb.projects.kadefinalsubmission.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MatchResponse(
    @SerializedName("events")
    val events: List<Match>
)

data class SearchMatchResponse(
    @SerializedName("event")
    val event: List<Match>
)

data class TeamLastMatchResponse(
    @SerializedName("results")
    val results: List<Match>
)

@Parcelize
data class Match(
    @SerializedName("idEvent")
    val idEvent: String,
    @SerializedName("idLeague")
    val idLeague: String,
    @SerializedName("strEvent")
    val matchName: String,
    @SerializedName("strSport")
    val sportType: String,
    @SerializedName("strLeague")
    val leagueName: String,
    @SerializedName("strHomeTeam")
    val homeTeam: String,
    @SerializedName("strAwayTeam")
    val awayTeam: String,
    @SerializedName("intHomeScore")
    val homeScore: String,
    @SerializedName("intAwayScore")
    val awayScore: String,
    @SerializedName("strHomeGoalDetails")
    val homeGoalDetails: String,
    @SerializedName("strHomeRedCards")
    val homeRedCards: String,
    @SerializedName("strHomeYellowCards")
    val homeYellowCards: String,
    @SerializedName("strHomeLineupSubstitutes")
    val homeSubstitutes: String,
    @SerializedName("strAwayGoalDetails")
    val awayGoalDetails: String,
    @SerializedName("strAwayRedCards")
    val awayRedCards: String,
    @SerializedName("strAwayYellowCards")
    val awayYellowCards: String,
    @SerializedName("strAwayLineupSubstitutes")
    val awaySubstitutes: String,
    @SerializedName("dateEvent")
    val dateEvent: String,
    @SerializedName("strTime")
    val timeEvent: String,
    @SerializedName("idHomeTeam")
    val idHomeTeam: String,
    @SerializedName("idAwayTeam")
    val idAwayTeam: String,
    var homeTeamBadge: String,
    var awayTeamBadge: String,
    var matchBanner: String
) : Parcelable