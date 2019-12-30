package com.dumb.projects.kadefinalsubmission.api

import com.dumb.projects.kadefinalsubmission.BuildConfig
import com.dumb.projects.kadefinalsubmission.model.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface APIService {
    @GET("lookupleague.php")
    suspend fun getLeagueDetail(@Query("id") idLeague: String): LeagueResponse

    @GET("lookup_all_teams.php")
    suspend fun getAllTeams(@Query("id") idLeague: String): TeamResponse

    @GET("lookuptable.php")
    suspend fun getLeagueClassement(@Query("l") idLeague: String, @Query("s") season: String): ClassementResponse

    @GET("lookupteam.php")
    suspend fun getTeamDetail(@Query("id") idTeam: String): TeamResponse

    @GET("eventspastleague.php")
    suspend fun getLeaguePastMatch(@Query("id") idLeague: String): MatchResponse

    @GET("eventslast.php")
    suspend fun getLastMatch(@Query("id") idTeam: String): TeamLastMatchResponse

    @GET("eventsnextleague.php")
    suspend fun getLeagueNextMatch(@Query("id") idLeague: String): MatchResponse

    @GET("eventsnext.php")
    suspend fun getNextMatch(@Query("id") idTeam: String): MatchResponse

    @GET("lookupevent.php")
    suspend fun getMatchDetail(@Query("id") idEvent: String): MatchResponse

    @GET("searchevents.php")
    suspend fun searchEvents(@Query("e") eventQuery: String): SearchMatchResponse

    @GET("searchteams.php")
    suspend fun searchTeams(@Query("t") teamQuery: String): TeamResponse

}

object API {
    val retrofitInstance: APIService by lazy {
        retrofit.create(APIService::class.java)
    }
}