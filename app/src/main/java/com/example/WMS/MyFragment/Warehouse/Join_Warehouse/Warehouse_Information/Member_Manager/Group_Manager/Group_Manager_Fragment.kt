package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Group_Manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Member_group_Adapter
import com.example.WMS.MyFragment.Warehouse.Warehouse_Authority_List
import com.example.WMS.R
import com.example.WMS.custom_Dialog.Create_Group_Dialog

class Group_Manager_Fragment(var wareHouseId:Int) :Fragment(){
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var group_ry:RecyclerView
    lateinit var gropu_adapter:Member_group_Adapter
    lateinit var add_button:Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.group_manager,container,false)
        initView(view)
        return  view
    }
    fun initView(view:View){
        base_Top_Bar = Base_Topbar(view, activity as MainActivity, true)
        base_Top_Bar.setTitle("人员管理")
        group_ry=view.findViewById(R.id.group_ry)
        add_button=view.findViewById(R.id.add_group)
        getData()
        add_button.setOnClickListener {
            var Create_Group_Dialog=Create_Group_Dialog(requireContext(),(activity as MainActivity).fragment_Manager.userinfo.token,wareHouseId,object :Create_Group_Dialog.AddResult{
                override fun result() {
                  getData()
                }
            })
            Create_Group_Dialog.show()
        }
    }

    fun getData(){
        Group_Model.get_group_list((activity as MainActivity).fragment_Manager.userinfo.token,wareHouseId,object :Group_Model.GroupShow{
            override fun show(g: Array<Group_Model.Gropu_data>) {
                group_ry.layoutManager= LinearLayoutManager(requireContext())
                var arrayList= arrayListOf<Group_Model.Gropu_data>()
                arrayList.addAll(g)
                gropu_adapter= Member_group_Adapter(activity as MainActivity,arrayList,wareHouseId,1,
                    Warehouse_Authority_List.roleList_Map.get(wareHouseId.toString()+(activity as MainActivity).fragment_Manager.userinfo.token).toString()
                )
                group_ry.adapter=gropu_adapter
            }

        })
    }
}