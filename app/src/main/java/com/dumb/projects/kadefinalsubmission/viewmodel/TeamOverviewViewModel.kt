package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.model.Team
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamOverviewViewModel(
    application: Application,
    private val idTeam: String,
    private val repo: APIRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team

    fun getDetailTeam() {
        GlobalScope.launch(context.main) {
            try {
                val team = repo.getTeamDetail(idTeam).teams[0]
                _team.value = team
            } catch (t: Throwable) {
                _team.value = null
            }
        }
    }
}

class TeamOverviewViewModelFactory(
    private val application: Application,
    private val idTeam: String,
    private val repo: APIRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamOverviewViewModel::class.java)) {
            return TeamOverviewViewModel(application, idTeam, repo) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}