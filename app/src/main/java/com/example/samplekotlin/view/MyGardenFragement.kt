package com.example.samplekotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.samplekotlin.R
import com.example.samplekotlin.adpater.PlantListAdapter

class MyGardenFragement : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_garden_fragement, container, false)
        val textView = view.findViewById<TextView>(R.id.test)
        textView.setText("asdfadsf")

        return view
    }
}