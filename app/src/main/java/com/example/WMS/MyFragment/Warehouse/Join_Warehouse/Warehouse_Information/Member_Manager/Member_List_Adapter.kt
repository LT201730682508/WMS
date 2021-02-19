package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Imformation.Member_Imformation_Fragment
import com.example.WMS.R

class Member_List_Adapter(var list:List<String>,var activity: MainActivity):RecyclerView.Adapter<Member_List_Adapter.ViewHolder>() {
    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
         var member_name:TextView=itemview.findViewById(R.id.member_name)
         var member_img:ImageView=itemview.findViewById(R.id.member_img)
         var member_title:TextView=itemview.findViewById(R.id.member_title)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.member_item,parent,false)
        var viewHolder=ViewHolder(view)
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            var memberImformationFragment=Member_Imformation_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(memberImformationFragment)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}