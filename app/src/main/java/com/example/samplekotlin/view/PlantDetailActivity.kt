package com.example.samplekotlin.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.samplekotlin.R
import com.example.samplekotlin.database.PlantDatabase
import com.example.samplekotlin.util.ExecutorInterface
import com.example.samplekotlin.util.Util
import com.example.samplekotlin.model.Plant
import com.orhanobut.logger.Logger


class PlantDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)

        val plantImg: ImageView = findViewById(R.id.plantImage)
        val intent: Intent = getIntent()
        val plant: Plant
        val likedData = intent.getSerializableExtra("likedData") as List<Plant>

        //todo : 아래와 같은 궁금점이 생겼다.
        // 1. 컴파일 시간에 타입 정보를 명시하는게 더 이득 아닌가? 그러면 런타임에서는 더욱 안전하게 원하는 타입의 객체를 사용할 수 있으니까 말이다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // 티라미수 버전(33)이상 부터는 T::class.java 를 통해 T 클래스의 타입을 명시적으로 지정해야 한다. 이렇게 하면 실행시간에 타입 정보를 사용해서 캐스팅을 수행하므로,
            //T가 컴파일 시간에 알려지지 않아도 된다. 그래서 이 방식이 좀 더 유연한 타입 추론을 제공하고 컴파일 오류의 가능성이 적다.
            plant = intent.getSerializableExtra("plantObject", Plant::class.java)!!
        } else {
            // as T 로 형변환을 시도하며 T 가 nullable한 타입이기 때문에 T?로 캐스팅하려는 것이다. 이 경우 T 는 컴파일 시간에 알려진 타입이어야 하며, 이 코드에서 T가 어떤 타입인지
            //알 수 없는 상황에서는 사용할 수 없다. 따라서 컴파일 오류가 발생할 가능성이 있다.
            plant = (intent.getSerializableExtra("plantObject") as Plant?)!!
        }

        Logger.v("Check")
        Logger.v(likedData.toString())
        Logger.v(plant.toString())

        val plantName: String = plant.name
        val imgURL: String = plant.imageResource
        var wateringNeeds: Int = plant.waterPeriod

        val planttNameTextView: TextView = findViewById(R.id.fuitName)
        val wateringNeedsTextView: TextView = findViewById(R.id.wateringNeeds)

        val backBtn: ImageView = findViewById(R.id.backBtn)
        backBtn.setImageResource(R.drawable.baseline_arrow_back)

        val shareBtn: ImageView = findViewById(R.id.shareBtn)
        shareBtn.setImageResource(R.drawable.baseline_share_15)

        val addBtn: ImageView = findViewById(R.id.addGardenBtn)
        addBtn.setImageResource(R.drawable.baseline_add_24)

        // 인터페이스의 추상메소드를 사용해서 익명객체를 만들어서 사용했다.
        val insertDb = object : ExecutorInterface {
            override fun executerAsync(task: () -> Unit) {
                task.invoke()
            }
        }

        Logger.v(likedData.size.toString())
        for(i: Int in 0..likedData.size-1){
            if(likedData.get(i).imageResource == plant.imageResource){
                addBtn.visibility = View.GONE
            }
        }

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

            // 인터페이스의 추상메소드를 사용해서 익명객체를 만들어서 사용했다.
            insertDb.executeAsync {
                val dbInstance = PlantDatabase.getInstance(baseContext)
                dbInstance!!.plantDao().insertPlant(plant)
            }

            val toast = Util(baseContext)
            toast.makeToastMessage(resources.getString(R.string.add_garden))

            Util.objectUtil.makeToastMessage(baseContext, resources.getString(R.string.add_garden))
            Util.makeToastMessage(baseContext, resources.getString(R.string.add_garden))

            addBtn.visibility = View.GONE
        })


        backBtn.setOnClickListener(View.OnClickListener() {
            if(addBtn.visibility == View.GONE){
                val resultIntent = Intent()
                Logger.v("plant "+plant.imageResource)
                resultIntent.putExtra("likedPlant", plant)
                setResult(RESULT_OK, resultIntent)
            }

            finish()
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


        // 0일마다 물을 줘도 되는 경우를 막기 위해서 +1 시킴
        wateringNeeds++
        Glide.with(this).load(imgURL).into(plantImg)
        planttNameTextView.setText(plantName)
        wateringNeedsTextView.setText("every ${wateringNeeds.toString()} days")

    }
}