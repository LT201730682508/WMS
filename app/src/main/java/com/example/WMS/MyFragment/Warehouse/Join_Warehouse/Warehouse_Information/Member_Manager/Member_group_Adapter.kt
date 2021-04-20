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
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.group_item.view.*
import kotlinx.android.synthetic.main.group_item.view.cancel
import kotlinx.android.synthetic.main.group_item.view.delete
import kotlinx.android.synthetic.main.group_item.view.delete_frame
import kotlinx.android.synthetic.main.member_item.view.*

class Member_group_Adapter(val activity: MainActivity,var list: ArrayList<Group_Model.Gropu_data>,val wareHouseid: Int,val type:Int,val role:String): RecyclerView.Adapter<Member_group_Adapter.ViewHolder>() {
    var hashMap= HashMap<Int,ArrayList<Member_Manager_Model.member_item>>()
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
        if(type==1) {
            holder.itemView.next.visibility = View.GONE
            holder.itemView.delete.setOnClickListener {
                     Group_Model.deleteGroup(activity.fragment_Manager.userinfo.token,list[position].group_id,object :Group_Model.Delete{
                         override fun show(g: String) {
                             if(g=="OK"){
                                 XToast.success(activity,g).show()
                                 list.removeAt(position)
                                 notifyDataSetChanged()
                             }
                             else
                                 XToast.warning(activity,g).show()

                             holder.itemView.delete_frame.visibility=View.GONE

                         }
                     })
            }
            holder.itemView.cancel.setOnClickListener {
                holder.itemView.delete_frame.visibility=View.GONE
            }
            holder.itemView.setOnLongClickListener {
                holder.itemView.delete_frame.visibility=View.VISIBLE
                 true
            }
        }
        holder.itemView.group_name.text=list[position].group_name
        holder.itemView.down.setOnClickListener {
            holder.itemView.down.visibility=View.GONE
            holder.itemView.next.visibility=View.VISIBLE
            holder.itemView.member_sr.visibility=View.GONE
        }
        if(role!="库主"){
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
                    getGroupMember(position,holder,list[position].group_id,false)
                }
            }
        }
       else{
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
          //`          XToast.success(activity,list[position].group_id.toString()).show()
                    holder.itemView.down.visibility=View.VISIBLE
                    holder.itemView.next.visibility=View.GONE
                    holder.itemView.member_sr.visibility=View.VISIBLE
                    holder.itemView.member_sr.layoutManager=LinearLayoutManager(activity)
                    getGroupMember(position,holder,list[position].group_id,true)
                }
            }
        }


    }

    fun Alldata(holder: ViewHolder,wareHouseid:Int){
        if(hashMap.containsKey(0)){
            var memberListAdapter= Member_List_Adapter(wareHouseid,0,hashMap.get(0)!!,object :Member_Manager_Model.notifychange{
                override fun change() {
                    TODO("Not yet implemented")
                }
            },false,activity )
            holder.itemView.member_sr.adapter=memberListAdapter
        }else{
            Member_Manager_Model.getData(object :Member_Manager_Model.Show{
                override fun show(wares: ArrayList<Member_Manager_Model.member_item>) {
                    if(wares.size==0){

                    }else{
                        hashMap.put(0,wares)
                        var memberListAdapter= Member_List_Adapter(wareHouseid,0,wares,object :Member_Manager_Model.notifychange{
                            override fun change() {
                                TODO("Not yet implemented")
                            }
                        },false,activity )
                        holder.itemView.member_sr.adapter=memberListAdapter
                    }
                }
            },(activity).fragment_Manager.userinfo,wareHouseid)
        }

    }
    fun getGroupMemberMethod(position: Int,holder: ViewHolder,groupId:Int,master:Boolean){
        Member_Manager_Model.getGroupMemberData(object :Member_Manager_Model.Show{
            override fun show(wares: ArrayList<Member_Manager_Model.member_item>) {
                if(wares.size==0){
                }else{
                    hashMap.put(position,wares)
                }
                var memberListAdapter= Member_List_Adapter(wareHouseid,list[position].group_id,wares,object :Member_Manager_Model.notifychange{
                    override fun change() {
                       getGroupMemberMethod(position,holder,groupId,master)
                    }
                },master,activity )
                holder.itemView.member_sr.adapter=memberListAdapter
            }

        },(activity ).fragment_Manager.userinfo.token,groupId)
    }
    fun getGroupMember(position: Int,holder: ViewHolder,groupId:Int,master: Boolean){
        if(hashMap.containsKey(position)){
            var memberListAdapter= Member_List_Adapter(wareHouseid,list[position].group_id,hashMap.get(position)!!,object :Member_Manager_Model.notifychange{
                override fun change() {
                    getGroupMemberMethod(position,holder,groupId,master)
                }
            },master,activity )
            holder.itemView.member_sr.adapter=memberListAdapter
        }else{
           getGroupMemberMethod(position,holder,groupId,master)
        }

    }


}

