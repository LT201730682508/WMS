package com.example.WMS.MyFragment.Warehouse.Join_Warehouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Join_Warehouse_Model
import com.example.WMS.R
import com.xuexiang.xui.widget.progress.loading.ARCLoadingView
import kotlinx.android.synthetic.main.all_warehouse.*

class Join_Warehouse_Fragment:Fragment() {
    lateinit var join_recycleview:RecyclerView
    lateinit var base_Top_Bar:Base_Topbar
    lateinit var joinWarehouseAdapter:Join_Warehouse_Adapter
    lateinit var empty_rl:RelativeLayout
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
        base_Top_Bar.make_sure.setOnClickListener {
            if(base_Top_Bar.search_content.text!!.isEmpty()){
                getDataAgain()
            }else{
                var mlist= arrayListOf<All_Warehouse_Model.Warehouse>()
                for (l in joinWarehouseAdapter.list){
                    if(l.warehouseName.contains(base_Top_Bar.search_content.text.toString()))   {
                        mlist.add(l)
                    }
                }
                joinWarehouseAdapter.list= mlist
                joinWarehouseAdapter.notifyDataSetChanged()
            }

        }
        join_recycleview=view.findViewById(R.id.join_recycleview)
        empty_rl=view.findViewById(R.id.empty_rl)
        initdata()
    }
    fun initdata(){
        join_recycleview.layoutManager= LinearLayoutManager(context)

            Join_Warehouse_Model.getData(object :Join_Warehouse_Model.Show{
                override fun show(wares: ArrayList<All_Warehouse_Model.Warehouse>) {
                    if (wares.size==0){
                        empty_rl.visibility=View.VISIBLE
                        join_recycleview.visibility=View.GONE
                    }else {
                        joinWarehouseAdapter = Join_Warehouse_Adapter(wares, activity as MainActivity,this@Join_Warehouse_Fragment)
                        join_recycleview.adapter = joinWarehouseAdapter
                    }
                }
            },(activity as MainActivity).fragment_Manager.userinfo)



    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (isHidden) {
        } else {
            getDataAgain()
        }
    }
    override fun onResume() {
        super.onResume()
    }
    fun getDataAgain(){
        Join_Warehouse_Model.getData(object :Join_Warehouse_Model.Show{
            override fun show(wares: ArrayList<All_Warehouse_Model.Warehouse>) {
                if (wares.size==0){
                    empty_rl.visibility=View.VISIBLE
                    all_recycleview.visibility=View.GONE
                }else{
                    joinWarehouseAdapter .setMyList(wares)
                }

            }

        },(activity as MainActivity).fragment_Manager.userinfo)
    }
}