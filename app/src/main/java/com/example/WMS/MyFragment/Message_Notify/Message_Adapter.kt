package com.example.WMS.MyFragment.Message_Notify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Data_report.Ware_in_Record.Adapter
import com.example.WMS.R

class Message_Adapter(var list:List<String>,var activity: MainActivity): RecyclerView.Adapter<Message_Adapter.ViewHolder>() {
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

    }
}