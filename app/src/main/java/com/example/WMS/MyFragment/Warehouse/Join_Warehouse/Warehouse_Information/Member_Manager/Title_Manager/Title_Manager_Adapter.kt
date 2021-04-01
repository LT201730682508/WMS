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

class Title_Manager_Adapter(
    val warehouseId: Int,
    var list: Array<Title_Manager_Model.titleItem>,
    var activity: MainActivity): RecyclerView.Adapter<Title_Manager_Adapter.ViewHolder>() {
    val TYPEONE=1
    val TYPETWO=2
    open class ViewHolder(itemview:View): RecyclerView.ViewHolder(itemview) {
    }
    class ViewHolder_Tilte(itemview: View):ViewHolder(itemview){
        var makesure:Button=itemview.findViewById(R.id.title_make_sure)
        var title:EditText=itemview.findViewById(R.id.title)
        var in_check: SmoothCheckBox=itemview.findViewById(R.id.in_check)
        var out_check: SmoothCheckBox=itemview.findViewById(R.id.out_check)
        var data_search_check: SmoothCheckBox=itemview.findViewById(R.id.data_search_check)
        var add_check: SmoothCheckBox=itemview.findViewById(R.id.add_check)
        var delete_check: SmoothCheckBox=itemview.findViewById(R.id.delete_check)
        var manager_check:SmoothCheckBox=itemview.findViewById(R.id.manager_check)
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
//                var item=Title_Manager_Model.titleItem(1,"员工"+(list.size+1),"a")
//                list.plus(item)
                var addParams=Title_Manager_Model.addParams(warehouseId,"员工"+(list.size+1),"a")
                Title_Manager_Model.add_title(addParams,(activity as MainActivity).fragment_Manager.userinfo.token,object :Title_Manager_Model.modify_show{
                    override fun show(string: String) {
                        if(string=="OK"){
                            XToast.success(activity,"新增成功").show()
                            Title_Manager_Model.get_title_list((activity as MainActivity).fragment_Manager.userinfo.token,warehouseId,object :Title_Manager_Model.titleShow{
                                override fun show(listnew: Array<Title_Manager_Model.titleItem>) {
                                     list=listnew
                                    notifyDataSetChanged()
                                }
                            })
                        }else{
                            XToast.warning(activity,string).show()
                        }
                    }

                    override fun error(string: String) {
                        XToast.warning(activity,"新增失败").show()
                    }
                })

            }
        }
        else{
//            holder.itemView.in_check.setOnCheckedChangeListener(OnCheckedChangeListener)
//            holder.itemView.out_check.setOnCheckedChangeListener(OnCheckedChangeListener)
//            holder.itemView.add_check.setOnCheckedChangeListener(OnCheckedChangeListener)
//            holder.itemView.data_search_check.setOnCheckedChangeListener(OnCheckedChangeListener)
//            holder.itemView.delete_check.setOnCheckedChangeListener(OnCheckedChangeListener)
            holder.itemView.title.setText(list[position].role)
            holder.itemView.title_make_sure.setOnClickListener {
                var str= arrayListOf<Char>()
                if (holder.itemView.in_check.isChecked){
                    str.add('b') }
                if (holder.itemView.out_check.isChecked){
                str.add('c') }
                if (holder.itemView.add_check.isChecked){
                    str.add('d') }
                if (holder.itemView.manager_check.isChecked){
                    str.add('e') }
                if (holder.itemView.data_search_check.isChecked){
                    str.add('f') }
                if (holder.itemView.delete_check.isChecked){
                    str.add('g') }

                var changeParams=Title_Manager_Model.changeParams(list[position].id,holder.itemView.title.text.toString(),str.toString())
                Title_Manager_Model.modify_member_title(changeParams,(activity as MainActivity).fragment_Manager.userinfo.token,object :Title_Manager_Model.modify_show{
                    override fun show(string: String) {
                        if(string=="OK"){
                            XToast.success(activity,string).show()
                        }else
                        {
                            XToast.warning(activity,string).show()
                        }
                    }

                    override fun error(string: String) {
                        XToast.success(activity,"已存在该名称，请更换").show()
                    }
                })
            }
            if(list[position].authorities.contains("b")){
                holder.itemView.in_check.isChecked=true
            }else{
                holder.itemView.in_check.isChecked=false
            }
            if(list[position].authorities.contains("c")){
                holder.itemView.out_check.isChecked=true
            }else{
                holder.itemView.out_check.isChecked=false
            }
            if(list[position].authorities.contains("d")){
                holder.itemView.add_check.isChecked=true
            }else{
                holder.itemView.add_check.isChecked=false
            }
            if(list[position].authorities.contains("e")){
                holder.itemView.manager_check.isChecked=true
            }else{
                holder.itemView.manager_check.isChecked=false
            }
            if(list[position].authorities.contains("f")){
                holder.itemView.data_search_check.isChecked=true
            }else{
                holder.itemView.data_search_check.isChecked=false
            }
            if(list[position].authorities.contains("g")){
                holder.itemView.delete_check.isChecked=true
            }else{
                holder.itemView.delete_check.isChecked=false
            }

        }
    }
    override fun getItemCount(): Int {
        return list.size+1
    }




}


