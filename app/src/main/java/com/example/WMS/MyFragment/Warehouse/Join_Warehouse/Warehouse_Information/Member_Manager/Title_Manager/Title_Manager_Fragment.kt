package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Title_Manager

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

class Title_Manager_Fragment:Fragment() {
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var title_Manager_Re:RecyclerView
    lateinit var titleManagerAdapter:Title_Manager_Adapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.title_manager,container,false)
        init(view)
        return view
    }
    fun init(view: View){
        base_Top_Bar= Base_Topbar(view,activity as MainActivity,false)
        base_Top_Bar.setTitle("人员管理")
        title_Manager_Re=view.findViewById(R.id.title_list)
        title_Manager_Re.layoutManager=LinearLayoutManager(requireContext())
        val mList: ArrayList<String> = arrayListOf("1", "3", "4", "5", "3")
        titleManagerAdapter= Title_Manager_Adapter(mList,activity as MainActivity)
        title_Manager_Re.adapter=titleManagerAdapter
    }
}