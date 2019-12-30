package com.dumb.projects.kadefinalsubmission.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dumb.projects.kadefinalsubmission.db.DatabaseContract

@Entity(tableName = DatabaseContract.MATCH_TABLE)
data class MatchFavorites(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val idEvent: String?,
    val matchName: String?,
    val leagueName: String?,
    val homeTeam: String?,
    val awayTeam: String?,
    val homeScore: String?,
    val awayScore: String?,
    val dateEvent: String?,
    val timeEvent: String?,
    val homeTeamBadge: String?,
    val awayTeamBadge: String?,
    val typeMatch: String?
)

@Entity(tableName = DatabaseContract.TEAM_TABLE)
data class TeamFavorites(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val idTeam: String?,
    val teamName: String?,
    val teamStadium: String?,
    val teamLocation: String?,
    val teamBadge: String?
)

object FavoritesModel {
    val dummyMatchData: MatchFavorites = MatchFavorites(
        id = 1,
        idEvent = "1234",
        matchName = "Celtic vs Roma",
        leagueName = "UEFA",
        dateEvent = "2019-12-12",
        timeEvent = "12:34:00",
        homeTeamBadge = "homeBadge",
        awayTeamBadge = "awayBadge",
        homeTeam = "Celtic",
        awayTeam = "Roma",
        homeScore = "3",
        awayScore = "2",
        typeMatch = DatabaseContract.TYPE_LAST
    )

    val dummyTeamData: TeamFavorites = TeamFavorites(
        id = 1,
        idTeam = "2345",
        teamName = "Celtic",
        teamStadium = "Celtic Stadium",
        teamLocation = "London",
        teamBadge = "teamBadge"
    )
}