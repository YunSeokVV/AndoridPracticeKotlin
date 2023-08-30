package com.example.samplekotlin.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplekotlin.R
import com.example.samplekotlin.vo.Plant

class PlantListAdapter (private val data: List<Plant>) : RecyclerView.Adapter<PlantListAdapter.ViewHolder>(){

    private  lateinit var listener : OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(plant : Plant)
    }

    fun setOnItemClickListener(onItemClickListener : OnItemClickListener){
        listener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LayoutInflater.from(parent.context)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.plant_item_list,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){

        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantName: TextView = itemView.findViewById(R.id.plantName)
        val plantImage: ImageView = itemView.findViewById<ImageView>(R.id.palntImage)

        fun bind(item : Plant){
            plantName.text = item.name
            Glide.with(itemView.context).load(item.imageResource).into(plantImage)
            // 이미지뷰의 모서리를 약간 둥글게 만든다.
            plantImage.clipToOutline = true
        }

        init {
            itemView.setOnClickListener{
                listener.onItemClick(data.get(adapterPosition))
            }
        }

    }

}