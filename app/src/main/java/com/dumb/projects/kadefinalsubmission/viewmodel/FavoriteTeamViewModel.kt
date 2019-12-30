package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.db.DatabaseFavorites
import com.dumb.projects.kadefinalsubmission.model.TeamFavorites
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider

class FavoriteTeamViewModel(
    application: Application,
    private val database: DatabaseFavorites,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) :
    AndroidViewModel(application) {
    val favoriteTeams: LiveData<List<TeamFavorites>> = database.teamDao().getAllFavoriteTeams()

    private val _navigatedItem = MutableLiveData<TeamFavorites>()
    val navigatedItem: LiveData<TeamFavorites>
        get() = _navigatedItem

    fun navigateItem(item: TeamFavorites) {
        _navigatedItem.value = item
    }

    fun doneNavigateItem() {
        _navigatedItem.value = null
    }

}

class FavoriteTeamViewModelFactory(
    private val application: Application,
    private val database: DatabaseFavorites
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteTeamViewModel::class.java)) {
            return FavoriteTeamViewModel(application, database) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}