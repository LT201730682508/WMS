package com.example.WMS.MyFragment.Data_report.Ware_out_Record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Login_fragment
import com.example.WMS.MyFragment.Message_Notify.Message_Adapter
import com.example.WMS.MyFragment.Message_Notify.Message_Notify_Model
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.R
import kotlinx.android.synthetic.main.ware_record__ware_item.view.*

class Ware_Out_Ware_Adapter(var list: ArrayList<All_Warehouse_Model.Warehouse>,var result: Ware_out_Record_Model.Ware_Record,var str:Show_Sure,var userLogin:Login_fragment.user_Login,var dialog: Ware_Out_Record_Dialog
): RecyclerView.Adapter<Ware_Out_Ware_Adapter.ViewHolder>() {
    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.ware_record__ware_item,parent,false)
        var viewHolder= ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.ware_name.text=list[position].warehouseName
        holder.itemView.setOnClickListener {
            Ware_out_Record_Model.getData(list[position].warehouseId,result,userLogin)
            dialog.dismiss()
            str.show(list[position].warehouseName)
        }
    }
}