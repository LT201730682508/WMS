package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Group_Manager.Group_Model
import com.example.WMS.R
import kotlinx.android.synthetic.main.group_item.view.*

class Member_group_Adapter(val activity: MainActivity,var list: ArrayList<Group_Model.Gropu_data>,val wareHouseid: Int): RecyclerView.Adapter<Member_group_Adapter.ViewHolder>() {
    var hashMap= HashMap<Int,Array<Member_Manager_Model.member_item>>()
    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.group_item,parent,false)
        var viewHolder=ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.group_name.text=list[position].group_name
        holder.itemView.down.setOnClickListener {
            holder.itemView.down.visibility=View.GONE
            holder.itemView.next.visibility=View.VISIBLE
            holder.itemView.member_sr.visibility=View.GONE
        }

        if(position==0){
            holder.itemView.next.setOnClickListener {
                holder.itemView.down.visibility=View.VISIBLE
                holder.itemView.next.visibility=View.GONE
                holder.itemView.member_sr.visibility=View.VISIBLE
                holder.itemView.member_sr.layoutManager=LinearLayoutManager(activity)
                Alldata(holder,wareHouseid)
            }
        }else{
            holder.itemView.next.setOnClickListener {
                holder.itemView.down.visibility=View.VISIBLE
                holder.itemView.next.visibility=View.GONE
                holder.itemView.member_sr.visibility=View.VISIBLE
                holder.itemView.member_sr.layoutManager=LinearLayoutManager(activity)
                getGroupMember(position,holder,list[position].group_id)
            }
        }
    }

    fun Alldata(holder: ViewHolder,wareHouseid:Int){
        if(hashMap.containsKey(0)){
            var memberListAdapter= Member_List_Adapter(wareHouseid,hashMap.get(0)!!,activity as MainActivity)
            holder.itemView.member_sr.adapter=memberListAdapter
        }else{
            Member_Manager_Model.getData(object :Member_Manager_Model.Show{
                override fun show(wares: Array<Member_Manager_Model.member_item>) {
                    if(wares.size==0){

                    }else{
                        hashMap.put(0,wares)
                        var memberListAdapter= Member_List_Adapter(wareHouseid,wares,activity as MainActivity)
                        holder.itemView.member_sr.adapter=memberListAdapter
                    }
                }
            },(activity as MainActivity).fragment_Manager.userinfo,wareHouseid)
        }

    }
    fun getGroupMember(position: Int,holder: ViewHolder,groupId:Int){
        if(hashMap.containsKey(position)){
            var memberListAdapter= Member_List_Adapter(wareHouseid,hashMap.get(position)!!,activity as MainActivity)
            holder.itemView.member_sr.adapter=memberListAdapter
        }else{
            Member_Manager_Model.getGroupMemberData(object :Member_Manager_Model.Show{
                override fun show(wares: Array<Member_Manager_Model.member_item>) {
                    if(wares.size==0){
                    }else{
                        hashMap.put(position,wares)
                        var memberListAdapter= Member_List_Adapter(wareHouseid,wares,activity as MainActivity)
                        holder.itemView.member_sr.adapter=memberListAdapter
                    }
                }

            },(activity as MainActivity).fragment_Manager.userinfo.token,groupId)
        }

    }
}