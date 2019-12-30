package com.dumb.projects.kadefinalsubmission.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dumb.projects.kadefinalsubmission.R
import com.dumb.projects.kadefinalsubmission.fragment.*

class PagerAdapter(fm: FragmentManager, private val context: Context) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private var listFragment: MutableList<Fragment> = mutableListOf()
    private var listTitle: MutableList<String> = mutableListOf()

    fun setForDetailLeague(argument: Bundle) {
        val lastMatch = LastMatchFragment()
        val nextMatch = NextMatchFragment()
        val leagueOverview = LeagueOverviewFragment()
        val team = TeamFragment()

        lastMatch.arguments = argument
        nextMatch.arguments = argument
        leagueOverview.arguments = argument
        team.arguments = argument

        listFragment.apply {
            add(leagueOverview)
            add(lastMatch)
            add(nextMatch)
            add(team)
        }
        listTitle.apply {
            add(context.getString(R.string.overview))
            add(context.getString(R.string.last_match))
            add(context.getString(R.string.next_match))
            add(context.getString(R.string.teams))
        }
    }

    fun setForDetailMatch(argument: Bundle) {
        val homeMatch = HomeMatchFragment()
        val awayMatch = AwayMatchFragment()

        homeMatch.arguments = argument
        awayMatch.arguments = argument

        listFragment.apply {
            add(homeMatch)
            add(awayMatch)
        }
        listTitle.apply {
            add(context.getString(R.string.home))
            add(context.getString(R.string.away))
        }
    }

    fun setForFavorites() {
        val lastMatch = FavoriteLastMatchFragment()
        val nextMatch = FavoriteNextMatchFragment()
        val team = FavoriteTeamFragment()

        listFragment.apply {
            add(lastMatch)
            add(nextMatch)
            add(team)
        }
        listTitle.apply {
            add(context.getString(R.string.last_match))
            add(context.getString(R.string.next_match))
            add(context.getString(R.string.teams))
        }
    }

    fun setForDetailTeam(argument: Bundle) {
        val teamOverview = TeamOverviewFragment()
        val teamStadium = TeamStadiumFragment()
        val lastMatch = TeamLastMatchFragment()
        val nextMatch = TeamNextMatchFragment()

        teamOverview.arguments = argument
        teamStadium.arguments = argument
        lastMatch.arguments = argument
        nextMatch.arguments = argument

        listFragment.apply {
            add(teamOverview)
            add(teamStadium)
            add(lastMatch)
            add(nextMatch)
        }
        listTitle.apply {
            add(context.getString(R.string.overview))
            add(context.getString(R.string.stadium))
            add(context.getString(R.string.last_match))
            add(context.getString(R.string.next_match))
        }
    }

    override fun getItem(position: Int): Fragment = listFragment[position]

    override fun getCount(): Int = listFragment.size

    override fun getPageTitle(position: Int): CharSequence? = listTitle[position]
}