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
import com.dumb.projects.kadefinalsubmission.DetailMatchActivity
import com.dumb.projects.kadefinalsubmission.adapter.MatchAdapter
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.databinding.FragmentNextMatchBinding
import com.dumb.projects.kadefinalsubmission.db.DatabaseContract
import com.dumb.projects.kadefinalsubmission.viewmodel.NextMatchViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.NextMatchViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment() {
    private lateinit var binding: FragmentNextMatchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNextMatchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Getting id
        val id = arguments?.getString(DetailLeagueActivity.EXTRA_DATA_ID) as String
//        ViewModel setup
        val repo = APIRepository()
        val viewModelFactory =
            NextMatchViewModelFactory(activity?.application as Application, id, repo)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NextMatchViewModel::class.java)
        viewModel.getNextMatch()
//        RecyclerView setup
        val adapter = MatchAdapter(MatchAdapter.OnClickMatch {
            viewModel.navigateItem(it)
        })
        binding.rvList.adapter = adapter
//        Handling click action
        viewModel.navigatedItem.observe(this, Observer {
            if (it != null) {
                val intent = Intent(context, DetailMatchActivity::class.java)
                intent.putExtra(DetailMatchActivity.EXTRA_ID, it.idEvent)
                intent.putExtra(DetailMatchActivity.EXTRA_NAME, it.matchName)
                intent.putExtra(DetailMatchActivity.EXTRA_TYPE, DatabaseContract.TYPE_NEXT)
                startActivity(intent)
                viewModel.doneNavigateItem()
            }
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
