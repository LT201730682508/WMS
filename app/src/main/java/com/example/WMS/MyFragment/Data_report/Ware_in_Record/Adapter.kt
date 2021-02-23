package com.example.WMS.MyFragment.Data_report.Ware_in_Record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.R

class Adapter(var list:List<String>,var activity: MainActivity): RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview){
        var date=itemview.findViewById<TextView>(R.id.date)
        var ware_in_item_name=itemview.findViewById<TextView>(R.id.ware_in_item_name)
        var ware_in_item_price=itemview.findViewById<TextView>(R.id.ware_in_item_price)
        var wae_in_item_number=itemview.findViewById<TextView>(R.id.wae_in_item_number)
        var wae_in_item_all_price=itemview.findViewById<TextView>(R.id.wae_in_item_all_price)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.ware_in_record_item,parent,false)
        var viewHolder=ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

    }
}