package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.db.DatabaseContract
import com.dumb.projects.kadefinalsubmission.db.DatabaseFavorites
import com.dumb.projects.kadefinalsubmission.model.MatchFavorites
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider

class FavoriteLastMatchViewModel(
    application: Application,
    private val database: DatabaseFavorites,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    val favoriteLastMatch: LiveData<List<MatchFavorites>> =
        database.matchDao().getMatchByType(DatabaseContract.TYPE_LAST)

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

class FavoriteLastMatchViewModelFactory(
    private val application: Application,
    private val database: DatabaseFavorites
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteLastMatchViewModel::class.java)) {
            return FavoriteLastMatchViewModel(application, database) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}