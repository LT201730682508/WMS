package com.example.WMS.MyFragment.Warehouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Join_Warehouse_Fragment
import com.example.WMS.R

class Warehouse_Fragment:Fragment() {
    lateinit var join_ware:RelativeLayout
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
        var search=view.findViewById<ImageView>(R.id.search)
        search.visibility=View.GONE
        var back=view.findViewById<ImageView>(R.id.back)
        back.setOnClickListener { (activity as MainActivity).fragment_Manager.pop() }
        join_ware=view.findViewById(R.id.join_ware)
        join_ware.setOnClickListener {
            var joinWarehouseFragment=Join_Warehouse_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(joinWarehouseFragment)
        }
    }
}