package com.example.WMS.MyFragment.Data_report.Ware_in_Record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Data_report.Ware_out_Record.Ware_out_Record_Model
import com.example.WMS.R


class Ware_out_Adapter(var list:Array<Ware_out_Record_Model.Out_Record>, var activity: MainActivity): BaseAdapter() {
    class ViewHolder(itemview: View){
        var img=itemview.findViewById<ImageView>(R.id.ware_in_item_img)
        var date=itemview.findViewById<TextView>(R.id.date)
        var ware_out_item_name=itemview.findViewById<TextView>(R.id.ware_in_item_name)
        var ware_out_item_price=itemview.findViewById<TextView>(R.id.ware_in_item_price)
        var ware_out_item_number=itemview.findViewById<TextView>(R.id.wae_in_item_number)
        var ware_out_item_all_price=itemview.findViewById<TextView>(R.id.wae_in_item_all_price)
        var ware_out_recevier=itemview.findViewById<TextView>(R.id.supplier_or_other)
        var ware_out_note=itemview.findViewById<TextView>(R.id.note)
        var ware_out_execute=itemview.findViewById<TextView>(R.id.executer)
    }
   fun makeList(mlist:Array<Ware_out_Record_Model.Out_Record>){
       list=mlist
       notifyDataSetChanged()
   }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var vH:ViewHolder?=null
        var view:View?=null
        if(convertView==null){
            view=View.inflate(activity,R.layout.ware_in_record_item,null);
            vH=ViewHolder(view)
            view.tag=vH
        }else{
            view = convertView
            vH = view.tag as ViewHolder
        }
        vH.date.text=list[position].createTime
        vH.ware_out_item_name.text=list[position].productName
        vH.ware_out_item_price.text="单价"+list[position].outPrice.toString()
        vH.ware_out_item_number.text="数量"+list[position].amount.toString()
        vH.ware_out_item_all_price.text="总价 "+(list[position].amount*list[position].outPrice).toString()
        vH.ware_out_recevier.text="客户："+list[position].receiverName.toString()
        vH.ware_out_note.text="备注："+list[position].note.toString()
        vH.ware_out_execute.text="执行者："+list[position].userName.toString()
        Glide.with(activity).load(R.drawable.touxiang).into(vH.img)
        return view!!
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}