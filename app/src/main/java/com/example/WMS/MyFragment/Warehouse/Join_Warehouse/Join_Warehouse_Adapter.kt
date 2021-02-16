package com.example.WMS.MyFragment.Warehouse.Join_Warehouse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.R

class Join_Warehouse_Adapter(var list:List<String>): RecyclerView.Adapter<Join_Warehouse_Adapter.ViewHolder>() {

   inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
         var imageView:ImageView=itemView.findViewById(R.id.ware_img)
         var ware_name:TextView=itemView.findViewById(R.id.warehouse_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =LayoutInflater.from(parent.context).inflate(R.layout.join_ware_item,parent,false)
        var viewHolder=ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context,position.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}