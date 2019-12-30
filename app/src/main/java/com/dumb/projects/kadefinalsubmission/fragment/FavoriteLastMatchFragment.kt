package com.dumb.projects.kadefinalsubmission.fragment


import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dumb.projects.kadefinalsubmission.DetailMatchActivity
import com.dumb.projects.kadefinalsubmission.adapter.FavoriteMatchAdapter
import com.dumb.projects.kadefinalsubmission.databinding.FragmentFavoriteLastMatchBinding
import com.dumb.projects.kadefinalsubmission.db.getDatabase
import com.dumb.projects.kadefinalsubmission.viewmodel.FavoriteLastMatchViewModel
import com.dumb.projects.kadefinalsubmission.viewmodel.FavoriteLastMatchViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class FavoriteLastMatchFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteLastMatchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteLastMatchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        ViewModel setup
        val database = getDatabase(context as Context)
        val viewModelFactory =
            FavoriteLastMatchViewModelFactory(activity?.application as Application, database)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FavoriteLastMatchViewModel::class.java)
//        RecyclerView setup
        val adapter = FavoriteMatchAdapter(FavoriteMatchAdapter.OnClickMatchFavorites {
            viewModel.navigateItem(it)
        })
        binding.rvList.adapter = adapter
//        Handling click action
        viewModel.navigatedItem.observe(this, Observer {
            if (it != null) {
                val intent = Intent(context, DetailMatchActivity::class.java)
                intent.putExtra(DetailMatchActivity.EXTRA_ID, it.idEvent)
                intent.putExtra(DetailMatchActivity.EXTRA_NAME, it.matchName)
                intent.putExtra(DetailMatchActivity.EXTRA_TYPE, it.typeMatch)
                startActivity(intent)
                viewModel.doneNavigateItem()
            }
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
