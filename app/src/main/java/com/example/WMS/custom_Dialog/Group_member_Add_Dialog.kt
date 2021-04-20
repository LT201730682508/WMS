package com.example.WMS.custom_Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Member_Manager_Model
import com.example.WMS.R

class Group_member_Add_Dialog (val group_id:Int,context: Context, val token:String,val wareHouseid:Int,val activity: MainActivity,val notifychange: Member_Manager_Model.notifychange) : Dialog(context,
    R.style.CustomDialog) {
    lateinit var user_ry:RecyclerView
    lateinit var groupMemberAddAdapter: Group_member_add_Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view = View.inflate(context, R.layout.group_add_dialog, null)
        setContentView(view)
        user_ry=view.findViewById(R.id.add_ry)
        user_ry.layoutManager=LinearLayoutManager(context)
        getAllStaff()
    }
    fun getAllStaff(){
        Member_Manager_Model.getData(object : Member_Manager_Model.Show{
            override fun show(wares: Array<Member_Manager_Model.member_item>) {
                if(wares.size==0){

                }else{
                    groupMemberAddAdapter= Group_member_add_Adapter(context,group_id,wares,token,notifychange)

                    user_ry.adapter=groupMemberAddAdapter
                }
            }
        },(activity as MainActivity).fragment_Manager.userinfo,wareHouseid)
    }
}