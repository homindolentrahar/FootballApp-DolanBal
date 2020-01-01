package com.dumb.projects.kadefinalsubmission

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.dumb.projects.kadefinalsubmission.adapter.MatchAdapter
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.databinding.ActivitySearchMatchBinding
import com.dumb.projects.kadefinalsubmission.db.DatabaseContract
import com.dumb.projects.kadefinalsubmission.viewmodel.SearchMatchViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.SearchMatchViewModelFactory
import kotlinx.android.synthetic.main.search_toolbar.*

class SearchMatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySearchMatchBinding>(
            this,
            R.layout.activity_search_match
        )
//        ViewModel setup
        val repo = APIRepository()
        val viewModelFactory = SearchMatchViewModelFactory(application, repo)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchMatchViewModel::class.java)
//        RecyclerView setup
        val adapter = MatchAdapter(MatchAdapter.OnClickMatch {
            viewModel.navigateItem(it)
        })
        binding.rvList.adapter = adapter
//        Handling click action
        viewModel.navigatedItem.observe(this, Observer {
            if (it != null) {
                val intent = Intent(this, DetailMatchActivity::class.java)
                intent.putExtra(DetailMatchActivity.EXTRA_ID, it.idEvent)
                intent.putExtra(DetailMatchActivity.EXTRA_NAME, it.matchName)
                intent.putExtra(DetailMatchActivity.EXTRA_TYPE, DatabaseContract.TYPE_SEARCH)
                startActivity(intent)
                viewModel.doneNavigateItem()
            }
        })
//        Floating SearchView setup
        floating_search_view.setSearchHint(getString(R.string.search_match))
        floating_search_view.setSearchFocused(true)
        floating_search_view.setDimBackground(true)
        floating_search_view.setOnHomeActionClickListener { finish() }
        floating_search_view.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String?) {
                if (!currentQuery?.trim().equals("")) {
                    currentQuery?.let {
                        viewModel.searchMatch(it.trim())
                    }
                }
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.root
    }

}
