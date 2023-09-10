package com.example.samplekotlin.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.PlantListAdapter
import com.example.samplekotlin.database.PlantDatabase
import com.example.samplekotlin.util.ExecutorInterface
import com.example.samplekotlin.util.MainActivityCallback
import com.example.samplekotlin.util.SingleExecute
import com.example.samplekotlin.util.SingleExecutor
import com.example.samplekotlin.vo.Plant
import com.orhanobut.logger.Logger
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MyGardenFragement : Fragment() {

    companion object {
        fun newInstance(containerId : Int): MyGardenFragement {
            val fragment = MyGardenFragement()
            val args = Bundle()
            args.putInt("containerId", containerId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val listener by lazy {
        object : PlantListAdapter.OnItemClickListener {
            override fun onItemClick(data: Plant) {
                val intent: Intent = Intent(context, PlantDetailActivity::class.java)
                intent.putExtra("plantObject", data)
                //startActivity(intent)
                resultLauncher.launch(intent)
                //resultLauncher.launch(intent)
            }
        }
    }
    val executor: Executor = Executors.newSingleThreadExecutor()
    private val data = mutableListOf<Plant>()
    private val plantlistAdapter: PlantListAdapter by lazy {
        PlantListAdapter(data)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val containerId = arguments?.getInt("containerId", -1)
        // 이 시점부터 container id 를 갖게 된거다. 쓰고 싶은 대로 쓰면 된다.

        val view = inflater.inflate(R.layout.fragment_my_garden_fragement, container, false)
        val addPlant = view.findViewById<Button>(R.id.addPlantbtn)
        val empty_text = view.findViewById<TextView>(R.id.empty_text)
        addPlant.setOnClickListener(View.OnClickListener {
            val viewPager2: ViewPager2 = requireActivity().findViewById(R.id.viewPager2)
            viewPager2.setCurrentItem(1)
        })

        //todo : 익명객체를 사용해서 람다를 사용하려고 시도했으나 실패했다. 메인스레드에서 DB관련 작업을 하면 안된다는 식의 오류였는데 왜 그런 오류가 발생했는지 이해가 안된다.
//        val executorInterface : ExecutorInterface = object : ExecutorInterface {
//            override fun executeApi(task: () -> Unit) {
//                task.invoke()
//            }
//        }
//        executorInterface.executeApi {
//            val recyclerView: RecyclerView =
//                view.findViewById<RecyclerView>(R.id.myPlantListrecyclerView)
//            val dbInstance = PlantDatabase.getInstance(requireContext())
//            val plantList: List<Plant> = dbInstance!!.plantDao().getAll()
//            if (plantList.size > 0) {
//                for (plant in plantList) {
//                    data.add(plant)
//                    Logger.v("plant " + plant.name)
//                    Logger.v("imageResource " + plant.imageResource)
//                }
//                // 메인 스레드가 아닌 곳에서 ui와 관련된 작업을 할 수 없다.
//                requireActivity().runOnUiThread {
//                    addPlant.visibility = View.GONE
//                    empty_text.visibility = View.GONE
//                    recyclerView.adapter = plantlistAdapter
//                    plantlistAdapter.setClickListener(listener)
//                    recyclerView.layoutManager = GridLayoutManager(context, 2)
//                }
//            }
//        }

        val singleExecutor : ExecutorInterface = SingleExecute()
        singleExecutor.executeApi {
            val recyclerView: RecyclerView =
                view.findViewById<RecyclerView>(R.id.myPlantListrecyclerView)
            val dbInstance = PlantDatabase.getInstance(requireContext())
            val plantList: List<Plant> = dbInstance!!.plantDao().getAll()
            if (plantList.size > 0) {
                for (plant in plantList) {
                    data.add(plant)
                    Logger.v("plant " + plant.name)
                    Logger.v("imageResource " + plant.imageResource)
                }
                // 메인 스레드가 아닌 곳에서 ui와 관련된 작업을 할 수 없다.
                requireActivity().runOnUiThread {
                    addPlant.visibility = View.GONE
                    empty_text.visibility = View.GONE
                    recyclerView.adapter = plantlistAdapter
                    plantlistAdapter.setClickListener(listener)
                    recyclerView.layoutManager = GridLayoutManager(context, 2)
                }
            }
        }

        resultLauncher = registerForActivityResult(
            //ActivityResultCallback은 람다로 사용하고 ActivityResult 객체가 파라미터로 떨어지고 여기서 원하는 데이터를 가져오면 된다.
            ActivityResultContracts.StartActivityForResult()){ result ->
            // 서브 액티비티로부터 돌아올 때의 결과 값을 받아 올 수 있는 구문
            if (result.resultCode == RESULT_OK){
                println("Check here!!")
                val name = result.data?.getStringExtra("name") ?: ""
                val age = result.data?.getStringExtra("age") ?: ""
                Logger.v("name {$name}")
                Logger.v("age {$age}")

                val mainActivityCallback = context as MainActivityCallback
                mainActivityCallback.callBackMethod(name)
            }

        }

        return view
    }

    fun testFun(){
        Logger.v("테스트용 메소드")
    }
}