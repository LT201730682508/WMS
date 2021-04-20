package com.example.WMS.MyFragment.Data_report.Ware_out_Record

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Data_report.Ware_in_Record.Ware_out_Adapter
import com.example.WMS.MyFragment.Login_fragment
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Join_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Warehouse_Authority_List
import com.example.WMS.MyFragment.Warehouse.Warehouse_authority_Model
import com.example.WMS.R
import com.xuexiang.xui.widget.toast.XToast

class Ware_Out_Record_Dialog( context: Context, var showSure: Show_Sure,var result: Ware_out_Record_Model.Ware_Record, var userLogin: Login_fragment.user_Login) : Dialog(context, R.style.CustomDialog) {
    lateinit var search_text:EditText
    lateinit var search_img:ImageView
    lateinit var record_ry:RecyclerView
    lateinit var arrayAdapter:Ware_Out_Ware_Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var view= View.inflate(context, R.layout.record_xml, null)
        setContentView(view)
        search_text=view.findViewById(R.id.search_text)
        search_img=view.findViewById(R.id.search_img)
        record_ry=view.findViewById(R.id.record_ry)
        getWare()
        search_img.setOnClickListener {
            if(!search_text.text.isEmpty()){
                var arrayList= arrayListOf<All_Warehouse_Model.Warehouse>()
                for(l in arrayAdapter.list){
                    if (l.warehouseName.contains(search_text.text.toString())){
                        arrayList.add(l)
                    }
                }
                arrayAdapter.list=arrayList
                arrayAdapter.notifyDataSetChanged()
            }else{
                getWare()
            }
        }



    //    Ware_out_Record_Model.getData(warehouseid,result,userLogin)
    }

    fun getWare(){
        Join_Warehouse_Model.getData(object : Join_Warehouse_Model.Show {
            override fun show(wares: ArrayList<All_Warehouse_Model.Warehouse>) {
                if (wares.size != 0) {
                    var mList = ArrayList<All_Warehouse_Model.Warehouse>()
                    mList.addAll(wares)
                    arrayAdapter=Ware_Out_Ware_Adapter(mList,result,showSure,userLogin,this@Ware_Out_Record_Dialog)
                    record_ry.layoutManager=LinearLayoutManager(context)
                    record_ry.adapter=arrayAdapter
                }
            }
        },userLogin)
    }
}

interface Show_Sure{
    fun show(str:String)
}