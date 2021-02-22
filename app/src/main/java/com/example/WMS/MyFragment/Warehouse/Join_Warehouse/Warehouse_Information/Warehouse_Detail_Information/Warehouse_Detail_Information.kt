package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Warehouse_Detail_Information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.R

class Warehouse_Detail_Information :Fragment(){
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var warehouseDetailInformationAdapter:Warehouse_Detail_Information_Adapter
    lateinit var warehouse_List_RecycleView:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.warehouse_detail_information,container,false)
        init(view)
        return view
    }
    fun init(view:View){
        base_Top_Bar= Base_Topbar(view,activity as MainActivity,false)
        base_Top_Bar.setTitle("仓库信息 ")
        warehouse_List_RecycleView=view.findViewById(R.id.warehouse_list)
        warehouse_List_RecycleView.layoutManager= LinearLayoutManager(context)
        val mList: List<String> = listOf("1", "3", "4", "5", "3","1", "3", "4", "5", "3","1", "3", "4", "5", "3","1", "3", "4", "5", "3")
        warehouseDetailInformationAdapter=Warehouse_Detail_Information_Adapter(mList,activity as MainActivity)
        warehouse_List_RecycleView.adapter=warehouseDetailInformationAdapter
    }
}