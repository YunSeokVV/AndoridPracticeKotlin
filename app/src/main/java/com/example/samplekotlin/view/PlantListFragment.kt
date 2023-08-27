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
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.PlantListAdapter
import com.example.samplekotlin.network.PlantApiService
import com.example.samplekotlin.vo.Plant
import com.example.samplekotlin.vo.URLs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlantListFragment : Fragment() {
    private lateinit var plantlistAdapter : PlantListAdapter
    private lateinit var recyclerView: RecyclerView

    private var data = mutableListOf<Plant>()
    private var hidePlants = mutableListOf<Plant>()

    private var filterAll = true;

    val plantNames = arrayOf("Apple", "Avocado", "Beet", "Bougainvillea", "Cilantro", "EggPlant", "Grape", "Hibiscus", "Mango", "Orange", "Pear", "Pink & White", "Rocky Mountain", "Sunflower", "Tomato", "Watermelon", "Yulan Magnolia")
    //val plantImages = arrayOf("apple", "avocado", "beet", "bougainvillea", "cilantro", "eggplant", "grape", "hibiscus", "mango", "orange", "pear", "pink_white", "rockymountian", "sunflower", "tomato", "watermelon", "yulanmagnolia")
    // 각 꽃의 고유 아이디들(unSplash 사이트에서 확인가능! 마지막 목련은 그냥 id값으로 줬다. 구할 수 없었기 때문)
    val plantImagesID = arrayOf("oo3kSFZ7uHk", "cueV_oTVsic", "TmjyLCUpcDY", "CdkGk6l4eCM", "FVikKhvhnKE", "8cqlBGw84oU", "vHnQqO3TT4c", "yFmwdScAUS8", "2vq33LK8bZA", "A4BBdJQu2co", "cfxnOUSLrgk", "fxbzT96KzOo", "7CHMu7uPHCQ", "2IzoIHBgYAo", "5Oi5sG6G0z8", "feJOQIBao", "pDGNBK9A0sk")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_plant_list, container,false)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        for(i : Int in 0..16){
            imgURL(plantImagesID[i], i)
        }

        // 자바에서 레이아웃 매너저를 설정하는 방식과 다르다.
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        return view
    }

    fun imgURL(plnatURL : String, count : Int) {
        val retrofit = Retrofit.Builder().baseUrl("https://api.unsplash.com/").addConverterFactory(GsonConverterFactory.create()).build()
        val apiService : PlantApiService = retrofit.create(PlantApiService::class.java)
        val repos : Call<URLs> = apiService.plantImage(plnatURL)

        repos.enqueue(object : Callback<URLs> {
            override fun onResponse(call: Call<URLs>, response: Response<URLs>) {
                if(response.isSuccessful()) {

                    var plantVO = Plant(count.toLong(), plantNames[count], count, response.body()?.urls!!.get("raw").toString())
                    data.add(plantVO)

                    if(data.size == 16){

                        plantlistAdapter = PlantListAdapter(data)
                        recyclerView.adapter = plantlistAdapter

                        plantlistAdapter.setOnItemClickListener(object : PlantListAdapter.OnItemClickListener {
                            override fun onItemClick(data : Plant) {
                                val intent : Intent = Intent(context, PlantDetailActivity::class.java)
//                                intent.putExtra("plantName", data.get(position).name)
//                                intent.putExtra("imgURL", data.get(position).imageResource)
//                                intent.putExtra("wateringNeeds", data.get(position).waterPeriod)
                                //intent.putExtra("plantObject", data.get(position))
                                intent.putExtra("plantObject", data)
                                startActivity(intent)
                            }
                        })
                    }

                } else { // code == 400

                }

            }

            override fun onFailure(call: Call<URLs>, t: Throwable) {

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id : Int = item.itemId
        if(id == R.id.settings){
            filterPlants(!filterAll)
            filterAll = !filterAll
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun filterPlants(filter : Boolean){

        // 모든 식물 목록을 보여준다.
        if(filter){
            for(i : Int in 0..11){
                data.add(hidePlants.get(i))
            }
            hidePlants.clear()
        }
        // 필터링된 4개의 식물만 보여준다.
        else{
            for(i in data.size - 1 downTo 4){
                hidePlants.add(data.get(i))
            }

            for (i in data.size - 1 downTo 4) {
                data.removeAt(i)
            }
        }


        plantlistAdapter.notifyDataSetChanged()
    }

}