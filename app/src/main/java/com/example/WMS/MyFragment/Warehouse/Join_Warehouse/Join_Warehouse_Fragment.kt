package com.example.WMS.MyFragment.Warehouse.Join_Warehouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.R

class Join_Warehouse_Fragment:Fragment() {
    lateinit var join_recycleview:RecyclerView
    lateinit var base_Top_Bar:Base_Topbar
    lateinit var joinWarehouseAdapter:Join_Warehouse_Adapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.join_warehouse,container,false)
        init(view)
        return view
    }
    fun init(view:View){
        base_Top_Bar= Base_Topbar(view,(activity as MainActivity),true)
        base_Top_Bar.setTitle("仓库管理")
        join_recycleview=view.findViewById(R.id.join_recycleview)
        join_recycleview.layoutManager= LinearLayoutManager(context)
        val mList: List<String> = listOf("1", "3", "4", "5", "3","1", "3", "4", "5", "3","1", "3", "4", "5", "3","1", "3", "4", "5", "3")
        joinWarehouseAdapter= Join_Warehouse_Adapter(mList,activity as MainActivity)
        join_recycleview.adapter=joinWarehouseAdapter
    }

    override fun onResume() {
        super.onResume()
    }
}