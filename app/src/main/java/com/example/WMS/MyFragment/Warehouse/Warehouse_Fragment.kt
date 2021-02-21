package com.example.WMS.MyFragment.Warehouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Create_Warehouse.Create_Warehouse_Fragment
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Join_Warehouse_Fragment
import com.example.WMS.R

class Warehouse_Fragment:Fragment() {
    lateinit var join_ware:RelativeLayout
    lateinit var create_ware:RelativeLayout
    lateinit var base_Top_Bar: Base_Topbar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.warehouse,container,false)
        init(view)
        return view
    }

    fun init(view:View){
        base_Top_Bar= Base_Topbar(view,(activity as MainActivity),false)
        join_ware=view.findViewById(R.id.join_ware)
        create_ware=view.findViewById(R.id.create_ware)
        join_ware.setOnClickListener {
            var joinWarehouseFragment=Join_Warehouse_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(joinWarehouseFragment)
        }
        create_ware.setOnClickListener {
            var create_Warehouse_Fragment=Create_Warehouse_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(create_Warehouse_Fragment)
        }
    }
}