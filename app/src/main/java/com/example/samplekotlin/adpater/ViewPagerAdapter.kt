package com.example.samplekotlin.adpater

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.samplekotlin.view.MyGardenFragement
import com.example.samplekotlin.view.PlantListFragment
import java.lang.IllegalArgumentException

class ViewPagerAdapter(fragmentActivity: AppCompatActivity) :
    FragmentStateAdapter(fragmentActivity) {
    // 이 두개의 fragment를 멤버변수로 사용한 이유는 액티비티에서 프레그먼트로 데이터를 보내야 하는 상황을 대비해서다.
    val myGardenFragement = MyGardenFragement()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> myGardenFragement
            1 -> PlantListFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

}