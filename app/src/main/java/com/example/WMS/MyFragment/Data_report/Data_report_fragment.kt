package com.example.WMS.MyFragment.Data_report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.R

class Data_report_fragment :Fragment(){
    lateinit var base_Top_Bar:Base_Topbar
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
        base_Top_Bar= Base_Topbar(view,(activity as MainActivity),false)
    }
}