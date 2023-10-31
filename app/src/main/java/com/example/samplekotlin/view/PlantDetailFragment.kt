package com.example.samplekotlin.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bumptech.glide.Glide
import com.example.samplekotlin.R
import com.example.samplekotlin.dataSource.local.GetPlantDataSourceImpl
import com.example.samplekotlin.dataSource.local.InsertPlantDataSourceImpl
import com.example.samplekotlin.database.PlantDatabase
import com.example.samplekotlin.model.Plant
import com.example.samplekotlin.repository.GetLocalPlantRepositoryImpl
import com.example.samplekotlin.repository.InsertPlantRepositoryImpl
import com.example.samplekotlin.useCase.GetLocalPlantUseCaseImpl
import com.example.samplekotlin.useCase.InsertPlantUseCaseImpl
import com.example.samplekotlin.util.Util
import com.example.samplekotlin.viewmodel.MainActivityViewModel
import com.example.samplekotlin.viewmodel.PlantDetailFragmentViewModel
import com.orhanobut.logger.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class PlantDetailFragment : Fragment() {
    val mainActivityViewModel: MainActivityViewModel by viewModels<MainActivityViewModel> {
        viewModelFactory {
            initializer {
                MainActivityViewModel(
                    GetLocalPlantUseCaseImpl(
                        GetLocalPlantRepositoryImpl(
                            GetPlantDataSourceImpl(
                                PlantDatabase.getInstance(requireActivity().applicationContext).plantDao()
                            )
                        )
                    )
                )
            }
        }
    }

    val viewModel : PlantDetailFragmentViewModel by viewModels {
        viewModelFactory {
            initializer {
                PlantDetailFragmentViewModel(
                    InsertPlantUseCaseImpl(
                        InsertPlantRepositoryImpl(
                            InsertPlantDataSourceImpl(
                                PlantDatabase.getInstance(requireContext()).plantDao()
                            )
                        )
                    )
                )
            }
        }
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

        val bundle: Bundle = requireArguments()
        bundle.getSerializable("plant")
        val plant: Plant = bundle.getSerializable("plant") as Plant

        mainActivityViewModel.getLocalPlant().observe(requireActivity(), Observer { data ->
            data.forEach {
                planttNameTextView.text = plant?.name
                Glide.with(this).load(plant?.imageResource).into(plantImg)
                var waterPeriod = plant?.waterPeriod
                wateringNeedsTextView.text = "every ${waterPeriod} days"
                addBtn.visibility = mainActivityViewModel.likedPlant(plant.imageResource)
            }
        })

        planttNameTextView.text = plant?.name
        Glide.with(this).load(plant?.imageResource).into(plantImg)
        var waterPeriod = plant?.waterPeriod
        wateringNeedsTextView.text = "every ${waterPeriod} days"

        addBtn.setOnClickListener(View.OnClickListener {
            viewModel.insertPlant(plant)
            Util.makeToastMessage(requireContext(), resources.getString(R.string.add_garden))
            addBtn.visibility = View.GONE

            mainActivityViewModel.loadLocalPlant()
        })


        backBtn.setOnClickListener(View.OnClickListener() {
            if (addBtn.visibility == View.GONE) {
//                val resultIntent = Intent()
//                Logger.v("plant "+plant.imageResource)
//                resultIntent.putExtra("likedPlant", plant)
//                setResult(RESULT_OK, resultIntent)
            }


            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.popBackStack()
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