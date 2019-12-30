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
import com.dumb.projects.kadefinalsubmission.DetailLeagueActivity
import com.dumb.projects.kadefinalsubmission.R
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.databinding.FragmentLeagueOverviewBinding
import com.dumb.projects.kadefinalsubmission.viewmodel.LeagueOverviewViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.LeagueOverviewViewModelFactory
import kotlinx.android.synthetic.main.fragment_league_overview.*

/**
 * A simple [Fragment] subclass.
 */
class LeagueOverviewFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentLeagueOverviewBinding
    private lateinit var openUrl: Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLeagueOverviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Getting id
        val id = arguments?.getString(DetailLeagueActivity.EXTRA_DATA_ID) as String
//        ViewModel setup
        val repo = APIRepository()
        val viewModelFactory =
            LeagueOverviewViewModelFactory(activity?.application as Application, id, repo)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LeagueOverviewViewModel::class.java)
        viewModel.getLeagueDetail()
//        Handling URL click action
        openUrl = Intent(Intent.ACTION_VIEW)
        tv_league_web.setOnClickListener(this)
        tv_league_twitter.setOnClickListener(this)
        tv_league_youtube.setOnClickListener(this)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onClick(view: View) {
        var data = ""
        when (view.id) {
            R.id.tv_league_web -> data = tv_league_web.text.toString()
            R.id.tv_league_twitter -> data = tv_league_twitter.text.toString()
            R.id.tv_league_youtube -> data = tv_league_youtube.text.toString()
        }
        openUrl.data = Uri.parse("https://$data")
        startActivity(openUrl)
    }
}
