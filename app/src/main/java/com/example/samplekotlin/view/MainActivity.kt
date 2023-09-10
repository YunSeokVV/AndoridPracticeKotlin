package com.example.samplekotlin.adpater.view


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.ViewPagerAdapter
import com.example.samplekotlin.util.MainActivityCallback
import com.example.samplekotlin.view.MyGardenFragement
import com.example.samplekotlin.view.PlantListFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class MainActivity : MainActivityCallback, AppCompatActivity(){
    private var filterVisible: Boolean = false
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.app_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        Logger.addLogAdapter(AndroidLogAdapter())

        Logger.v("Test log here")

        val tableLayout: TabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager2: ViewPager2 = findViewById(R.id.viewPager2)

        setResultNext()

//        val fragmentManager: FragmentManager = supportFragmentManager
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.plantListContainer, PlantListFragment());
//        fragmentTransaction.commit();
//        fragmentTransaction.replace(R.id.myGardenContainer, MyGardenFragement());
//        fragmentTransaction.commit();

        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, PlantListFragment())
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, MyGardenFragement()).commit()


        viewPager2.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tableLayout, viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> "My garden"
                1 -> "Plant list"
                else -> throw IllegalArgumentException("Invalid position")
            }

        }.attach()

        tableLayout.getTabAt(0)?.setIcon(R.drawable.ic_my_garden_active)
        tableLayout.getTabAt(1)?.setIcon(R.drawable.ic_plant_list_active)

        // 선택된 탭레이아웃의 글자 색상을 설정해준다.
        tableLayout.setTabTextColors(
            //un selected Tab
            ContextCompat.getColor(this, R.color.unSelectedTabTextColor),
            //selected Tab
            ContextCompat.getColor(this, R.color.yellow)
        )

    }

    // onCreateOptionMenu 는 화면이 바뀔때 뷰가 새로 바뀌는 기회를 제공하기 위해서 계속 호출된다.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // 메뉴 리소스 파일을 인플레이트하여 액션바 또는 툴바에 메뉴를 추가
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        val menuItem: MenuItem = menu.findItem(R.id.settings)

        menuItem.setVisible(filterVisible)
        filterVisible = !filterVisible
        return false
    }

    private fun setResultNext(){
        resultLauncher = registerForActivityResult(

            //ActivityResultCallback은 람다로 사용하고 ActivityResult 객체가 파라미터로 떨어지고 여기서 원하는 데이터를 가져오면 된다.
            ActivityResultContracts.StartActivityForResult()){ result ->
            // 서브 액티비티로부터 돌아올 때의 결과 값을 받아 올 수 있는 구문
            if (result.resultCode == RESULT_OK){
                Logger.v("result.resultCode == RESULT_OK")
                val name = result.data?.getStringExtra("name") ?: ""
                val age = result.data?.getStringExtra("age") ?: ""

                Logger.v("name ${name}")
                Logger.v("age ${age}")
            }
        }
    }

    override fun callBackMethod(name : String) {
//        val intent = Intent(this, MyGardenFragement::class.java)
//        resultLauncher.launch(intent)

        Logger.v("name {$name}")
        //val myGardenFragement = MyGardenFragement()
        //val myGardenFragement = supportFragmentManager.findFragmentById(R.id.my_garden_container) as MyGardenFragement
        val myGardenFragement2: MyGardenFragement = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as MyGardenFragement
        //val fragment: MyGardenFragement = fragmentManager.findFragmentById(R.id.my_garden_container) as MyGardenFragement
        myGardenFragement2.testFun()
        //fragment.testFun()
        //tf.testFunction()
    }
}