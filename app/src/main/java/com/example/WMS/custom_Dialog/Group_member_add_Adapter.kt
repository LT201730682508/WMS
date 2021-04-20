package com.example.WMS.custom_Dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Member_Manager_Model
import com.example.WMS.R
import kotlinx.android.synthetic.main.group_add_item.view.*
import kotlinx.android.synthetic.main.member_item.view.*
import kotlinx.android.synthetic.main.member_item.view.member_name
import kotlinx.android.synthetic.main.member_list_add.view.*

class Group_member_add_Adapter (
    val context: Context,
    var group_id:Int,
    var list: ArrayList<Member_Manager_Model.member_item>,
    val token:String,
    val notifychange: Member_Manager_Model.notifychange
): RecyclerView.Adapter<Group_member_add_Adapter.ViewHolder>() {

    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view :View
        var viewHolder:ViewHolder

        view =
                LayoutInflater.from(parent.context).inflate(R.layout.group_add_item, parent, false)
        viewHolder = ViewHolder(view)

        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.member_name.text=list[position].user_name
            holder.itemView.make_sure.setOnClickListener {
                   Member_Manager_Model.groupAddMember(context,token,group_id,list[position].user_name,notifychange)
            }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}