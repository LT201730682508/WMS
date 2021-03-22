package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Add_Member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.R

class Add_Member_Fragment:Fragment() {
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var title_spinner:Spinner
    lateinit var search_no:TextView
    lateinit var search_text:EditText
    lateinit var search_img:ImageView
    lateinit var search_results:RelativeLayout
    lateinit var invite:Button
    lateinit var select_title:String
    lateinit var new_member_name:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.member_add,container,false)
        init(view)
        return view
    }
    fun init(view: View){
        base_Top_Bar= Base_Topbar(view,activity as MainActivity,false)
        base_Top_Bar.setTitle("人员管理")
        title_spinner=view.findViewById(R.id.new_member_title)
        search_results=view.findViewById(R.id.search_results)
        invite=view.findViewById(R.id.invite)
        search_img=view.findViewById(R.id.search_img)
        new_member_name=view.findViewById(R.id.new_member_name)
        search_text=view.findViewById(R.id.search_text)
        search_no=view.findViewById(R.id.search_no)
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
        search_img.setOnClickListener {
            val memberparams= Add_Member_Model.member_params((activity as MainActivity).fragment_Manager.userinfo.token,search_text.text.toString().toInt())
            Add_Member_Model.search_user(object :Add_Member_Model.Companion.show{
                override fun shou(str: String) {

                }

            },memberparams)
        }
        invite.setOnClickListener {
            Toast.makeText(activity,"已发出邀请",Toast.LENGTH_SHORT).show()
        }
    }
}