package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.model.Team
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamViewModel(
    application: Application,
    private val idLeague: String,
    private val repo: APIRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private val _teams = MutableLiveData<List<Team>>()
    val teams: LiveData<List<Team>>
        get() = _teams

    private val _navigatedItem = MutableLiveData<Team>()
    val navigatedItem: LiveData<Team>
        get() = _navigatedItem

    fun getTeams() {
        GlobalScope.launch(context.main) {
            try {
                val listTeams = repo.getLeagueTeam(idLeague).teams
                _teams.value = listTeams
            } catch (t: Throwable) {
                _teams.value = listOf()
            }
        }
    }

    fun navigateItem(item: Team) {
        _navigatedItem.value = item
    }

    fun doneNavigateItem() {
        _navigatedItem.value = null
    }
}

class TeamViewModelFactory(
    private val application: Application,
    private val idLeague: String,
    private val repo: APIRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            return TeamViewModel(application, idLeague, repo) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}