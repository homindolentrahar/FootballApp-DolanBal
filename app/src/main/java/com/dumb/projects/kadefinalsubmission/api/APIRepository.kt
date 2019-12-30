package com.dumb.projects.kadefinalsubmission.api

import com.dumb.projects.kadefinalsubmission.model.*

class APIRepository {
    suspend fun getLeagueDetail(idLeague: String): LeagueResponse =
        API.retrofitInstance.getLeagueDetail(idLeague)

    suspend fun getLeagueClassement(idLeague: String, season: String): ClassementResponse =
        API.retrofitInstance.getLeagueClassement(idLeague, season)

    suspend fun getLeagueLastMatch(idLeague: String): MatchResponse =
        API.retrofitInstance.getLeaguePastMatch(idLeague)

    suspend fun getLeagueNextMatch(idLeague: String): MatchResponse =
        API.retrofitInstance.getLeagueNextMatch(idLeague)

    suspend fun getLeagueTeam(idLeague: String): TeamResponse =
        API.retrofitInstance.getAllTeams(idLeague)

    suspend fun getTeamDetail(idTeam: String): TeamResponse =
        API.retrofitInstance.getTeamDetail(idTeam)

    suspend fun getMatchDetail(idMatch: String): MatchResponse =
        API.retrofitInstance.getMatchDetail(idMatch)

    suspend fun getNextMatch(idTeam: String): MatchResponse =
        API.retrofitInstance.getNextMatch(idTeam)

    suspend fun getLastMatch(idTeam: String): TeamLastMatchResponse =
        API.retrofitInstance.getLastMatch(idTeam)

    suspend fun searchMatch(query: String): SearchMatchResponse =
        API.retrofitInstance.searchEvents(query)

    suspend fun searchTeam(query: String): TeamResponse =
        API.retrofitInstance.searchTeams(query)
}