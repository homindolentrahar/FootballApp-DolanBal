package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.model.Match
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LastMatchViewModel(
    application: Application,
    private val idLeague: String,
    private val repo: APIRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private var listLastMatches: List<Match>? = listOf()

    private val _lastMatches = MutableLiveData<List<Match>>()
    val lastMatch: LiveData<List<Match>>
        get() = _lastMatches

    private val _navigatedItem = MutableLiveData<Match>()
    val navigatedItem: LiveData<Match>
        get() = _navigatedItem

    fun getLastMatch() {
        GlobalScope.launch(context.main) {
            try {
                listLastMatches = repo.getLeagueLastMatch(idLeague).events
                listLastMatches?.forEach {
                    async {
                        it.homeTeamBadge = repo.getTeamDetail(it.idHomeTeam).teams[0].teamBadge
                        it.awayTeamBadge = repo.getTeamDetail(it.idAwayTeam).teams[0].teamBadge
                    }
                }
                _lastMatches.value = listLastMatches
            } catch (t: Throwable) {
                _lastMatches.value = listOf()
            }
        }
    }

    fun navigateItem(item: Match) {
        _navigatedItem.value = item
    }

    fun doneNavigateItem() {
        _navigatedItem.value = null
    }
}

class LastMatchViewModelFactory(
    private val application: Application,
    private val idLeague: String,
    private val repo: APIRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LastMatchViewModel::class.java)) {
            return LastMatchViewModel(application, idLeague, repo) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}