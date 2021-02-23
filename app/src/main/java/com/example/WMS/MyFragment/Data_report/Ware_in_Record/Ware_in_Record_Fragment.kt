package com.example.WMS.MyFragment.Data_report.Ware_in_Record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyRecyclerView
import com.example.WMS.R

class Ware_in_Record_Fragment :Fragment(){
    lateinit var baseTopbar:Base_Topbar
    lateinit var ware_spinner:Spinner
    lateinit var ware_in_recycle: MyRecyclerView
    lateinit var select_title:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.ware_in_record,container,false)
        init(view)
        return view
    }
    fun init(view:View){
        baseTopbar= Base_Topbar(view, activity  as MainActivity,true)
        baseTopbar.setTitle("数据报表")
        ware_spinner=view.findViewById(R.id.ware_spinner)
        ware_in_recycle=view.findViewById(R.id.ware_in_recycle)
        val mList: List<String> = listOf("初级员工", "高级员工", "管理员", "仓库主任", "CEO")
        var arrayAdapter= ArrayAdapter<String>(activity as MainActivity,R.layout.member_title_spinner,mList)
        arrayAdapter.setDropDownViewResource(R.layout.item_dropdown)
        ware_spinner.adapter=arrayAdapter
        ware_spinner.prompt="请选择仓库"
        ware_spinner.onItemSelectedListener=object :
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
        val mList1: List<String> = listOf("1", "3", "4", "5", "3","1", "3", "4", "5", "3","1", "3", "4", "5", "3","1", "3", "4", "5", "3","1", "3", "4", "5", "3")
        var wareInListAdapter=Adapter(mList1,activity as MainActivity)
        ware_in_recycle.layoutManager= LinearLayoutManager(context)
        ware_in_recycle.adapter=wareInListAdapter
    }
}