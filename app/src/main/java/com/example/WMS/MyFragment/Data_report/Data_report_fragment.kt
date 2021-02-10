package com.example.WMS.MyFragment.Data_report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.WMS.MainActivity
import com.example.WMS.R

class Data_report_fragment :Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.data_report,container,false)
        init(view)
        return view
    }
    fun init(view:View){
        var search=view.findViewById<ImageView>(R.id.search)
        search.visibility=View.GONE
        var back=view.findViewById<ImageView>(R.id.back)
        back.setOnClickListener { (activity as MainActivity).fragment_Manager.pop() }
    }
}