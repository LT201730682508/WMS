package com.example.WMS.MyFragment.Data_report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Data_report.Ware_in_Record.Ware_in_Record_Fragment
import com.example.WMS.MyFragment.Data_report.Ware_in_Record.Ware_out_Record_Fragment
import com.example.WMS.R

class Data_report_fragment :Fragment(){
    lateinit var base_Top_Bar:Base_Topbar
    lateinit var ware_in:RelativeLayout
    lateinit var ware_out:RelativeLayout
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
        ware_in=view.findViewById(R.id.ware_in)
        ware_out=view.findViewById(R.id.ware_out)
        ware_in.setOnClickListener {
            var wareInRecordFragment=Ware_in_Record_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(wareInRecordFragment)
        }
        ware_out.setOnClickListener {
            var wareOutRecordFragment=Ware_out_Record_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(wareOutRecordFragment)
        }
    }
}