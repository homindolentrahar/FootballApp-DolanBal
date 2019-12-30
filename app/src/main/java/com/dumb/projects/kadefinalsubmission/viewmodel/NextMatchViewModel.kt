package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.model.Match
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NextMatchViewModel(
    application: Application,
    private val idLeague: String,
    private val repo: APIRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) :
    AndroidViewModel(application) {
    private var listNextMatches: List<Match>? = listOf()

    private val _nextMatches = MutableLiveData<List<Match>>()
    val nextMatches: LiveData<List<Match>>
        get() = _nextMatches

    private val _navigatedItem = MutableLiveData<Match>()
    val navigatedItem: LiveData<Match>
        get() = _navigatedItem

    fun getNextMatch() {
        GlobalScope.launch(context.main) {
            try {
                listNextMatches = repo.getLeagueNextMatch(idLeague).events
                listNextMatches?.forEach {
                    async {
                        it.homeTeamBadge = repo.getTeamDetail(it.idHomeTeam).teams[0].teamBadge
                        it.awayTeamBadge = repo.getTeamDetail(it.idAwayTeam).teams[0].teamBadge
                    }
                }
                _nextMatches.value = listNextMatches
            } catch (t: Throwable) {
                _nextMatches.value = listOf()
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

class NextMatchViewModelFactory(
    private val application: Application,
    private val idLeague: String,
    private val repo: APIRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NextMatchViewModel::class.java)) {
            return NextMatchViewModel(application, idLeague, repo) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}