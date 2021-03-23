package com.example.WMS.MyFragment.Warehouse.Join_Warehouse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Join_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Warehouse_Information
import com.example.WMS.R

class Join_Warehouse_Adapter(var list: Array<All_Warehouse_Model.Warehouse>, var activity: MainActivity): RecyclerView.Adapter<Join_Warehouse_Adapter.ViewHolder>() {

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
        holder.ware_name.text=list[position].warehouseName
        Glide.with(activity).load(list[position].warehouseImg).into(holder.imageView)
        holder.itemView.setOnClickListener {
            var warehouseInformation= Warehouse_Information(list[position])
            activity.fragment_Manager.hide_all(warehouseInformation)
        }
    }
}