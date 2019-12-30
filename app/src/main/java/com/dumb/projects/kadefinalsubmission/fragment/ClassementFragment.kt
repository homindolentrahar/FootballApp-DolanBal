package com.dumb.projects.kadefinalsubmission.fragment


import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dumb.projects.kadefinalsubmission.DetailTeamActivity
import com.dumb.projects.kadefinalsubmission.R
import com.dumb.projects.kadefinalsubmission.adapter.ClassementAdapter
import com.dumb.projects.kadefinalsubmission.api.APIRepository
import com.dumb.projects.kadefinalsubmission.databinding.FragmentClassementBinding
import com.dumb.projects.kadefinalsubmission.viewmodel.ClassementViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.ClassementViewModelFactory
import kotlinx.android.synthetic.main.fragment_classement.*

/**
 * A simple [Fragment] subclass.
 */
class ClassementFragment : Fragment() {
    private lateinit var binding: FragmentClassementBinding

    companion object {
        private const val SPINNER_PREF = "spinner_pref"
        private const val SPINNER_INDEX = "spinner_index"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentClassementBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Getting spinner items
        val leaguesId = resources.getStringArray(R.array.league_id)
        val leaguesName = resources.getStringArray(R.array.league_name)
//        SharedPreference for spinner
        val spinnerPref = context?.getSharedPreferences(SPINNER_PREF, Context.MODE_PRIVATE)
        val index = spinnerPref?.getInt(SPINNER_INDEX, 0) as Int
//        ViewModel setup
        val repo = APIRepository()
        val viewModelFactory =
            ClassementViewModelFactory(activity?.application as Application, repo)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ClassementViewModel::class.java)
        viewModel.getClassement(leaguesId[index])
//        RecyclerView setup
        val adapter = ClassementAdapter(ClassementAdapter.OnClickClassement {
            viewModel.navigateItem(it)
        })
        rv_list.adapter = adapter
//        Handling click action
        viewModel.navigatedItem.observe(this, Observer {
            if (it != null) {
                val intent = Intent(context, DetailTeamActivity::class.java)
                intent.putExtra(DetailTeamActivity.EXTRA_ID, it.teamId)
                intent.putExtra(DetailTeamActivity.EXTRA_NAME, it.teamName)
                startActivity(intent)
                viewModel.doneNavigateItem()
            }
        })
//        Spinner setup
        val arrayAdapter = ArrayAdapter<String>(
            context as Context,
            android.R.layout.simple_spinner_item,
            leaguesName
        )
        spinner_league.setAdapter(arrayAdapter)
        spinner_league.selectedIndex = index
        spinner_league.setOnItemSelectedListener { view, position, id, item ->
            spinnerPref.edit {
                putInt(SPINNER_INDEX, position)
                apply()
            }
            viewModel.getClassement(leaguesId[position])
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
