package com.dumb.projects.kadefinalsubmission.fragment


import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dumb.projects.kadefinalsubmission.DetailTeamActivity
import com.dumb.projects.kadefinalsubmission.adapter.FavoriteTeamAdapter
import com.dumb.projects.kadefinalsubmission.databinding.FragmentFavoriteTeamBinding
import com.dumb.projects.kadefinalsubmission.db.getDatabase
import com.dumb.projects.kadefinalsubmission.viewmodel.FavoriteTeamViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.FavoriteTeamViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTeamFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteTeamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteTeamBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        ViewModel setup
        val database = getDatabase(context as Context)
        val viewModelFactory =
            FavoriteTeamViewModelFactory(activity?.application as Application, database)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(FavoriteTeamViewModel::class.java)
//        RecyclerView setup
        val adapter = FavoriteTeamAdapter(FavoriteTeamAdapter.OnClickTeamFavorites {
            viewModel.navigateItem(it)
        })
        binding.rvList.adapter = adapter
//        Handling click action
        viewModel.navigatedItem.observe(this, Observer {
            if (it != null) {
                val intent = Intent(context, DetailTeamActivity::class.java)
                intent.putExtra(DetailTeamActivity.EXTRA_ID, it.idTeam)
                intent.putExtra(DetailTeamActivity.EXTRA_NAME, it.teamName)
                startActivity(intent)
                viewModel.doneNavigateItem()
            }
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
