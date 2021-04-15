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
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Group_Manager.Group_Manager_Fragment
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Group_Manager.Group_Model
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
    lateinit var group_manager:ExtendedFloatingActionButton
    lateinit var empty_rl:RelativeLayout
     lateinit var memberList:ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.member_manager,container,false)
        init(view)
        return view
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (isHidden) {
        } else {
            getGroup(wareHouseid)
        }
    }
    fun init(view:View) {
        base_Top_Bar = Base_Topbar(view, activity as MainActivity, true)
        base_Top_Bar.setTitle("仓库信息")
        title_manager = view.findViewById(R.id.title_manager)
        add_new_member = view.findViewById(R.id.add_new_member)
        member_Recycle = view.findViewById(R.id.member_list)
        group_manager=view.findViewById(R.id.manager_group)
        empty_rl = view.findViewById(R.id.empty_rl)
        memberList = arrayListOf()
        if(Warehouse_Authority_List.roleList_Map.get(wareHouseid.toString() + (activity as MainActivity).fragment_Manager.userinfo.token)=="库主"){
            group_manager.visibility=View.VISIBLE
        }else{
            group_manager.visibility=View.GONE
        }
        getGroup(wareHouseid)
        group_manager.setOnClickListener {
            var groupManagerFragment=Group_Manager_Fragment(wareHouseid)
            (activity as MainActivity).fragment_Manager.hide_all(groupManagerFragment)
        }
        val authority =
            Warehouse_Authority_List.authorityList_Map.get(wareHouseid.toString() + (activity as MainActivity).fragment_Manager.userinfo.token)
        if(authority!!.contains('e')){
            title_manager.visibility=View.VISIBLE
        }else  title_manager.visibility=View.GONE
        if(authority!!.contains('d')){
            add_new_member.visibility=View.VISIBLE
        }else  add_new_member.visibility=View.GONE
        title_manager.setOnClickListener {
                    val authority =
                        Warehouse_Authority_List.authorityList_Map.get(wareHouseid.toString() + (activity as MainActivity).fragment_Manager.userinfo.token)
                    println("@@@@@@@@$authority")
                    val authorities = authority!!.toCharArray()
                    if (authorities.contains('e')) {
                        var titleManagerFragment = Title_Manager_Fragment(wareHouseid)
                        (activity as MainActivity).fragment_Manager.hide_all(titleManagerFragment)
                    } else {
                        XToast.warning(requireContext(), "您没有相关权限").show()
                    }
        }
            add_new_member.setOnClickListener {

                    val authority =
                        Warehouse_Authority_List.authorityList_Map.get(wareHouseid.toString() + (activity as MainActivity).fragment_Manager.userinfo.token)
                    if (authority!!.contains("d")) {
                        var addMemberFragment = Add_Member_Fragment(wareHouseid)
                        (activity as MainActivity).fragment_Manager.hide_all(addMemberFragment)
                    } else {
                        XToast.warning(requireContext(), "您没有相关权限").show()
                    }
                }
        }
    fun getGroup(wareHouseid: Int){
        member_Recycle.layoutManager=LinearLayoutManager(context)
        Group_Model.get_group_list((activity as MainActivity).fragment_Manager.userinfo.token,wareHouseid,object :
                Group_Model.GroupShow{
                override fun show(g: Array<Group_Model.Gropu_data>) {

                    var arrayList= arrayListOf<Group_Model.Gropu_data>()
                    var all=Group_Model.Gropu_data(0,"所有成员",0)
                    arrayList.add(all)
                    arrayList.addAll(g)

                    var gropu_adapter= Member_group_Adapter(activity as MainActivity,arrayList,wareHouseid)
                    member_Recycle.adapter=gropu_adapter
                }
            })
    }
}