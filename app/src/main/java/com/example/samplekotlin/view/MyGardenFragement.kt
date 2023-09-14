package com.example.samplekotlin.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.PlantListAdapter
import com.example.samplekotlin.database.PlantDatabase
import com.example.samplekotlin.util.ExecutorInterface
import com.example.samplekotlin.model.Plant
import com.orhanobut.logger.Logger
import java.util.concurrent.Executors

class MyGardenFragement : Fragment() {
    private val recyclerView by lazy { activity?.findViewById<RecyclerView>(R.id.myPlantListrecyclerView) }
    val likedData = mutableListOf<Plant>()
    private val listener by lazy {
        (object : PlantListAdapter.OnItemClickListener {
            override fun onItemClick(data: Plant) {
                val intent: Intent = Intent(context, PlantDetailActivity::class.java)
                intent.putExtra("plantObject", data)
                intent.putExtra("likedData", ArrayList(likedData))
                startActivity(intent)
            }
        })
    }

    private val plantlistAdapter: PlantListAdapter by lazy {
        PlantListAdapter(likedData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.v("onCreate called")
        super.onCreate(savedInstanceState)

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


        val execute = object : ExecutorInterface {
            override fun executerAsync(task: () -> Unit) {
                val executor = Executors.newSingleThreadExecutor()
                executor.execute {
                    task.invoke()
                }
            }
        }

        execute.executerAsync {
            //val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.myPlantListrecyclerView)
            val dbInstance = PlantDatabase.getInstance(requireContext())
            val plantList: List<Plant> = dbInstance!!.plantDao().getAll()
            if (plantList.size > 0) {
                for (plant in plantList) {
                    likedData.add(plant)
                }
                // 메인 스레드가 아닌 곳에서 ui와 관련된 작업을 할 수 없다.
                requireActivity().runOnUiThread {
                    addPlant.visibility = View.GONE
                    empty_text.visibility = View.GONE
                    recyclerView?.adapter = plantlistAdapter
                    plantlistAdapter.setClickListener(listener)
                    recyclerView?.layoutManager = GridLayoutManager(context, 2)
                }
            }
        }

        return view
    }

    fun addLikedItem(plant: Plant) {
        likedData.add(plant)
        Logger.v(plant.imageResource)
        recyclerView?.adapter?.notifyItemInserted(likedData.size)
    }

}