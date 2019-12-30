package com.dumb.projects.kadefinalsubmission.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dumb.projects.kadefinalsubmission.model.MatchFavorites
import com.dumb.projects.kadefinalsubmission.model.TeamFavorites

object DatabaseContract {
    const val DB_NAME = "favorite.db"
    const val MATCH_TABLE = "match_table"
    const val TEAM_TABLE = "team_table"
    const val TYPE_NEXT = "type_next"
    const val TYPE_LAST = "type_last"
    const val TYPE_SEARCH = "type_search"
}

@Database(
    entities = [MatchFavorites::class, TeamFavorites::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseFavorites : RoomDatabase() {
    abstract fun matchDao(): MatchDao
    abstract fun teamDao(): TeamDao
}

private lateinit var INSTANCE: DatabaseFavorites

fun getDatabase(context: Context): DatabaseFavorites {
    synchronized(context.applicationContext) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                DatabaseFavorites::class.java,
                DatabaseContract.DB_NAME
            ).build()
        }
    }
    return INSTANCE
}

@Dao
interface MatchDao {
    @Query("SELECT * FROM match_table WHERE typeMatch = :type")
    fun getMatchByType(type: String): LiveData<List<MatchFavorites>>

    @Query("SELECT * FROM match_table WHERE idEvent = :idEvent")
    fun checkMatchFavorite(idEvent: String): LiveData<List<MatchFavorites>>

    @Query("SELECT * FROM match_table WHERE idEvent =:idEvent")
    fun getSingleData(idEvent: String): List<MatchFavorites>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MatchFavorites)

    @Query("DELETE FROM match_table WHERE idEvent = :idEvent")
    suspend fun delete(idEvent: String)
}

@Dao
interface TeamDao {
    @Query("SELECT * FROM team_table")
    fun getAllFavoriteTeams(): LiveData<List<TeamFavorites>>

    @Query("SELECT * FROM team_table WHERE idTeam = :idTeam")
    fun checkTeamFavorites(idTeam: String): LiveData<List<TeamFavorites>>

    @Query("SELECT * FROM team_table WHERE idTeam = :idTeam")
    fun getSingleData(idTeam: String): List<TeamFavorites>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TeamFavorites)

    @Query("DELETE FROM team_table WHERE idTeam =:idTeam")
    suspend fun delete(idTeam: String)
}