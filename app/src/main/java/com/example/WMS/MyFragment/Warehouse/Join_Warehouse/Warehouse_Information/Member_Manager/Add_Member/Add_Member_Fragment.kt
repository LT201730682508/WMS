package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Add_Member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Title_Manager.Title_Manager_Model
import com.example.WMS.R
import com.xuexiang.xui.widget.toast.XToast
import java.util.*

class Add_Member_Fragment(val warehouseid:Int):Fragment() {
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var title_spinner:Spinner
    lateinit var search_no:TextView
    lateinit var search_text:EditText
    lateinit var search_img:ImageView
    lateinit var search_results:RelativeLayout
    lateinit var invite:Button
    lateinit var select_title:String
    lateinit var new_member_name:TextView
    lateinit var new_member_img:ImageView
    var mList = ArrayList<String>()
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
        new_member_img=view.findViewById(R.id.new_member_img)
        search_no=view.findViewById(R.id.search_no)

        Title_Manager_Model.get_title_list((activity as MainActivity).fragment_Manager.userinfo.token,warehouseid,object :Title_Manager_Model.titleShow{
            override fun show(list: Array<Title_Manager_Model.titleItem>) {
                for(l in list){
                    mList.add(l.role)
                }
                if(!mList.isEmpty()){
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
                }


            }

        })


        search_img.setOnClickListener {
            val memberparams= Add_Member_Model.member_params((activity as MainActivity).fragment_Manager.userinfo.token,search_text.text.toString())
            Add_Member_Model.search_user(object :Add_Member_Model.Companion.show{
                override fun shou(str: Add_Member_Model.search_member_imformation) {
                    if(str.user_name!=null){
                        search_results.visibility=View.VISIBLE
                        search_no.visibility=View.GONE
                        new_member_name.text=str.user_name
                        Glide.with(requireContext()).load(str.user_img).into(new_member_img)
                    }
                  else{
                        search_no.visibility=View.VISIBLE
                        search_results.visibility=View.GONE
                    }
                }
            },memberparams)
        }
        invite.setOnClickListener {
            Add_Member_Model.send_invite((activity as MainActivity).fragment_Manager.userinfo.token,
                Add_Member_Model.member_invite_params(warehouseid,new_member_name.text.toString(),select_title),object :Add_Member_Model.Companion.invite_show{
                    override fun shou(str: String) {
                        if(str=="OK"){
                            XToast.success(requireContext(),"邀请成功").show()
                        }else{
                            XToast.warning(requireContext(),str).show()
                        }
                    }

                }
            )
        }
    }
}