package com.example.WMS.MyFragment.Data_report.Ware_in_Record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.example.WMS.MainActivity
import com.example.WMS.R


class Ware_out_Adapter(var list:List<String>,var activity: MainActivity): BaseAdapter() {
    class ViewHolder(itemview: View){
        var date=itemview.findViewById<TextView>(R.id.date)
        var ware_in_item_name=itemview.findViewById<TextView>(R.id.ware_in_item_name)
        var ware_in_item_price=itemview.findViewById<TextView>(R.id.ware_in_item_price)
        var wae_in_item_number=itemview.findViewById<TextView>(R.id.wae_in_item_number)
        var wae_in_item_all_price=itemview.findViewById<TextView>(R.id.wae_in_item_all_price)
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