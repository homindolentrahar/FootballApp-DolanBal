package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.model.League
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueOverviewViewModel(
    application: Application,
    private val idLeague: String,
    private val repo: APIRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private val _league = MutableLiveData<League>()
    val league: LiveData<League>
        get() = _league

    fun getLeagueDetail() {
        GlobalScope.launch(context.main) {
            try {
                val leagueDetail = repo.getLeagueDetail(idLeague).leagues[0]
                _league.value = leagueDetail
            } catch (t: Throwable) {
                _league.value = null
            }
        }
    }
}

class LeagueOverviewViewModelFactory(
    private val application: Application,
    private val idLeague: String,
    private val repo: APIRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeagueOverviewViewModel::class.java)) {
            return LeagueOverviewViewModel(application, idLeague, repo) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}