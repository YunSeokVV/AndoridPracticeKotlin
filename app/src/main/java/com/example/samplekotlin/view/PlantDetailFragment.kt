package com.example.samplekotlin.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.samplekotlin.R
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.util.ExecutorInterface
import com.example.samplekotlin.util.Util
import com.example.samplekotlin.viewmodel.MyGardenFragmentViewModel
import com.example.samplekotlin.viewmodel.MyGardenFramgnetViewModelFactory
import com.example.samplekotlin.viewmodel.PlantDetailFragmentViewModel
import com.orhanobut.logger.Logger

class PlantDetailFragment : Fragment() {
    lateinit var plant: Plant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plant_detail, container, false)
        val plantImg: ImageView = view.findViewById(R.id.plantImage)
        val planttNameTextView: TextView = view.findViewById(R.id.fuitName)
        val wateringNeedsTextView: TextView = view.findViewById(R.id.wateringNeeds)

        val backBtn: ImageView = view.findViewById(R.id.backBtn)
        backBtn.setImageResource(R.drawable.baseline_arrow_back)

        val shareBtn: ImageView = view.findViewById(R.id.shareBtn)
        shareBtn.setImageResource(R.drawable.baseline_share_15)

        val addBtn: ImageView = view.findViewById(R.id.addGardenBtn)
        addBtn.setImageResource(R.drawable.baseline_add_24)

        val viewModel: PlantDetailFragmentViewModel =
            ViewModelProvider(requireActivity()).get(PlantDetailFragmentViewModel::class.java)
        viewModel.plant.observe(viewLifecycleOwner, Observer {
            planttNameTextView.text = it.name
            Glide.with(this).load(it.imageResource).into(plantImg)
            var waterPeriod = it.waterPeriod
            waterPeriod++
            wateringNeedsTextView.text = "every ${waterPeriod} days"
            plant = it
        })

        addBtn.setOnClickListener(View.OnClickListener {
            // 기존에 DB에 데이터를 추가했었던 방식이다. executor 만 사용했지 람다는 적용되어있지 않다.
//            val loadDataExecutor = SingleExecutor()
//            loadDataExecutor.executeTask {
//                val dbInstance = PlantDatabase.getInstance(baseContext)
//                dbInstance!!.plantDao().insertPlant(plant)
//            }

            // 인터페이스에 디폴트 메소드를 만든다음 디폴트메소드를 구현하는 객체를 만든뒤 그 객체를 사용해서 DB 관련 동작을 수행했다.
//            val executor : ExecutorInterface = SingleExecutorLauncher()
//            executor.executerAsync{
//                val dbInstance = PlantDatabase.getInstance(baseContext)
//                dbInstance!!.plantDao().insertPlant(plant)
//            }

//            // 익명객체를 만든뒤 인터페이스의 디폴트메소드를 사용해싿.
//            insertDb.executeAsync {
//                val dbInstance = PlantDatabase.getInstance(baseContext)
//                dbInstance!!.plantDao().insertPlant(plant)
//                Logger.v("data inserted!!")
//                if(Looper.myLooper() == Looper.getMainLooper()){
//                    Logger.v("mainlooper")
//                }
//                else{
//                    Logger.v("sublooper")
//                }
//            }


            val r = Runnable {
                val viewModelFactory =
                    MyGardenFramgnetViewModelFactory(requireActivity().applicationContext)
                val myGardenFragmentViewModel =
                    ViewModelProvider(requireActivity(), viewModelFactory).get(
                        MyGardenFragmentViewModel::class.java
                    )
                myGardenFragmentViewModel.addPlant(plant)
            }

            val thread = Thread(r)
            thread.start()

            Util.makeToastMessage(requireContext(), resources.getString(R.string.add_garden))

            addBtn.visibility = View.GONE
        })


        backBtn.setOnClickListener(View.OnClickListener() {
            if (addBtn.visibility == View.GONE) {
//                val resultIntent = Intent()
//                Logger.v("plant "+plant.imageResource)
//                resultIntent.putExtra("likedPlant", plant)
//                setResult(RESULT_OK, resultIntent)
            }

            activity?.finish()
        })

        shareBtn.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(Intent.ACTION_SEND)
            // 문자열 데이터를 전송할것을 명시
            intent.setType("text/plain")
            val sendMessage: String = "Check out the Apple plant in the Android Sunflower app";
            // 문자데이터를 보낸다.
            intent.putExtra(Intent.EXTRA_TEXT, sendMessage)
            val shareIntent = Intent.createChooser(intent, "share")
            startActivity(shareIntent)
        })

        return view
    }


}