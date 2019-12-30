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
import com.dumb.projects.kadefinalsubmission.databinding.ActivityDetailMatchBinding
import com.dumb.projects.kadefinalsubmission.db.getDatabase
import com.dumb.projects.kadefinalsubmission.viewmodel.DetailMatchViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.DetailMatchViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_match.*
import kotlinx.android.synthetic.main.detail_toolbar.*

class DetailMatchActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_DATA_ID = "extra_data_match_id"
    }

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var viewModel: DetailMatchViewModel
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailMatchBinding>(
            this,
            R.layout.activity_detail_match
        )
//        Getting data
        val id = intent.getStringExtra(EXTRA_ID)
        val name = intent.getStringExtra(EXTRA_NAME)
        type = intent.getStringExtra(EXTRA_TYPE) as String
//        Toolbar setup
        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        title = name
//        ViewModel setup
        val repo = APIRepository()
        val database = getDatabase(this)
        val viewModelFactory =
            DetailMatchViewModelFactory(application, id as String, repo, database)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailMatchViewModel::class.java)
        viewModel.getDetailMatch()
//        Check if favorite
        viewModel.isFavorite.observe(this, Observer {
            if (it != null) {
                isFavorite = it.isNotEmpty()
            }
        })
//        ViewPager & TabLayout setup
        val bundle = Bundle()
        bundle.putString(EXTRA_DATA_ID, id)
        val pagerAdapter = PagerAdapter(supportFragmentManager, applicationContext)
        pagerAdapter.setForDetailMatch(bundle)
        view_pager.adapter = pagerAdapter
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
                    viewModel.addFavorites(type)
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
        else menuItem?.getItem(0)?.icon =
            ContextCompat.getDrawable(this, R.drawable.ic_heart)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(detail_match_activity, message, Snackbar.LENGTH_LONG).show()
    }
}
