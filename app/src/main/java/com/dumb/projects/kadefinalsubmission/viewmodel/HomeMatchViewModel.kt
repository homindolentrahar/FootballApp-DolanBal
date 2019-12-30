package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.model.Match
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeMatchViewModel(
    application: Application,
    private val idEvent: String,
    private val repo: APIRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private val _homeMatch = MutableLiveData<Match>()
    val homeMatch: LiveData<Match>
        get() = _homeMatch

    fun getHomeMatch() {
        GlobalScope.launch(context.main) {
            try {
                val homeMatch = repo.getMatchDetail(idEvent).events[0]
                _homeMatch.value = homeMatch
            } catch (t: Throwable) {
                _homeMatch.value = null
            }
        }
    }
}

class HomeMatchViewModelFactory(
    private val application: Application,
    private val idEvent: String,
    private val repo: APIRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeMatchViewModel::class.java)) {
            return HomeMatchViewModel(application, idEvent, repo) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}