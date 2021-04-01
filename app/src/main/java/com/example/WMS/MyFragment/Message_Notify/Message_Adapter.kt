package com.example.WMS.MyFragment.Message_Notify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.WMS.MainActivity
import com.example.WMS.R
import com.xuexiang.xui.widget.toast.XToast

class Message_Adapter(
    var list: Array<Message_Notify_Model.invite_item>,
    var activity: MainActivity): RecyclerView.Adapter<Message_Adapter.ViewHolder>() {
    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        var message_img=itemview.findViewById<ImageView>(R.id.message_img)
        var message_ware_name=itemview.findViewById<TextView>(R.id.message_ware_name)
        var message_ware_title=itemview.findViewById<TextView>(R.id.message_ware_title)
        var join_it=itemview.findViewById<Button>(R.id.join_it)
        var delete=itemview.findViewById<ImageView>(R.id.delete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.message_item,parent,false)
        var viewHolder=ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        Glide.with(activity).load(R.drawable.touxiang).into(holder.message_img)
        holder.message_ware_name.text=list[position].warehouseName
        holder.message_ware_title.text=list[position].role
        holder.join_it.setOnClickListener {
            if(list[position].invitation_id==(activity as MainActivity).fragment_Manager.userinfo.userInfo.companyId||(activity as MainActivity).fragment_Manager.userinfo.userInfo.companyId==0){
                Message_Notify_Model.accept_Invite(list[position].invitation_id,(activity as MainActivity).fragment_Manager.userinfo.token,object :Message_Notify_Model.after_Show_accept{
                    override fun show(str: String) {
                        if(str=="OK") {
                            notifyItemRemoved(position)
                            XToast.success(activity,"成功加入").show()
                        }
                    }
                })
            }else{
                XToast.warning(activity,"所属公司不同，无法加入").show()
            }

        }
        holder.delete.setOnClickListener {
            Message_Notify_Model.refuse_Invite(list[position].invitation_id,(activity as MainActivity).fragment_Manager.userinfo.token,object :Message_Notify_Model.after_Show_accept{
                override fun show(str: String) {
                    if(str=="OK") {

                        notifyItemRemoved(position)
                        XToast.success(activity,"已拒绝").show()
                    }


                }
            })
        }
    }
}