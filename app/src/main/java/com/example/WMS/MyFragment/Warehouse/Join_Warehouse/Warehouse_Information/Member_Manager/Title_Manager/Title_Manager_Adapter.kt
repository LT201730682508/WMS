package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Title_Manager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.R
import com.xuexiang.xui.widget.button.SmoothCheckBox
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.title_manager_item.view.*

class Title_Manager_Adapter(var list:ArrayList<String>,var activity: MainActivity): RecyclerView.Adapter<Title_Manager_Adapter.ViewHolder>() {
    val TYPEONE=1
    val TYPETWO=2
    open class ViewHolder(itemview:View): RecyclerView.ViewHolder(itemview) {
    }
    class ViewHolder_Tilte(itemview: View):ViewHolder(itemview){
        var title:EditText=itemview.findViewById(R.id.title)
        var in_check: SmoothCheckBox=itemview.findViewById(R.id.in_check)
        var out_check: SmoothCheckBox=itemview.findViewById(R.id.out_check)
        var data_search_check: SmoothCheckBox=itemview.findViewById(R.id.data_search_check)
        var add_check: SmoothCheckBox=itemview.findViewById(R.id.add_check)
        var delete_check: SmoothCheckBox=itemview.findViewById(R.id.delete_check)
    }
    class ViewHolder_Add(itemview: View):ViewHolder(itemview){
          var add_img=itemview.findViewById<ImageView>(R.id.add_title)
    }
    override fun getItemViewType(position: Int): Int {
        // 最后一个item设置为FooterView
        return if (position == list.size) {
            TYPETWO
        } else {
          TYPEONE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view:View
        var viewHolder:ViewHolder
        if(viewType==TYPEONE){
            view= LayoutInflater.from(parent.context).inflate(R.layout.title_manager_item,parent,false)
            viewHolder=ViewHolder_Tilte(view)
        }else{
            view= LayoutInflater.from(parent.context).inflate(R.layout.add_title,parent,false)
            viewHolder=ViewHolder_Add(view)
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position==list.size){
            holder.itemView.setOnClickListener {
                list.add("1")
                notifyDataSetChanged()
            }
        }
        else{
            var OnCheckedChangeListener= SmoothCheckBox.OnCheckedChangeListener{checkBox, isChecked->
                if(isChecked){
                    XToast.warning(activity, "已勾选").show()
                }else{
                    XToast.warning(activity, "已取消").show()
                }
            }
            holder.itemView.in_check.setOnCheckedChangeListener(OnCheckedChangeListener)
            holder.itemView.out_check.setOnCheckedChangeListener(OnCheckedChangeListener)
            holder.itemView.add_check.setOnCheckedChangeListener(OnCheckedChangeListener)
            holder.itemView.data_search_check.setOnCheckedChangeListener(OnCheckedChangeListener)
            holder.itemView.delete_check.setOnCheckedChangeListener(OnCheckedChangeListener)
        }
    }
    override fun getItemCount(): Int {
        return list.size+1
    }
}