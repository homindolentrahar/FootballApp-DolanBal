package com.dumb.projects.kadefinalsubmission.fragment


import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dumb.projects.kadefinalsubmission.DetailTeamActivity
import com.dumb.projects.kadefinalsubmission.R
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.databinding.FragmentTeamOverviewBinding
import com.dumb.projects.kadefinalsubmission.viewmodel.TeamOverviewViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.TeamOverviewViewModelFactory
import kotlinx.android.synthetic.main.fragment_team_overview.*

/**
 * A simple [Fragment] subclass.
 */
class TeamOverviewFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentTeamOverviewBinding
    private lateinit var openUrl: Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeamOverviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Getting id
        val id = arguments?.getString(DetailTeamActivity.EXTRA_DATA_ID) as String
//        ViewModel setup
        val repo = APIRepository()
        val viewModelFactory =
            TeamOverviewViewModelFactory(activity?.application as Application, id, repo)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TeamOverviewViewModel::class.java)
        viewModel.getDetailTeam()
//        Handling URL click action
        openUrl = Intent(Intent.ACTION_VIEW)
        tv_team_web.setOnClickListener(this)
        tv_team_twitter.setOnClickListener(this)
        tv_team_instagram.setOnClickListener(this)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onClick(view: View) {
        var data = ""
        when (view.id) {
            R.id.tv_team_web -> data = tv_team_web.text.toString()
            R.id.tv_team_twitter -> data = tv_team_twitter.text.toString()
            R.id.tv_team_instagram -> data = tv_team_instagram.text.toString()
        }
        openUrl.data = Uri.parse("https://$data")
        startActivity(openUrl)
    }
}
