package com.example.samplekotlin.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplekotlin.BuildConfig
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.PlantListAdapter
import com.example.samplekotlin.adpater.view.MainActivity
import com.example.samplekotlin.network.NetworkProvider
import com.example.samplekotlin.network.PlantApiService
import com.example.samplekotlin.util.SendPlantListener
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.model.UnsplashResults
import com.example.samplekotlin.viewmodel.PlantDetailFragmentViewModel
import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlantListFragment : Fragment() {
    val listener by lazy {
        (object : PlantListAdapter.OnItemClickListener {
            override fun onItemClick(data: Plant) {

                val viewModel: PlantDetailFragmentViewModel =
                    ViewModelProvider(requireActivity()).get(PlantDetailFragmentViewModel::class.java)
                viewModel.setPlant(data)
                viewModel.setLikeData(ArrayList((activity as MainActivity).getLikedPlants()))

                val transaction = parentFragmentManager.beginTransaction()
                val plantDetailFragment = PlantDetailFragment()
                transaction.replace(R.id.fragmentContainer, plantDetailFragment)

                transaction.commit()

            }
        })
    }


    private val data = mutableListOf<Plant>()

    private val plantlistAdapter: PlantListAdapter by lazy {
        PlantListAdapter(data)
    }
    private val hidePlants = mutableListOf<Plant>()

    private var filterAll = true;

    //private lateinit var plantApiService: PlantApiService
    private val plantApiService: PlantApiService = NetworkProvider.provideApi()

    lateinit var sendPlantListener: SendPlantListener

    override fun onDestroy() {
        super.onDestroy()
        Logger.v("onDestroy called")
    }

    override fun onStop() {
        super.onStop()
        Logger.v("onStop called")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        sendPlantListener = context as SendPlantListener
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

        val result = plantApiService.plantImage("Apple", BuildConfig.UNSPLASH_KEY)
        Logger.v("result ${result.request().url().toString()}")
        result.enqueue(object : Callback<UnsplashResults> {
            override fun onResponse(
                call: Call<UnsplashResults>,
                response: Response<UnsplashResults>
            ) {

                val resultList = response.body()?.results
                resultList?.forEachIndexed { index, element ->
                    var plantVO = Plant(
                        index.toLong(),
                        "Apple",
                        index,
                        response.body()?.results?.get(index)?.urls?.get("raw").toString()
                    )
                    data.add(plantVO)
                }

                plantlistAdapter.setClickListener(listener)
                recyclerView.adapter = plantlistAdapter
            }

            override fun onFailure(call: Call<UnsplashResults>, t: Throwable) {
                Logger.e("onFailure called ${t.stackTraceToString()}")
            }

        })



        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.settings) {
            filterPlants(!filterAll)
            filterAll = !filterAll
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun filterPlants(filter: Boolean) {

        // 모든 식물 목록을 보여준다.
        if (filter) {
            for (i: Int in 0..11) {
                data.add(hidePlants.get(i))
            }
            hidePlants.clear()
        }
        // 필터링된 4개의 식물만 보여준다.
        else {
            for (i in data.size - 1 downTo 4) {
                hidePlants.add(data.get(i))
            }

            for (i in data.size - 1 downTo 4) {
                data.removeAt(i)
            }
        }

        plantlistAdapter.notifyDataSetChanged()
    }

}