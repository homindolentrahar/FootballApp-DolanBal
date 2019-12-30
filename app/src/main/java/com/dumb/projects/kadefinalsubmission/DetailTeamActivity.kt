package com.dumb.projects.kadefinalsubmission

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dumb.projects.kadefinalsubmission.adapter.PagerAdapter
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.databinding.ActivityDetailTeamBinding
import com.dumb.projects.kadefinalsubmission.db.getDatabase
import com.dumb.projects.kadefinalsubmission.viewmodel.DetailTeamViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.DetailTeamViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.detail_toolbar.*

class DetailTeamActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id_team"
        const val EXTRA_NAME = "extra_name_team"
        const val EXTRA_DATA_ID = "extra_data_team_id"
    }

    private lateinit var viewModel: DetailTeamViewModel
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailTeamBinding>(
            this,
            R.layout.activity_detail_team
        )
//        Getting data
        val id = intent.getStringExtra(EXTRA_ID) as String
        val name = intent.getStringExtra(EXTRA_NAME) as String
//        Toolbar setup
        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        title = name
//        ViewModel setup
        val repo = APIRepository()
        val database = getDatabase(this)
        val viewModelFactory = DetailTeamViewModelFactory(application, id, repo, database)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailTeamViewModel::class.java)
        viewModel.getDetailTeam()
//        Check if favorite
        viewModel.isFavorite.observe(this, Observer {
            if (it != null) {
                isFavorite = it.isNotEmpty()
            }
        })
//        ViewPager & TabLayout setup
        val argument = Bundle()
        argument.putString(EXTRA_DATA_ID, id)
        val adapter = PagerAdapter(supportFragmentManager, applicationContext)
        adapter.setForDetailTeam(argument)
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_favorite, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.toolbar_favorite_menu -> {
                if (isFavorite) {
                    viewModel.removeFavorites()
                    showSnackbar("Removed from Favorites")
                } else {
                    viewModel.addFavorites()
                    showSnackbar("Added to Favorites")
                }

                isFavorite = !isFavorite
                setFavorite()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavorite() {
        if (isFavorite) menuItem?.getItem(0)?.icon =
            ContextCompat.getDrawable(this, R.drawable.ic_heart_fill)
        else menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_heart)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(detail_team_activity, message, Snackbar.LENGTH_LONG).show()
    }
}
