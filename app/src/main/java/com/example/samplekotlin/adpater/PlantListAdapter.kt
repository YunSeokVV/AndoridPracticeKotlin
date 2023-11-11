package com.example.samplekotlin.adpater

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplekotlin.R
import com.example.samplekotlin.model.Plant

class PlantListAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<PlantListAdapter.ViewHolder>() {

    private var isFiltered = false

    private val originalData = mutableListOf<Plant>()

    private val longClickedItem = SparseBooleanArray()

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

    override fun getItemCount(): Int {
        return plantData.size
    }

    fun notifyItemChanged() {
        notifyItemInserted(plantData.size)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantName: TextView = itemView.findViewById(R.id.plantName)
        val plantImage: ImageView = itemView.findViewById<ImageView>(R.id.palntImage)

        fun bind(item: Plant) {
            plantName.text = item.location
            Glide.with(itemView.context).load(item.imageResource).into(plantImage)
            // 이미지뷰의 모서리를 약간 둥글게 만든다.
            plantImage.clipToOutline = true

            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
            if (longClickedItem.get(adapterPosition) == true) {
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.yellow
                    )
                )
            }

        }

        init {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(plantData.get(adapterPosition))
            }

            itemView.setOnLongClickListener {
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.yellow
                    )
                )
                longClickedItem.put(adapterPosition, true)
                true
            }
        }

    }

    fun filterPlants() {
        val filtered: List<Plant> = originalData.take(4).mapNotNull { it }

        // 아이템을 4개이하로 필터링 해야하는 경우
        if (!isFiltered) {
            // 원본 컬렉션의 각 요소에 지정된 변환 함수를 적용한 결과 중 널이 아닌 결과만 포함된 목록을 반환합니다.
            //val filtered: List<Plant> = originalData.mapNotNull {it}
            setData(filtered)
            notifyItemRangeRemoved(filtered.size, originalData.size - filtered.size)
            isFiltered = true
        }

        // 전체 아이템 목록을 보여주는 경우
        else {
            setData(originalData)
            notifyItemRangeInserted(filtered.size, originalData.size - filtered.size)
            isFiltered = false
        }

    }

    fun setOriginalData(list: List<Plant>) {
        this.originalData.clear()
        this.originalData.addAll(list)
    }

    fun setData(data: List<Plant>) {
        this.plantData.clear()
        this.plantData.addAll(data)
    }

}
