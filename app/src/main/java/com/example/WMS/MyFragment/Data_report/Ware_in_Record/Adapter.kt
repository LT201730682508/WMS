package com.example.WMS.MyFragment.Data_report.Ware_in_Record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.WMS.MainActivity
import com.example.WMS.R

class Adapter(var list:Array<Ware_In_Record_Model.In_Record>,var activity: MainActivity): RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview){
        var img=itemview.findViewById<ImageView>(R.id.ware_in_item_img)
        var date=itemview.findViewById<TextView>(R.id.date)
        var ware_in_item_name=itemview.findViewById<TextView>(R.id.ware_in_item_name)
        var ware_in_item_price=itemview.findViewById<TextView>(R.id.ware_in_item_price)
        var ware_in_item_number=itemview.findViewById<TextView>(R.id.wae_in_item_number)
        var ware_in_item_all_price=itemview.findViewById<TextView>(R.id.wae_in_item_all_price)
        var ware_in_supplier=itemview.findViewById<TextView>(R.id.supplier_or_other)
        var ware_in_note=itemview.findViewById<TextView>(R.id.note)
        var ware_in_execute=itemview.findViewById<TextView>(R.id.executer)
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
        Glide.with(activity).load(R.drawable.touxiang).into(holder.img)
        holder.date.text=list[position].createTime
        holder.ware_in_item_name.text=list[position].productName
        holder.ware_in_item_price.text="单价："+list[position].inPrice.toString()
        holder.ware_in_item_number.text="数量："+list[position].amount.toString()
        holder.ware_in_item_all_price.text="总价："+(list[position].amount*list[position].inPrice).toString()
        holder.ware_in_supplier.text="供应商："+list[position].supplierName.toString()
        holder.ware_in_note.text="备注："+list[position].note.toString()
        holder.ware_in_execute.text="执行者："+list[position].userName.toString()
    }
}