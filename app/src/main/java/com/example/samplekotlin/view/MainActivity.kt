package com.example.samplekotlin.adpater.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class MainActivity : AppCompatActivity() {
    private var filterVisible: Boolean = false
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
}