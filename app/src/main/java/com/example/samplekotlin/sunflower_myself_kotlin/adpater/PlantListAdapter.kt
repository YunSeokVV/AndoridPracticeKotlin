package com.example.sunflower_myself_kotlin.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunflower_myself_kotlin.R
import com.example.sunflower_myself_kotlin.vo.Plant

class PlantListAdapter (private val data: List<Plant>) : RecyclerView.Adapter<PlantListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.plant_item_list,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val plantName:String = data.get(position).name
        val imageResource:Int = data.get(position).imageResource

        holder.plantName.setText(plantName)
        holder.plantImage.setImageResource(imageResource)
        // 이미지뷰의 모서리를 둥글게 해주는 역할
        holder.plantImage.clipToOutline = true
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var plantName: TextView = itemView.findViewById(R.id.plantName);
        var plantImage: ImageView = itemView.findViewById<ImageView>(R.id.palntImage)


    }
}