package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.AreaManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Group_Manager.Group_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Member_group_Adapter
import com.example.WMS.R
import kotlinx.android.synthetic.main.areaitem.view.*

class AreaListAdapter(val activity: MainActivity, var list: ArrayList<AreaManagerModel.Area> ): RecyclerView.Adapter<AreaListAdapter.ViewHolder>() {
    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.areaitem, parent, false)
        var viewHolder =ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.Area_name.text=list[position].areaName
        holder.itemView.TotalNum.text="总存量："+list[position].totalVolume.toString()
        holder.itemView.currNum.text="剩余存量："+list[position].currVolume
    }

}