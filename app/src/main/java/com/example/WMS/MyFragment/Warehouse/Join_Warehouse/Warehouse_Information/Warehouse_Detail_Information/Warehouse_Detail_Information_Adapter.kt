package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Warehouse_Detail_Information

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.R
import kotlinx.android.synthetic.main.warehouse_list.view.*

class Warehouse_Detail_Information_Adapter(var list:List<String>,var activity: MainActivity): RecyclerView.Adapter<Warehouse_Detail_Information_Adapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var name: TextView =itemView.findViewById(R.id.name)
        var number:TextView=itemView.findViewById(R.id.number)
        var money:TextView=itemView.findViewById(R.id.money)
        var xiahuaxian:View=itemView.findViewById(R.id.xiahuaxian)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.warehouse_list,parent,false)
        var viewHolder=ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
         return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position==list.size-1){
            holder.itemView.xiahuaxian.visibility=View.GONE
        }
    }
}