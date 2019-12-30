package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.model.Match
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AwayMatchViewModel(
    application: Application,
    private val idEvent: String,
    private val repo: APIRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private val _awayMatch = MutableLiveData<Match>()
    val awayMatch: LiveData<Match>
        get() = _awayMatch

    fun getAwayMatch() {
        GlobalScope.launch(context.main) {
            try {
                val awayMatch = repo.getMatchDetail(idEvent).events[0]
                _awayMatch.value = awayMatch
            } catch (t: Throwable) {
                _awayMatch.value = null
            }
        }
    }
}

class AwayMatchViewModelFactory(
    private val application: Application,
    private val idEvent: String,
    private val repo: APIRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AwayMatchViewModel::class.java)) {
            return AwayMatchViewModel(application, idEvent, repo) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}