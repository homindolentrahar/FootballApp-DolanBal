package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.model.Match
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchMatchViewModel(
    application: Application,
    private val repo: APIRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private val _searchedMatch = MutableLiveData<List<Match>>()
    val searchedMatch: LiveData<List<Match>>
        get() = _searchedMatch

    private val _navigatedItem = MutableLiveData<Match>()
    val navigatedItem: LiveData<Match>
        get() = _navigatedItem

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int>
        get() = _error

    fun searchMatch(query: String) {
        GlobalScope.launch(context.main) {
            try {
                _status.value = View.VISIBLE
                val listSearchedMatch = repo.searchMatch(query).event.filter {
                    it.sportType == "Soccer"
                }
                listSearchedMatch.forEach {
                    async {
                        it.homeTeamBadge = repo.getTeamDetail(it.idHomeTeam).teams[0].teamBadge
                        it.awayTeamBadge = repo.getTeamDetail(it.idAwayTeam).teams[0].teamBadge
                    }
                }
                _status.value = View.GONE

                if (listSearchedMatch[0].sportType != "Soccer") _error.value = View.VISIBLE
                else _error.value = View.GONE

                _searchedMatch.value = listSearchedMatch
            } catch (t: Throwable) {
                _searchedMatch.value = listOf()
                _status.value = View.GONE
                _error.value = View.VISIBLE
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

class SearchMatchViewModelFactory(
    private val application: Application,
    private val repo: APIRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchMatchViewModel::class.java)) {
            return SearchMatchViewModel(application, repo) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}