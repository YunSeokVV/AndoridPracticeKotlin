package com.example.samplekotlin.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.samplekotlin.R
import com.example.samplekotlin.vo.Plant
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class PlantDetailActivity : AppCompatActivity() {
    lateinit var plantImg : ImageView
    val executor : Executor = Executors.newSingleThreadExecutor()
    //val plantDao : PlantDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)

        val intent : Intent = getIntent()
        val plant = intent.getSerializableExtra("plantObject") as Plant?
        val plantName : String = plant?.name.toString()
        val imgURL : String = plant?.imageResource.toString()
        var wateringNeeds : Int = plant?.waterPeriod!!

        //plantDao = Data

        plantImg = findViewById(R.id.plantImage)
        val planttNameTextView : TextView = findViewById(R.id.fuitName)
        val wateringNeedsTextView : TextView = findViewById(R.id.wateringNeeds)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        backBtn.setImageResource(R.drawable.baseline_arrow_back)

        val shareBtn : ImageView = findViewById(R.id.shareBtn)
        shareBtn.setImageResource(R.drawable.baseline_share_15)

        val addBtn : ImageView =findViewById(R.id.addGardenBtn)
        addBtn.setImageResource(R.drawable.baseline_add_24)

        addBtn.setOnClickListener(View.OnClickListener {
            // 자신이 좋아하는 식물목록에 추가하면 DB에 저장하게끔 동작한다. main스레드에서 DB관련 코드를 적용하면 안되기 때문에 executor를 사용했다.
            executor.execute {

            }
        })

        backBtn.setOnClickListener(View.OnClickListener() {
            finish()
        })

        shareBtn.setOnClickListener(View.OnClickListener {
            val intent : Intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            val sendMessage :String = "Check out the Apple plant in the Android Sunflower app";
            intent.putExtra(Intent.EXTRA_TEXT, sendMessage)
            val shareIntent = Intent.createChooser(intent, "share")
            startActivity(shareIntent)
        })


        // 0일마다 물을 줘도 되는 경우를 막기 위해서 +1 시킴
        wateringNeeds++
        Glide.with(this).load(imgURL).into(plantImg)
        planttNameTextView.setText(plantName)
        wateringNeedsTextView.setText("every ${wateringNeeds.toString()} days")

    }
}