package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Imformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.R


class Member_Imformation_Fragment:Fragment() {
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var title_spinner: Spinner
    lateinit var save:Button
    lateinit var select_title:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.member_information,container,false)
        init(view)
        return view
    }
    fun init(view: View){
        base_Top_Bar= Base_Topbar(view,activity as MainActivity,false)
        base_Top_Bar.setTitle("人员管理")
        title_spinner=view.findViewById(R.id.member_title)
        save=view.findViewById(R.id.save)
        val mList: List<String> = listOf("初级员工", "高级员工", "管理员", "仓库主任", "CEO")
        select_title=mList[0]
        var arrayAdapter=ArrayAdapter<String>(activity as MainActivity,R.layout.member_title_spinner,mList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        title_spinner.adapter=arrayAdapter
        title_spinner.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                select_title=mList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        title_spinner.visibility=View.VISIBLE
        save.setOnClickListener {
            (activity as MainActivity).fragment_Manager.pop()
        }
    }
}