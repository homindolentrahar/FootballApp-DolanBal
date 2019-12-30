package com.dumb.projects.kadefinalsubmission.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dumb.projects.kadefinalsubmission.adapter.PagerAdapter
import com.dumb.projects.kadefinalsubmission.databinding.FragmentFavoriteBinding
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        TabLayout & ViewPager setup
        val adapter =
            PagerAdapter(activity?.supportFragmentManager as FragmentManager, context as Context)
        adapter.setForFavorites()
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }
}
