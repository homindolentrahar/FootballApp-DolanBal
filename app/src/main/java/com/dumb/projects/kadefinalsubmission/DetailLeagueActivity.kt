package com.dumb.projects.kadefinalsubmission

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.dumb.projects.kadefinalsubmission.adapter.PagerAdapter
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.databinding.ActivityDetailLeagueBinding
import com.dumb.projects.kadefinalsubmission.model.LeagueLocal
import com.dumb.projects.kadefinalsubmission.viewmodel.DetailLeagueViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.DetailLeagueViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_league.*
import kotlinx.android.synthetic.main.detail_toolbar.*
import org.jetbrains.anko.startActivity

class DetailLeagueActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_league_local_data"
        const val EXTRA_DATA_ID = "extra_league_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailLeagueBinding>(
            this,
            R.layout.activity_detail_league
        )
//        Getting local data
        val data = intent.getParcelableExtra<LeagueLocal>(EXTRA_DATA)
//        Toolbar setup
        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        title = data?.leagueName
//        ViewPager & TabLayout setup
        val argument = Bundle()
        argument.putString(EXTRA_DATA_ID, data?.idLeague)
        val pagerAdapter = PagerAdapter(supportFragmentManager, applicationContext)
        pagerAdapter.setForDetailLeague(argument)
        view_pager.adapter = pagerAdapter
        tab_layout.setupWithViewPager(view_pager)
//        ViewModel setup
        val repo = APIRepository()
        val viewModelFactory =
            DetailLeagueViewModelFactory(application, data?.idLeague as String, repo)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailLeagueViewModel::class.java)
        viewModel.getLeagueDetail()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.toolbar_search_menu -> startActivity<SearchTeamActivity>()
        }
        return super.onOptionsItemSelected(item)
    }
}
