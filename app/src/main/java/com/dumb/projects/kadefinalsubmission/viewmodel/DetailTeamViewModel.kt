package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.db.DatabaseFavorites
import com.dumb.projects.kadefinalsubmission.model.Team
import com.dumb.projects.kadefinalsubmission.model.TeamFavorites
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailTeamViewModel(
    application: Application,
    private val idTeam: String,
    private val repo: APIRepository,
    private val database: DatabaseFavorites,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    val isFavorite: LiveData<List<TeamFavorites>> = database.teamDao().checkTeamFavorites(idTeam)

    fun getDetailTeam() {
        GlobalScope.launch(context.main) {
            try {
                _status.value = View.VISIBLE
                val team = repo.getTeamDetail(idTeam).teams[0]
                _status.value = View.GONE
                _team.value = team
            } catch (t: Throwable) {
                _status.value = View.GONE
                _team.value = null
            }
        }
    }

    fun addFavorites() {
        val item = TeamFavorites(
            idTeam = idTeam,
            teamName = _team.value?.teamName as String,
            teamStadium = _team.value?.teamStadium as String,
            teamLocation = _team.value?.stadiumLocation as String,
            teamBadge = _team.value?.teamBadge as String
        )
        GlobalScope.launch(context.main) {
            database.teamDao().insert(item)
        }
    }

    fun removeFavorites() {
        GlobalScope.launch(context.main) {
            database.teamDao().delete(idTeam)
        }
    }
}

class DetailTeamViewModelFactory(
    private val application: Application,
    private val idTeam: String,
    private val repo: APIRepository,
    private val database: DatabaseFavorites
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailTeamViewModel::class.java)) {
            return DetailTeamViewModel(application, idTeam, repo, database) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}