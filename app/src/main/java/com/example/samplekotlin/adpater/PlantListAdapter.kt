package com.example.samplekotlin.adpater

import android.util.SparseBooleanArray
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
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

class PlantListAdapter() : RecyclerView.Adapter<PlantListAdapter.ViewHolder>() {
    private lateinit var originalData: List<Plant>

    private lateinit var listener: OnItemClickListener

    private lateinit var longClickListener: OnItemLongClickListener

    private val longClickedItem = SparseBooleanArray()

    //private val data = mutableListOf<Plant>()
    private var data = listOf<Plant>()

    fun setClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setLongClickListener(longClickListener: OnItemLongClickListener) {
        this.longClickListener = longClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(plant: Plant)
    }

    interface OnItemLongClickListener {
        fun onItemlongClick(plant: Plant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LayoutInflater.from(parent.context)
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.plant_item_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

//        val gestureDetector = GestureDetector(
//            holder.itemView.context,
//            object : GestureDetector.SimpleOnGestureListener() {
//                override fun onLongPress(e: MotionEvent) {
//                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
//                }
//
//                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
//                    Logger.v("Check here!!!")
//                    return super.onSingleTapConfirmed(e)
//                }
//
//            })
//
//        holder.itemView.setOnTouchListener { v, event ->
//            gestureDetector.onTouchEvent(event)
//
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    //listener.onItemClick(data[position])
//                }
//
//                MotionEvent.ACTION_UP -> {
//                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
//                }
//            }
//            true // 이벤트가 소비되었음을 나타냄
//        }

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

        val gestureDetector = GestureDetector(
            itemView.context,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onLongPress(e: MotionEvent) {
                    Logger.v("onLongPress called")
                    itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.yellow
                        )
                    )
                }

                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    Logger.v("onSingleTapConfirmed called")

                    return super.onSingleTapConfirmed(e)
                }

            })

        init {
            itemView.setOnClickListener {
                Logger.v("setOnClickListener")
                listener.onItemClick(data.get(adapterPosition))
            }

            itemView.setOnLongClickListener {
                Logger.v("setOnLongClickListener")
                longClickListener.onItemlongClick(data.get(adapterPosition))
                //itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.yellow))
                longClickedItem.put(adapterPosition, true)
                true
            }

            itemView.setOnTouchListener { v, event ->
                Logger.v("setOnTouchListener")
                gestureDetector.onTouchEvent(event)

                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        itemView.setBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.white
                            )
                        )
                    }
                }
                false
            }

        }

    }

    fun filterPlants() {
        val filtered: List<Plant> = listOf(
            originalData.get(0),
            originalData.get(1),
            originalData.get(2),
            originalData.get(3)
        )
        // 필터링 되지 않은 경우. 필터링을 진행해서 아이템을 4개로 만든다.
        if (data.size != 4) {
            this.data = filtered
            notifyItemRangeRemoved(4,6)
        }

        // 필터링을 해제하는 경우. 원래 아이템의 개수대로 되돌린다.
        else {
            Logger.v(originalData.size.toString())
            Logger.v(this.data.size.toString())
            this.data = originalData
            notifyItemRangeInserted(4, 6)
        }

    }

    fun setOriginalData(list: List<Plant>) {
        originalData = list
    }

    fun setData(data: List<Plant>) {
        this.data = data
    }

}