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
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Add_Member.Add_Member_Fragment
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Title_Manager.Title_Manager_Fragment
import com.example.WMS.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class Member_Manager_Fragment:Fragment() {
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var memberListAdapter:Member_List_Adapter
    lateinit var member_Recycle:RecyclerView
    lateinit var add_new_member: ExtendedFloatingActionButton
    lateinit var title_manager: ExtendedFloatingActionButton
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
        member_Recycle.layoutManager=LinearLayoutManager(context)
        val mList: List<String> = listOf("1", "3", "4", "5", "3")
        memberListAdapter= Member_List_Adapter(mList,activity as MainActivity)
        member_Recycle.adapter=memberListAdapter

        title_manager.setOnClickListener {
            var titleManagerFragment=Title_Manager_Fragment()
            (activity  as MainActivity).fragment_Manager.hide_all(titleManagerFragment)
        }
        add_new_member.setOnClickListener {
            var addMemberFragment=Add_Member_Fragment()
            (activity  as MainActivity).fragment_Manager.hide_all(addMemberFragment)
        }
    }
}