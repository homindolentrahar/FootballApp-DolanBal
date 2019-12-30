package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.db.DatabaseContract
import com.dumb.projects.kadefinalsubmission.db.DatabaseFavorites
import com.dumb.projects.kadefinalsubmission.model.MatchFavorites
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider

class FavoriteNextMatchViewModel(
    application: Application,
    private val database: DatabaseFavorites,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    val favoriteNextMatch: LiveData<List<MatchFavorites>> =
        database.matchDao().getMatchByType(DatabaseContract.TYPE_NEXT)

    private val _navigatedItem = MutableLiveData<MatchFavorites>()
    val navigatedItem: LiveData<MatchFavorites>
        get() = _navigatedItem

    fun navigateItem(item: MatchFavorites) {
        _navigatedItem.value = item
    }

    fun doneNavigateItem() {
        _navigatedItem.value = null
    }
}

class FavoriteNextMatchViewModelFactory(
    private val application: Application,
    private val database: DatabaseFavorites
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteNextMatchViewModel::class.java)) {
            return FavoriteNextMatchViewModel(application, database) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}