package com.dumb.projects.kadefinalsubmission.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.model.Classement
import com.dumb.projects.kadefinalsubmission.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ClassementViewModel(
    application: Application,
    private val repo: APIRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AndroidViewModel(application) {
    private val _classement = MutableLiveData<List<Classement>>()
    val classement: LiveData<List<Classement>>
        get() = _classement

    private val _navigatedItem = MutableLiveData<Classement>()
    val navigatedItem: LiveData<Classement>
        get() = _navigatedItem

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    fun getClassement(idLeague: String) {
        GlobalScope.launch(context.main) {
            try {
                _status.value = View.VISIBLE
                var rank = 1
                val listClassement = repo.getLeagueClassement(idLeague, "1920").table
                listClassement.forEach {
                    it.rank = rank++
                    it.teamBadge = repo.getTeamDetail(it.teamId).teams[0].teamBadge
                }
                _status.value = View.GONE
                _classement.value = listClassement
            } catch (t: Throwable) {
                _status.value = View.GONE
                _classement.value = listOf()
            }
        }
    }

    fun navigateItem(classement: Classement) {
        _navigatedItem.value = classement
    }

    fun doneNavigateItem() {
        _navigatedItem.value = null
    }
}

class ClassementViewModelFactory(
    private val application: Application,
    private val repo: APIRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClassementViewModel::class.java)) {
            return ClassementViewModel(application, repo) as T
        }
        throw IllegalArgumentException("ViewModel class not found !")
    }
}