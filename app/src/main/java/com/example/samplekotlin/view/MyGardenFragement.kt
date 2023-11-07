package com.example.samplekotlin.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.PlantListAdapter
import com.example.samplekotlin.dataSource.local.GetPlantDataSourceImpl
import com.example.samplekotlin.database.PlantDatabase
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.repository.GetLocalPlantRepositoryImpl
import com.example.samplekotlin.useCase.GetLocalPlantUseCaseImpl
import com.example.samplekotlin.viewmodel.MainActivityViewModel
import com.orhanobut.logger.Logger


class MyGardenFragement : Fragment() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels<MainActivityViewModel> {
        viewModelFactory {
            initializer {
                MainActivityViewModel(
                    GetLocalPlantUseCaseImpl(
                        GetLocalPlantRepositoryImpl(
                            GetPlantDataSourceImpl(
                                PlantDatabase.getInstance(requireContext().applicationContext)!!
                                    .plantDao()
                            )
                        )
                    )
                )
            }
        }
    }

    private val plantlistAdapter: PlantListAdapter by lazy {
        PlantListAdapter()
    }

    private val clickListener by lazy {
        (object : PlantListAdapter.OnItemClickListener {
            override fun onItemClick(data: Plant) {
                val transaction = parentFragmentManager.beginTransaction()
                val plantDetailFragment = PlantDetailFragment(data)
                transaction.replace(R.id.fragmentContainer, plantDetailFragment)
                transaction.commit()

            }
        })
    }

//    //todo : 클릭리스너에 대한 질문이 끝나면 이 코드를 설정하도록 하기
//    private val longClickListener by lazy {
//        (object : PlantListAdapter.OnItemLongClickListener {
//            override fun onItemlongClick(plant: Plant) {
//                //viewHolder.
//            }
//        })
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_garden_fragement, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.myPlantListrecyclerView)
        val addPlant = view.findViewById<Button>(R.id.addPlantbtn)
        val empty_text = view.findViewById<TextView>(R.id.empty_text)
        addPlant.setOnClickListener(View.OnClickListener {
            val viewPager2: ViewPager2 = requireActivity().findViewById(R.id.viewPager2)
            viewPager2.setCurrentItem(1)
        })

        addPlant.visibility = View.GONE
        empty_text.visibility = View.GONE

        recyclerView.layoutManager = GridLayoutManager(context, 2)

        plantlistAdapter.setClickListener(clickListener)
        //plantlistAdapter.setLongClickListener(longClickListener)

        recyclerView.adapter = plantlistAdapter

        mainActivityViewModel.getLocalPlant()
            .observe(requireActivity(), Observer<List<Plant>> { data ->
                Logger.v(data.toString())
                plantlistAdapter.setData(data)
                plantlistAdapter.notifyItemChanged()

            })

        return view
    }

}