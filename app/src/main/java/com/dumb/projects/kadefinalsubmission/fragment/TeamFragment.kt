package com.dumb.projects.kadefinalsubmission.fragment


import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dumb.projects.kadefinalsubmission.DetailLeagueActivity
import com.dumb.projects.kadefinalsubmission.DetailTeamActivity
import com.dumb.projects.kadefinalsubmission.adapter.TeamAdapter
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.databinding.FragmentTeamBinding
import com.dumb.projects.kadefinalsubmission.viewmodel.TeamViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.TeamViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class TeamFragment : Fragment() {
    private lateinit var binding: FragmentTeamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeamBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Getting id
        val id = arguments?.getString(DetailLeagueActivity.EXTRA_DATA_ID) as String
//        ViewModel setup
        val repo = APIRepository()
        val viewModelFactory = TeamViewModelFactory(activity?.application as Application, id, repo)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(TeamViewModel::class.java)
        viewModel.getTeams()
//        RecyclerView setup
        val adapter = TeamAdapter(TeamAdapter.OnClickTeam {
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
