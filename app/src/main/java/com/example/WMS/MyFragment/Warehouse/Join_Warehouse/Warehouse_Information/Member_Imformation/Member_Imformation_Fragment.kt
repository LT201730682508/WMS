package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Imformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Member_Manager_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Title_Manager.Title_Manager_Model
import com.example.WMS.R
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.member_information.*


class Member_Imformation_Fragment(val warehouseId:Int,val item: Member_Manager_Model.member_item):Fragment() {
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var title_spinner: Spinner
    lateinit var save:Button
    lateinit var select_title:String
    lateinit var member_name:TextView
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
        member_name=view.findViewById(R.id.member_name)
        member_name.text=item.user_name



        save=view.findViewById(R.id.save)
        Title_Manager_Model.get_title_list((activity as MainActivity).fragment_Manager.userinfo.token,warehouseId,object :Title_Manager_Model.titleShow{
            override fun show(list: Array<Title_Manager_Model.titleItem>) {
                var mList= arrayListOf<String>()
                for (l in list){
                    mList.add(l.role)
                }
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
            }

        })



        save.setOnClickListener {
            Member_Imformation_Model.modify_member_title(Member_Imformation_Model.modify_params((activity as MainActivity).fragment_Manager.userinfo.token,warehouseId,item.user_name,select_title),object :Member_Imformation_Model.result{
                override fun result() {
                    XToast.success(requireContext(),"修改成功").show()
                    (activity as MainActivity).fragment_Manager.pop()
                }

            })

        }
    }
}