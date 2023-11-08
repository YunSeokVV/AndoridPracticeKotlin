package com.example.samplekotlin.adpater

import android.util.SparseBooleanArray
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplekotlin.R
import com.example.samplekotlin.model.Plant
import com.orhanobut.logger.Logger

class PlantListAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<PlantListAdapter.ViewHolder>() {

    private var isFiltered = false

    //private lateinit var originalData = List<Plant>() 아래처럼하지말고 이렇게 리스트 선언하기
    private val originalData = mutableListOf<Plant>()

    private val longClickedItem = SparseBooleanArray()

    private val data = mutableListOf<Plant>()

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
        holder.bind(data[position])

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun notifyItemChanged() {
        notifyItemInserted(data.size)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantName: TextView = itemView.findViewById(R.id.plantName)
        val plantImage: ImageView = itemView.findViewById<ImageView>(R.id.palntImage)

        fun bind(item: Plant) {
            plantName.text = item.name
            Glide.with(itemView.context).load(item.imageResource).into(plantImage)
            // 이미지뷰의 모서리를 약간 둥글게 만든다.
            plantImage.clipToOutline = true
            //plantName.setBackgroundColor(R.color.yellow)

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
            Logger.v("init called")
            itemView.setOnClickListener {
                Logger.v("setOnClickListener")
                //listener.onItemClick(data.get(adapterPosition))
                onItemClickListener.onItemClick(data.get(adapterPosition))
            }

            itemView.setOnLongClickListener {
                Logger.v("setOnLongClickListener")
                //longClickListener.onItemlongClick(data.get(adapterPosition))
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
        val filtered : List<Plant> = originalData.take(4).mapNotNull { it }

        // 아이템을 4개이하로 필터링 해야하는 경우
        if(!isFiltered){

            // 원본 컬렉션의 각 요소에 지정된 변환 함수를 적용한 결과 중 널이 아닌 결과만 포함된 목록을 반환합니다.
            //val filtered: List<Plant> = originalData.mapNotNull {it}
            setData(filtered)
            notifyItemRangeRemoved(filtered.size, originalData.size - filtered.size)
            isFiltered = true
        }

        // 전체 아이템 목록을 보여주는 경우
        else{
            setData(originalData)
            notifyItemRangeInserted(filtered.size, originalData.size - filtered.size)
            isFiltered = false
        }


//        // 필터링 되지 않은 경우. 필터링을 진행해서 아이템을 4개로 만든다.
//        if (data.size != 4) {
//            //this.data = filtered
//            //처음 n개의 요소가 포함된 리스트를 반환합니다.
//            val filteredData = originalData.take(4)
//
//
//
//            // 원본 컬렉션의 각 요소에 지정된 변환 함수를 적용한 결과 중 널이 아닌 결과만 포함된 목록을 반환합니다.
//            val filtered: List<Plant> = filteredData.mapNotNull {it}
//
//            // 주어진 인덱스에 있는 요소를 반환하거나 인덱스가 이 목록의 범위를 벗어난 경우 null을 반환합니다.
//            if(originalData.getOrNull(0) != null){
//                setData(filtered)
//                notifyItemRangeRemoved(4, 6)
//            }
//
//        }
//
//        // 필터링을 해제하는 경우. 원래 아이템의 개수대로 되돌린다.
//        else {
////            Logger.v(originalData.size.toString())
//            Logger.v(this.data.size.toString())
////            this.data = originalData
//            setData(originalData)
//            notifyItemRangeInserted(4, 6)
//        }

    }

    fun setOriginalData(list: List<Plant>) {
        //originalData = list
        this.originalData.clear()
        this.originalData.addAll(list)
    }

    fun setData(data: List<Plant>) {
        this.data.clear()
        this.data.addAll(data)
    }

}
