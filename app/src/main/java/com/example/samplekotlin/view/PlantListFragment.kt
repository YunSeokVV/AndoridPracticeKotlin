package com.example.samplekotlin.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplekotlin.BuildConfig
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.PlantListAdapter
import com.example.samplekotlin.network.NetworkProvider
import com.example.samplekotlin.network.PlantApiService
import com.example.samplekotlin.vo.Plant
import com.example.samplekotlin.vo.UnsplashResults
import com.orhanobut.logger.Logger

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantListFragment : Fragment() {
    val listener by lazy {
        (object : PlantListAdapter.OnItemClickListener {
            override fun onItemClick(data: Plant) {
                val intent: Intent = Intent(context, PlantDetailActivity::class.java)
                intent.putExtra("plantObject", data)
                startActivity(intent)
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

    val plantNames = arrayOf(
        "Apple",
        "Avocado",
        "Beet",
        "Bougainvillea",
        "Cilantro",
        "EggPlant",
        "Grape",
        "Hibiscus",
        "Mango",
        "Orange",
        "Pear",
        "Pink & White",
        "Rocky Mountain",
        "Sunflower",
        "Tomato",
        "Watermelon",
        "Yulan Magnolia"
    )

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

        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.plantListrecyclerView)

        for (i: Int in 0..16) {
            imgURL(plantNames[i], i, recyclerView)
        }


        // 자바에서 레이아웃 매너저를 설정하는 방식과 다르다.
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        return view
    }

    fun imgURL(plantURL: String, count: Int, recyclerVeiw: RecyclerView) {
        //plantApiService = NetworkProvider.provideApi()
        val result = plantApiService.plantImage(plantURL, BuildConfig.UNSPLASH_KEY)
        Logger.v("result ${result.request().url().toString()}")
        result.enqueue(object : Callback<UnsplashResults> {
            override fun onResponse(
                call: Call<UnsplashResults>,
                response: Response<UnsplashResults>
            ) {
                Logger.v("count $count")
                //Logger.v("response.body() ${response.body()?.results?.get(count)?.urls}")
                println(count.toLong())
                println(plantNames[count])

                Logger.v("result {${response.body()?.results?.size}")
                //Error here!! todo : 왜 count가 10맘ㄴ되면 앱이 죽지?
                Logger.v("Check " + response.body()?.results?.get(0)?.urls?.get("raw").toString())
                var plantVO = Plant(
                    count.toLong(),
                    plantNames[count],
                    count,
                    response.body()?.results?.get(0)?.urls?.get("raw").toString()
                )
                data.add(plantVO)

                if (data.size == 16) {
                    //plantlistAdapter.submitList(data)
                    //listener
                    plantlistAdapter.setClickListener(listener)
                    val recyclerView = recyclerVeiw
                    recyclerView.adapter = plantlistAdapter

                }

            }

            override fun onFailure(call: Call<UnsplashResults>, t: Throwable) {
                Logger.e("onFailure called ${t.stackTraceToString()}")
            }

        })
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