package com.dumb.projects.kadefinalsubmission.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dumb.projects.kadefinalsubmission.R
import com.dumb.projects.kadefinalsubmission.adapter.PagerAdapter
import com.dumb.projects.kadefinalsubmission.databinding.FragmentMatchBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_match.*

/**
 * A simple [Fragment] subclass.
 */
class MatchFragment : Fragment() {
    companion object {
        private const val PREF_NAME = "spinner_state_pref"
        private const val INDEX = "spinner_index"
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: FragmentMatchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMatchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        SharedPreference for spinner state
        val pref = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var index = pref?.getInt(INDEX, 0) as Int
//        Spinner setup
        val leaguesId = resources.getStringArray(R.array.league_id)
        val leaguesName = resources.getStringArray(R.array.league_name)
        val arrayAdapter = ArrayAdapter(
            context?.applicationContext as Context,
            android.R.layout.simple_spinner_item,
            leaguesName
        )
        spinner_league.setAdapter(arrayAdapter)
        spinner_league.selectedIndex = index
        spinner_league.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(view, item.toString(), Snackbar.LENGTH_SHORT).show()
//        Saving spinner state
            pref.edit {
                putInt(INDEX, position)
                apply()
            }
        }
//        TabLayout & ViewPager setup
        val adapter =
            PagerAdapter(activity?.supportFragmentManager as FragmentManager, context as Context)
        val argument = Bundle()
        argument.putString(EXTRA_ID, leaguesId[index])
        adapter.setForDetailLeague(argument)
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }

}
