package com.dumb.projects.kadefinalsubmission.fragment


import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dumb.projects.kadefinalsubmission.DetailLeagueActivity
import com.dumb.projects.kadefinalsubmission.adapter.LeagueLocalAdapter
import com.dumb.projects.kadefinalsubmission.databinding.FragmentLeagueBinding
import com.dumb.projects.kadefinalsubmission.viewmodel.LeagueViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.LeagueViewModelFactory
import org.jetbrains.anko.startActivity

/**
 * A simple [Fragment] subclass.
 */
class LeagueFragment : Fragment() {
    private lateinit var binding: FragmentLeagueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLeagueBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        ViewModel setup
        val viewModelFactory =
            LeagueViewModelFactory(activity?.application as Application, context as Context)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LeagueViewModel::class.java)
//        Recyclerview setup
        val adapter = LeagueLocalAdapter(LeagueLocalAdapter.OnClickLeagueLocal {
            viewModel.navigateToDetail(it)
        })
        binding.rvList.adapter = adapter
//        Handling click action
        viewModel.navigateItem.observe(this, Observer {
            if (it != null) {
                activity?.startActivity<DetailLeagueActivity>(DetailLeagueActivity.EXTRA_DATA to it)
                viewModel.doneNavigate()
            }
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
