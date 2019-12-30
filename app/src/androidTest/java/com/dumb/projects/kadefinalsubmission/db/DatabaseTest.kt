package com.dumb.projects.kadefinalsubmission.db

import androidx.room.Room
import androidx.test.runner.AndroidJUnit4
import com.dumb.projects.kadefinalsubmission.model.FavoritesModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var matchDao: MatchDao
    private lateinit var teamDao: TeamDao
    private lateinit var db: DatabaseFavorites

    @Before
    fun createDB() {
        db = Room.inMemoryDatabaseBuilder(
            androidx.test.InstrumentationRegistry.getContext(),
            DatabaseFavorites::class.java
        ).build()
        matchDao = db.matchDao()
        teamDao = db.teamDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testInsertMatch() {
        runBlocking {
            val dummyMatchData = FavoritesModel.dummyMatchData
            matchDao.insert(dummyMatchData)
            val getData = matchDao.getSingleData("1234")[0]
            assertEquals(getData, dummyMatchData)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testInsertTeam() {
        runBlocking {
            val dummyTeamData = FavoritesModel.dummyTeamData
            teamDao.insert(dummyTeamData)
            val getData = teamDao.getSingleData("2345")[0]
            assertEquals(getData, dummyTeamData)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testDeleteMatch() {
        runBlocking {
            val dummyMatchData = FavoritesModel.dummyMatchData
            matchDao.insert(dummyMatchData)
            matchDao.delete("1234")
            assert(matchDao.getSingleData("1234").isEmpty())
        }
    }

    @Test
    @Throws(Exception::class)
    fun testDeleteTeam() {
        runBlocking {
            val dummyTeamData = FavoritesModel.dummyTeamData
            teamDao.insert(dummyTeamData)
            teamDao.delete("2345")
            assert(teamDao.getSingleData("2345").isEmpty())
        }
    }

}