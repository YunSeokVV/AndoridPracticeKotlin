package com.example.samplekotlin.adpater.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.app_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val tableLayout: TabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager2: ViewPager2 = findViewById(R.id.viewPager2)

        viewPager2.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tableLayout, viewPager2) {tab,position ->
            tab.text = when (position) {
                0 -> "My garden"
                1 -> "Plant list"
                else -> throw IllegalArgumentException("Invalid position")
            }

            // 왜인지는 모르겠으나 아래 코드는 안먹혀서 따로 아이콘에 대한 설정을 따로 해줬다.
//            tab.icon = when(position) {
//                0 -> resources.getDrawable(R.drawable.ic_my_garden_active)
//                1 -> resources.getDrawable(R.drawable.ic_plant_list_active)
//                else -> throw IllegalArgumentException("Invalid position")
//            }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // 메뉴 리소스 파일을 인플레이트하여 액션바 또는 툴바에 메뉴를 추가
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 메뉴 아이템 선택 시 동작을 처리
        return when (item.itemId) {
            R.id.settings -> {
                // 'Settings' 메뉴 아이템 선택 시 동작을 정의
                // 예를 들어, 설정 화면으로 이동하거나 다른 작업 수행
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}