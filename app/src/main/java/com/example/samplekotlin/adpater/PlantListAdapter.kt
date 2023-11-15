package com.example.samplekotlin.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplekotlin.R
import com.example.samplekotlin.model.Plant

class PlantListAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<PlantListAdapter.ViewHolder>() {
    private val plantData = mutableListOf<Plant>()

    interface OnItemClickListener {
        fun onItemClick(plant: Plant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LayoutInflater.from(parent.context)
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.plant_item_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(plantData[position])

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return plantData.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantName: TextView = itemView.findViewById(R.id.plantName)
        val plantImage: ImageView = itemView.findViewById<ImageView>(R.id.palntImage)

        fun bind(item: Plant) {
            plantName.text = item.location
            Glide.with(itemView.context).load(item.imageResource).into(plantImage)
            // 이미지뷰의 모서리를 약간 둥글게 만든다.
            plantImage.clipToOutline = true

        }

        init {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(plantData.get(adapterPosition))
            }

            itemView.setOnLongClickListener {
                plantName.setBackgroundResource(
                    R.drawable.add_btn_yellow
                )

                true
            }

            plantName.setBackgroundResource(
                R.drawable.add_btn
            )

        }

    }

    fun setData(data: List<Plant>) {
        this.plantData.clear()
        this.plantData.addAll(data)
        // 아이템이 총 몇개에서 몇개로 변하는지 정확하게 알 수 없기 때문에 이 메소드를 사용
        notifyDataSetChanged()
    }

}
