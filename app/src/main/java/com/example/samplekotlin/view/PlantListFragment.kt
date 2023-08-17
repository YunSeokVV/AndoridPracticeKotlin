package com.example.samplekotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.PlantListAdapter
import com.example.samplekotlin.vo.Plant

class PlantListFragment : Fragment() {
    private lateinit var plantlistAdapter : PlantListAdapter
    private lateinit var recyclerView: RecyclerView

    private var data = mutableListOf<Plant>()

    val plantNames = arrayOf("Apple", "Avocado", "Beet", "Bougainvillea", "Cilantro", "EggPlant", "Grape", "Hibiscus", "Mango", "Orange", "Pear", "Pink & White", "Rocky Mountain", "Sunflower", "Tomato", "Watermelon", "Yulan Magnolia")
    val plantImages = arrayOf("apple", "avocado", "beet", "bougainvillea", "cilantro", "eggplant", "grape", "hibiscus", "mango", "orange", "pear", "pink_white", "rockymountian", "sunflower", "tomato", "watermelon", "yulanmagnolia")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_plant_list, container,false)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)


        //recyclerView.findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.rootView.findViewById<RecyclerView>(R.id.recyclerView)



        // 자바에서 레이아웃 매너저를 설정하는 방식과 다르다.
        recyclerView.layoutManager = GridLayoutManager(context, 2)


//        var plantVO = Plant(0,"asdf",3,R.drawable.apple)
//        data.add(plantVO)
//        plantlistAdapter = PlantListAdapter(data)
//        recyclerView.adapter = plantlistAdapter



        for (i : Int in 0..16){
            //내가 한거
            //val res : Int = context.resources.getIdentifier(plantImages[i], "drawable", context.packageName)

            val res = this.resources.getIdentifier(plantImages[i], "drawable", requireContext().packageName)
            var plantVO = Plant(i.toLong(), plantNames[i], i, res)
            data.add(plantVO)
        }
        plantlistAdapter = PlantListAdapter(data)
        recyclerView.adapter = plantlistAdapter





        return view
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)


    }

}