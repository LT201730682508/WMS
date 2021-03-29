package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager

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
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Add_Member.Add_Member_Fragment
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Title_Manager.Title_Manager_Fragment
import com.example.WMS.MyFragment.Warehouse.Warehouse_Authority_List
import com.example.WMS.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.xuexiang.xui.widget.toast.XToast

class Member_Manager_Fragment(val wareHouseid:Int):Fragment() {
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var memberListAdapter:Member_List_Adapter
    lateinit var member_Recycle:RecyclerView
    lateinit var add_new_member: ExtendedFloatingActionButton
    lateinit var title_manager: ExtendedFloatingActionButton
    lateinit var empty_rl:RelativeLayout
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
        base_Top_Bar.setTitle("仓库信息")
        title_manager=view.findViewById(R.id.title_manager)
        add_new_member=view.findViewById(R.id.add_new_member)
        member_Recycle=view.findViewById(R.id.member_list)
        empty_rl=view.findViewById(R.id.empty_rl)
        member_Recycle.layoutManager=LinearLayoutManager(context)
        Member_Manager_Model.getData(object :Member_Manager_Model.Show{
            override fun show(wares: Array<Member_Manager_Model.member_item>) {
                if(wares.size==0){
                    empty_rl.visibility==View.VISIBLE
                    member_Recycle.visibility=View.GONE
                }else{
                    memberListAdapter= Member_List_Adapter(wareHouseid,wares,activity as MainActivity)
                    member_Recycle.adapter=memberListAdapter
                }

            }

        },(activity as MainActivity).fragment_Manager.userinfo,wareHouseid)

        title_manager.setOnClickListener {
            val authority=Warehouse_Authority_List.authorityList_Map.get(wareHouseid)
            if(authority!!.contains("e")){
                var titleManagerFragment=Title_Manager_Fragment(wareHouseid)
                (activity  as MainActivity).fragment_Manager.hide_all(titleManagerFragment)
            }else{
                XToast.warning(requireContext(),"您没有相关权限").show()
            }
        }
        add_new_member.setOnClickListener {
            val authority=Warehouse_Authority_List.authorityList_Map.get(wareHouseid)
            if(authority!!.contains("d")){
                var addMemberFragment=Add_Member_Fragment(wareHouseid)
                (activity  as MainActivity).fragment_Manager.hide_all(addMemberFragment)
            }else{
                XToast.warning(requireContext(),"您没有相关权限").show()
            }

        }
    }
}