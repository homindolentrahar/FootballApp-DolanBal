package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.db.DatabaseFavorites
import com.dumb.projects.kadefinalsubmission.model.Match
import com.dumb.projects.kadefinalsubmission.model.MatchFavorites
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchViewModel(
    application: Application,
    private val idMatch: String,
    private val repo: APIRepository,
    private val database: DatabaseFavorites,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private val _match = MutableLiveData<Match>()
    val match: LiveData<Match>
        get() = _match

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    val isFavorite: LiveData<List<MatchFavorites>> = database.matchDao().checkMatchFavorite(idMatch)

    fun getDetailMatch() {
        GlobalScope.launch(context.main) {
            try {
                _status.value = View.VISIBLE
                val detailMatch = repo.getMatchDetail(idMatch).events[0]
                detailMatch.homeTeamBadge =
                    repo.getTeamDetail(detailMatch.idHomeTeam).teams[0].teamBadge
                detailMatch.awayTeamBadge =
                    repo.getTeamDetail(detailMatch.idAwayTeam).teams[0].teamBadge
                detailMatch.matchBanner =
                    repo.getLeagueDetail(detailMatch.idLeague).leagues[0].fanart
                _status.value = View.GONE
                _match.value = detailMatch
            } catch (t: Throwable) {
                _status.value = View.GONE
                _match.value = null
            }
        }
    }

    fun addFavorites(type: String) {
        val item = MatchFavorites(
            idEvent = idMatch,
            matchName = _match.value?.matchName,
            leagueName = _match.value?.leagueName,
            homeTeam = _match.value?.homeTeam,
            awayTeam = _match.value?.awayTeam,
            homeScore = _match.value?.homeScore,
            awayScore = _match.value?.awayScore,
            dateEvent = _match.value?.dateEvent,
            timeEvent = _match.value?.timeEvent,
            homeTeamBadge = _match.value?.homeTeamBadge,
            awayTeamBadge = _match.value?.awayTeamBadge,
            typeMatch = type
        )
        GlobalScope.launch(context.main) {
            database.matchDao().insert(item)
        }
    }

    fun removeFavorites() {
        GlobalScope.launch(context.main) {
            database.matchDao().delete(idMatch)
        }
    }
}

class DetailMatchViewModelFactory(
    private val application: Application,
    private val idMatch: String,
    private val repo: APIRepository,
    private val database: DatabaseFavorites
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailMatchViewModel::class.java)) {
            return DetailMatchViewModel(application, idMatch, repo, database) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}