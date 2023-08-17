package com.example.samplekotlin.adpater

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.samplekotlin.view.MyGardenFragement
import com.example.samplekotlin.view.PlantListFragment
import java.lang.IllegalArgumentException

class ViewPagerAdapter(fragmentActivity: AppCompatActivity) :
    FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> MyGardenFragement()
            1-> PlantListFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }


}