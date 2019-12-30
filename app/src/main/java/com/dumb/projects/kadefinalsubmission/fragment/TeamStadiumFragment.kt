package com.dumb.projects.kadefinalsubmission.fragment


import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dumb.projects.kadefinalsubmission.DetailTeamActivity
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.databinding.FragmentTeamStadiumBinding
import com.dumb.projects.kadefinalsubmission.viewmodel.TeamStadiumViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.TeamStadiumViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class TeamStadiumFragment : Fragment() {
    private lateinit var binding: FragmentTeamStadiumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeamStadiumBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Getting id
        val id = arguments?.getString(DetailTeamActivity.EXTRA_DATA_ID) as String
//        ViewModel setup
        val repo = APIRepository()
        val viewModelFactory =
            TeamStadiumViewModelFactory(activity?.application as Application, id, repo)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TeamStadiumViewModel::class.java)
        viewModel.getDetailTeam()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
