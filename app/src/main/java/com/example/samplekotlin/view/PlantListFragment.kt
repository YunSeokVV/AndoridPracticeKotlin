package com.example.samplekotlin.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplekotlin.BuildConfig
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.PlantListAdapter
import com.example.samplekotlin.dataSource.local.GetPlantDataSourceImpl
import com.example.samplekotlin.dataSource.remote.PlantRemoteDataSource
import com.example.samplekotlin.database.PlantDatabase
import com.example.samplekotlin.network.NetworkProvider
import com.example.samplekotlin.util.SendPlantListener
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.repository.GetLocalPlantRepositoryImpl
import com.example.samplekotlin.repository.GetRemotePlantRepositoryImpl
import com.example.samplekotlin.useCase.GetLocalPlantUseCaseImpl
import com.example.samplekotlin.useCase.GetRemotePlantUseCaseImpl
import com.example.samplekotlin.viewmodel.MainActivityViewModel
import com.example.samplekotlin.viewmodel.PlantListFragmentViewModel
import com.orhanobut.logger.Logger


class PlantListFragment : Fragment() {
    private val plantListViewModel: PlantListFragmentViewModel by viewModels {
        viewModelFactory {
            initializer {
                PlantListFragmentViewModel(
                    GetRemotePlantUseCaseImpl(
                        GetRemotePlantRepositoryImpl(
                            PlantRemoteDataSource(NetworkProvider.provideApi())
                        )
                    )
                )
            }
        }
    }

    private val plantlistAdapter: PlantListAdapter by lazy {
        PlantListAdapter(object : PlantListAdapter.OnItemClickListener {
            override fun onItemClick(plant: Plant) {
                val transaction = parentFragmentManager.beginTransaction()
                val plantDetailFragment = PlantDetailFragment(plant)
                transaction.replace(R.id.fragmentContainer, plantDetailFragment)
                transaction.commit()
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plant_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.plantListrecyclerView)
        // 자바에서 레이아웃 매너저를 설정하는 방식과 다르다.
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        plantListViewModel.plants.observe(requireActivity(), Observer { data ->
            plantListViewModel.showRemotePlantData(data, plantlistAdapter)
            recyclerView.adapter = plantlistAdapter
        })

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.settings) {
            plantlistAdapter.filterPlants()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}