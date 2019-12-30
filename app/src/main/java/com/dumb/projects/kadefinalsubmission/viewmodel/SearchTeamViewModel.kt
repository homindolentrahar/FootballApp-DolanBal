package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.model.Team
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchTeamViewModel(
    application: Application,
    private val repo: APIRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private val _searchedTeam = MutableLiveData<List<Team>>()
    val searchedTeam: LiveData<List<Team>>
        get() = _searchedTeam

    private val _navigatedItem = MutableLiveData<Team>()
    val navigatedItem: LiveData<Team>
        get() = _navigatedItem

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int>
        get() = _error

    fun searchTeam(query: String) {
        GlobalScope.launch(context.main) {
            try {
                _status.value = View.VISIBLE
                val listSearchedTeam = repo.searchTeam(query).teams.filter {
                    it.typeSport == "Soccer"
                }
                _status.value = View.GONE

                if (listSearchedTeam[0].typeSport != "Soccer") _error.value = View.VISIBLE
                else _error.value = View.GONE

                _searchedTeam.value = listSearchedTeam
            } catch (t: Throwable) {
                _status.value = View.GONE
                _error.value = View.VISIBLE
                _searchedTeam.value = listOf()
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

class SearchTeamViewModelFactory(
    private val application: Application,
    private val repo: APIRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchTeamViewModel::class.java)) {
            return SearchTeamViewModel(application, repo) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}