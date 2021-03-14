package com.example.WMS.MyFragment.Warehouse.All_Warehouse

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

class All_Warehouse_Fragment: Fragment() {
    lateinit var all_recycleview: RecyclerView
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var allWarehouseAdapter: All_Warehouse_Adapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.all_warehouse,container,false)
        init(view)
        return view
    }
    fun init(view: View){
        base_Top_Bar= Base_Topbar(view,(activity as MainActivity),true)
        base_Top_Bar.setTitle("仓库管理")
        all_recycleview=view.findViewById(R.id.all_recycleview)
        initdata()
    }

    fun initdata(){
        all_recycleview.layoutManager= LinearLayoutManager(context)
        All_Warehouse_Model.getData(object :All_Warehouse_Model.Show{
            override fun show(wares: Array<All_Warehouse_Model.Warehouse>) {
                allWarehouseAdapter= All_Warehouse_Adapter(wares,activity as MainActivity)
                all_recycleview.adapter=allWarehouseAdapter
            }

        })

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