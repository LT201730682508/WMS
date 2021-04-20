package com.example.WMS.custom_Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Member_Manager_Model
import com.example.WMS.R

class Group_member_Add_Dialog (val group_id:Int,context: Context, val token:String,val wareHouseid:Int,val activity: MainActivity,val notifychange: Member_Manager_Model.notifychange) : Dialog(context,
    R.style.CustomDialog) {
    lateinit var user_ry:RecyclerView
    lateinit var groupMemberAddAdapter: Group_member_add_Adapter
    lateinit var search_frame:LinearLayout
    lateinit var search_text:EditText
    lateinit var search_img:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view = View.inflate(context, R.layout.group_add_dialog, null)
        setContentView(view)
        user_ry=view.findViewById(R.id.add_ry)
        user_ry.layoutManager=LinearLayoutManager(context)
        search_frame=view.findViewById(R.id.search_frame)
        search_text=view.findViewById(R.id.search_text)
        search_img=view.findViewById(R.id.search_img)
        search_frame.visibility=View.VISIBLE
        search_img.setOnClickListener {
            if(!search_text.text.isEmpty()){
                var mlist= arrayListOf<Member_Manager_Model.member_item>()
                for(l in groupMemberAddAdapter.list){
                    if(l.user_name.contains(search_text.text.toString())){
                        mlist.add(l)
                    }
                }
                groupMemberAddAdapter.list=mlist
                groupMemberAddAdapter.notifyDataSetChanged()
            }else{
                getAllStaff()
            }
        }

        getAllStaff()
    }
    fun getAllStaff(){
        Member_Manager_Model.getData(object : Member_Manager_Model.Show{
            override fun show(wares: ArrayList<Member_Manager_Model.member_item>) {
                if(wares.size==0){

                }else{
                    groupMemberAddAdapter= Group_member_add_Adapter(context,group_id,wares.toList() as ArrayList<Member_Manager_Model.member_item>,token,notifychange)

                    user_ry.adapter=groupMemberAddAdapter
                }
            }
        },(activity as MainActivity).fragment_Manager.userinfo,wareHouseid)
    }
}