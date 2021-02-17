package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.R

class Member_Manager_Fragment:Fragment() {
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var memberListAdapter:Member_List_Adapter
    lateinit var member_Recycle:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.member_manager,container,false)
        init(view)
        return view
    }
    fun init(view:View){
        base_Top_Bar= Base_Topbar(view,activity as MainActivity,true)
        member_Recycle=view.findViewById(R.id.member_list)
        member_Recycle.layoutManager=LinearLayoutManager(context)
        val mList: List<String> = listOf("1", "3", "4", "5", "3")
        memberListAdapter= Member_List_Adapter(mList,activity as MainActivity)
        member_Recycle.adapter=memberListAdapter
    }
}