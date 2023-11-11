package com.example.samplekotlin.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.PlantListAdapter
import com.example.samplekotlin.dataSource.remote.PlantRemoteDataSource
import com.example.samplekotlin.network.NetworkProvider
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.repository.GetRemotePlantRepositoryImpl
import com.example.samplekotlin.useCase.GetRemotePlantUseCaseImpl
import com.example.samplekotlin.viewmodel.PlantListFragmentViewModel

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
        recyclerView.adapter = plantlistAdapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        plantListViewModel.plants.observe(requireActivity(), Observer { data ->
            plantlistAdapter.setOriginalData(plantListViewModel.showRemotePlantData(data))
            plantlistAdapter.setData(plantListViewModel.showRemotePlantData(data))
            plantlistAdapter.notifyDataSetChanged()
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