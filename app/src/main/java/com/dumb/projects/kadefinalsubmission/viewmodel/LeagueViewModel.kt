package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.R
import com.dumb.projects.kadefinalsubmission.model.LeagueLocal

class LeagueViewModel(application: Application, private val context: Context) :
    AndroidViewModel(application) {
    private val _leagues = MutableLiveData<List<LeagueLocal>>()
    val leagues: LiveData<List<LeagueLocal>>
        get() = _leagues

    private val _navigateItem = MutableLiveData<LeagueLocal>()
    val navigateItem: LiveData<LeagueLocal>
        get() = _navigateItem

    private var listLeague: MutableList<LeagueLocal> = mutableListOf()

    init {
        getLocalItems()
    }

    private fun getLocalItems() {
        val id = context.resources.getStringArray(R.array.league_id)
        val name = context.resources.getStringArray(R.array.league_name)
        val country = context.resources.getStringArray(R.array.league_country)
        val badge = context.resources.obtainTypedArray(R.array.league_badge)
        listLeague.clear()

        for (i in id.indices) {
            listLeague.add(
                LeagueLocal(
                    idLeague = id[i],
                    leagueName = name[i],
                    country = country[i],
                    badge = badge.getResourceId(i, 0)
                )
            )
        }
        badge.recycle()
        _leagues.value = listLeague
    }

    fun navigateToDetail(item: LeagueLocal) {
        _navigateItem.value = item
    }

    fun doneNavigate() {
        _navigateItem.value = null
    }
}

class LeagueViewModelFactory(private val application: Application, private val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeagueViewModel::class.java)) {
            return LeagueViewModel(application, context) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}