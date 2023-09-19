package com.example.samplekotlin.view


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.PlantListAdapter
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.viewmodel.MyGardenFragmentViewModel
import com.example.samplekotlin.viewmodel.MyGardenFramgnetViewModelFactory
import com.orhanobut.logger.Logger


class MyGardenFragement : Fragment() {
    private val recyclerView by lazy { activity?.findViewById<RecyclerView>(R.id.myPlantListrecyclerView) }
    val likedData = mutableListOf<Plant>()
    private val listener by lazy {
        (object : PlantListAdapter.OnItemClickListener {
            override fun onItemClick(data: Plant) {

                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, PlantDetailFragment())
                transaction.addToBackStack(null)
                transaction.commit()

            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.v("onCreate called")
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.v("onDestroy")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_garden_fragement, container, false)
        val addPlant = view.findViewById<Button>(R.id.addPlantbtn)
        val empty_text = view.findViewById<TextView>(R.id.empty_text)
        addPlant.setOnClickListener(View.OnClickListener {
            val viewPager2: ViewPager2 = requireActivity().findViewById(R.id.viewPager2)
            viewPager2.setCurrentItem(1)
        })

        //recyclerView?.adapter = plantlistAdapter
        //plantlistAdapter.setClickListener(listener)

        addPlant.visibility = View.GONE
        empty_text.visibility = View.GONE

        //viewmodel 생성시 viewmodel factory 사용하기
        val viewModelFactory =
            MyGardenFramgnetViewModelFactory(requireActivity().applicationContext)
        val myGardenFragmentViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(MyGardenFragmentViewModel::class.java)
        myGardenFragmentViewModel.allPlants
            .observe(viewLifecycleOwner, Observer<List<Plant>> { list ->
                Logger.v("reload datas")
                Logger.v(list.size.toString())


//                list?.let {
//                    Logger.v("reload datas")
//
//                    //todo : 지난주에 멘토님이 가급적이면 리사이클러뷰 초기화를 코드의 상단 부분에서 해라고 말씀해주셨는데, 리스트 데이터를 받기 전까지는 초기화를
//                    // 할 방법이 없기 때문에 아래와 같이 초기화를 할 수 밖에 없다.
//                    val adapter = PlantListAdapter(it)
//                    recyclerView?.adapter = adapter
//                    recyclerView?.adapter = PlantListAdapter(it)
//                    recyclerView?.layoutManager = GridLayoutManager(context, 2)
//                    adapter.notifyItemChanged(it)
//                    adapter.setClickListener(listener)
//                }
            })

        return view
    }

    fun addLikedItem(plant: Plant) {
        likedData.add(plant)
        Logger.v(plant.imageResource)
        recyclerView?.adapter?.notifyItemInserted(likedData.size)
    }

}