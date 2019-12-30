package com.dumb.projects.kadefinalsubmission.fragment


import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dumb.projects.kadefinalsubmission.DetailMatchActivity
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.databinding.FragmentAwayMatchBinding
import com.dumb.projects.kadefinalsubmission.viewmodel.AwayMatchViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.AwayMatchViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class AwayMatchFragment : Fragment() {
    private lateinit var binding: FragmentAwayMatchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAwayMatchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Getting id
        val id = arguments?.getString(DetailMatchActivity.EXTRA_DATA_ID) as String
//        ViewModel setup
        val repo = APIRepository()
        val viewModelFactory =
            AwayMatchViewModelFactory(activity?.application as Application, id, repo)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AwayMatchViewModel::class.java)
        viewModel.getAwayMatch()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
