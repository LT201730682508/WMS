package com.example.WMS.MyFragment.Warehouse.Join_Warehouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Join_Warehouse_Model
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
        initdata()
    }
    fun initdata(){
        join_recycleview.layoutManager= LinearLayoutManager(context)
        Join_Warehouse_Model.getData(object :Join_Warehouse_Model.Show{
            override fun show(wares: Array<All_Warehouse_Model.Warehouse>) {
                joinWarehouseAdapter= Join_Warehouse_Adapter(wares,activity as MainActivity)
                join_recycleview.adapter=joinWarehouseAdapter
            }

        },(activity as MainActivity).fragment_Manager.userinfo)

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (isHidden) {
        } else {
            initdata()
        }
    }
    override fun onResume() {
        super.onResume()
    }
}