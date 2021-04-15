package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Imformation.Member_Imformation_Fragment
import com.example.WMS.R
import com.example.WMS.custom_Dialog.Group_member_Add_Dialog
import kotlinx.android.synthetic.main.member_item.view.*
import kotlinx.android.synthetic.main.member_list_add.view.*

class Member_List_Adapter(
    var warhouseId:Int,
    val group_id:Int,
    var list: Array<Member_Manager_Model.member_item>,
    val notifychange: Member_Manager_Model.notifychange,
    var activity: MainActivity):RecyclerView.Adapter<Member_List_Adapter.ViewHolder>() {

    val TYPEONE=1
    val TYPETWO=2
    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {

    }

    override fun getItemViewType(position: Int): Int {
        // 最后一个item设置为FooterView
        return if (position == list.size) {
            TYPETWO
        } else {
            TYPEONE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view :View
        var viewHolder:ViewHolder
        if(viewType==TYPEONE) {
             view =
                LayoutInflater.from(parent.context).inflate(R.layout.member_item, parent, false)
            viewHolder = ViewHolder(view)

        }else{
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.member_list_add, parent, false)
            viewHolder = ViewHolder(view)
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position<list.size){
            holder.itemView.member_name.text=list[position].user_name
            holder.itemView.member_title.text=list[position].role
            holder.itemView.setOnClickListener {
                var memberImformationFragment=Member_Imformation_Fragment(warhouseId,list[position])
                activity .fragment_Manager.hide_all(memberImformationFragment)
            }
        }else{
            holder.itemView.add.setOnClickListener {
              var groupMemberAddDialog=Group_member_Add_Dialog(group_id,activity,activity.fragment_Manager.userinfo.token,warhouseId,activity,notifychange)
                groupMemberAddDialog.show()
            }
        }

    }
    override fun getItemCount(): Int {
        if(group_id==0){
            return list.size
        }else
        return list.size+1
    }
}