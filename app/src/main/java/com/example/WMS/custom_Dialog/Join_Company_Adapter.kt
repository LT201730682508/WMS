package com.example.WMS.custom_Dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Message_Notify.Message_Adapter
import com.example.WMS.R

class Join_Company_Adapter(var list:List<String>): RecyclerView.Adapter<Join_Company_Adapter.ViewHolder>() {
    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var company_name = itemview.findViewById<TextView>(R.id.company_name)
        var join_it = itemview.findViewById<Button>(R.id.join_it)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.company_item, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}