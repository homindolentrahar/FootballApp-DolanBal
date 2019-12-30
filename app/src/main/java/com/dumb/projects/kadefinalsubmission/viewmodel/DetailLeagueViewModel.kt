package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.model.League
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.runBlocking

class DetailLeagueViewModel(
    application: Application,
    private val idLeague: String,
    private val repo: APIRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private val _league = MutableLiveData<League>()
    val league: LiveData<League>
        get() = _league

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    fun getLeagueDetail() {
        runBlocking {
            try {
                _status.value = View.VISIBLE
                val fetchedLeague = repo.getLeagueDetail(idLeague).leagues[0]
                _status.value = View.GONE
                _league.value = fetchedLeague
            } catch (t: Throwable) {
                _league.value = null
                _status.value = View.GONE
            }
        }
    }
}

class DetailLeagueViewModelFactory(
    private val application: Application,
    private val idLeague: String,
    private val repo: APIRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailLeagueViewModel::class.java)) {
            return DetailLeagueViewModel(application, idLeague, repo) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}