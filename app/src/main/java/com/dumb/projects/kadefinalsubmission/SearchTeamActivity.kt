package com.dumb.projects.kadefinalsubmission

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dumb.projects.kadefinalsubmission.adapter.TeamAdapter
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.databinding.ActivitySearchTeamBinding
import com.dumb.projects.kadefinalsubmission.viewmodel.SearchTeamViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.SearchTeamViewModelFactory
import kotlinx.android.synthetic.main.search_toolbar.*

class SearchTeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySearchTeamBinding>(
            this,
            R.layout.activity_search_team
        )
//        ViewModel setup
        val repo = APIRepository()
        val viewModelFactory = SearchTeamViewModelFactory(application, repo)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchTeamViewModel::class.java)
//        RecyclerView setup
        val adapter = TeamAdapter(TeamAdapter.OnClickTeam {
            viewModel.navigateItem(it)
        })
        binding.rvList.adapter = adapter
//        Handling click action
        viewModel.navigatedItem.observe(this, Observer {
            if (it != null) {
                val intent = Intent(this, DetailTeamActivity::class.java)
                intent.putExtra(DetailTeamActivity.EXTRA_ID, it.idTeam)
                intent.putExtra(DetailTeamActivity.EXTRA_NAME, it.teamName)
                startActivity(intent)
                viewModel.doneNavigateItem()
            }
        })
//        Floating SearchView setup
        floating_search_view.setSearchHint(getString(R.string.search_team))
        floating_search_view.setSearchFocused(true)
        floating_search_view.setDimBackground(true)
        floating_search_view.setOnHomeActionClickListener { finish() }
        floating_search_view.setOnQueryChangeListener { oldQuery, newQuery ->
            if (newQuery != "") {
                viewModel.searchTeam(newQuery)
            }
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.root
    }
}
