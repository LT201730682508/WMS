package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Member_Manager_Fragment
import com.example.WMS.R
import com.example.WMS.WarehouseIn.WarehouseInList_Fragment
import com.example.WMS.WarehouseOut.WarehouseOutList_Fragment

class Warehouse_Information (var str:String):Fragment(){
    lateinit var ware_information: TextView
    lateinit var ware_in: TextView
    lateinit var ware_out: TextView
    lateinit var ware_menber: TextView
    lateinit var base_Top_Bar: Base_Topbar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.warehouse_information,container,false)
        init(view)
        return view
    }
    fun init(view:View){
        base_Top_Bar= Base_Topbar(view,activity as MainActivity,false)
        base_Top_Bar.setTitle("我加入的仓库")
        ware_in=view.findViewById(R.id.ware_in)
        ware_out=view.findViewById(R.id.ware_out)
        ware_information=view.findViewById(R.id.information)
        ware_menber=view.findViewById(R.id.ware_menber)

        ware_in.setOnClickListener {
            var warehouseinlistFragment=WarehouseInList_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(warehouseinlistFragment)
        }
        ware_out.setOnClickListener {
            var warehouseoutlistFragment=WarehouseOutList_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(warehouseoutlistFragment)
        }
        ware_menber.setOnClickListener {
            var memberManagerFragment=Member_Manager_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(memberManagerFragment)
        }
    }
}