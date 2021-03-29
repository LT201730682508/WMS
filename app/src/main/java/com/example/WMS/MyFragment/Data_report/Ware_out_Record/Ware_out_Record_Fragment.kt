package com.example.WMS.MyFragment.Data_report.Ware_in_Record

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.WMS.*
import com.example.WMS.MyFragment.Data_report.Ware_out_Record.Ware_out_Record_Model
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Join_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Warehouse_Authority_List
import com.example.WMS.MyFragment.Warehouse.Warehouse_authority_Model
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener
import com.xuexiang.xui.widget.toast.XToast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class Ware_out_Record_Fragment :Fragment(){
    lateinit var baseTopbar:Base_Topbar
    lateinit var ware_spinner:Spinner
    lateinit var ware_in_recycle: MyListView
    lateinit var select_title:String
//    lateinit var start_time:TextView
//    lateinit var end_time:TextView
//    lateinit var start:Date
//    lateinit var end:Date
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.ware_out_record,container,false)
        init(view)
        return view
    }
    fun init(view:View){
        baseTopbar= Base_Topbar(view, activity  as MainActivity,true)
        baseTopbar.setTitle("数据报表")
        ware_spinner=view.findViewById(R.id.ware_spinner)
        ware_in_recycle=view.findViewById(R.id.ware_in_recycle)
//        start_time=view.findViewById(R.id.start_time)
//        end_time=view.findViewById(R.id.end_time)



        Join_Warehouse_Model.getData(object : Join_Warehouse_Model.Show{
            override fun show(wares: Array<All_Warehouse_Model.Warehouse>) {
                if (wares.size!=0){
                    var mList = ArrayList<String>()
                    mList.add("请选择")
                    for (ware in wares){
                        mList.add(ware.warehouseName)
                    }
                    var arrayAdapter= ArrayAdapter<String>(activity as MainActivity,R.layout.member_title_spinner,mList)
                    arrayAdapter.setDropDownViewResource(R.layout.item_dropdown)
                    ware_spinner.adapter=arrayAdapter
                    ware_spinner.prompt="请选择仓库"
                    ware_spinner.onItemSelectedListener=object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            select_title=mList[position]
                            if (position>0){
                                var my_authority=Warehouse_Authority_List.authorityList_Map.get(wares.get(position-1).warehouseId)
                                if(my_authority==null){
                                    Warehouse_authority_Model.getRole(wares.get(position-1).warehouseId,(activity as MainActivity).fragment_Manager.userinfo.token,object :Warehouse_authority_Model.getRole{
                                        override fun get(authority: Warehouse_authority_Model.authority) {
                                            my_authority=authority.authorities
                                            Warehouse_Authority_List.authorityList_Map.put(wares.get(position-1).warehouseId,authority.authorities)
                                            Warehouse_Authority_List.roleList_Map.put(wares.get(position-1).warehouseId,authority.role)
                                            if(my_authority!!.contains("f")) {
                                                Ware_out_Record_Model.getData(
                                                    wares.get(position - 1).warehouseId,
                                                    object : Ware_out_Record_Model.Ware_Record {
                                                        override fun result(record_list: Array<Ware_out_Record_Model.Out_Record>) {
                                                            var wareOutAdapter = Ware_out_Adapter(
                                                                record_list,
                                                                activity as MainActivity
                                                            )
                                                            ware_in_recycle.adapter = wareOutAdapter
                                                        }
                                                    },
                                                    (activity as MainActivity).fragment_Manager.userinfo
                                                )
                                            }else{
                                                XToast.warning(requireContext(),"您没有相关权限").show()
                                            }
                                        }

                                    })
                                }else{
                                    if(my_authority!!.contains("f")) {
                                        Ware_out_Record_Model.getData(
                                            wares.get(position - 1).warehouseId,
                                            object : Ware_out_Record_Model.Ware_Record {
                                                override fun result(record_list: Array<Ware_out_Record_Model.Out_Record>) {
                                                    var wareOutAdapter = Ware_out_Adapter(
                                                        record_list,
                                                        activity as MainActivity
                                                    )
                                                    ware_in_recycle.adapter = wareOutAdapter
                                                }
                                            },
                                            (activity as MainActivity).fragment_Manager.userinfo
                                        )
                                    }else{
                                        XToast.warning(requireContext(),"您没有相关权限").show()
                                    }
                                }
                                }


                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
                }

            }},(activity as MainActivity).fragment_Manager.userinfo)




//
//        start_time.setOnClickListener {
//            getTimerPicker(start_time)
//        }
//        end_time.setOnClickListener {
//            getTimerPicker(end_time)
//        }
    }
//
//    fun getTimerPicker( view:TextView){
//        var calendar = Calendar.getInstance()
//        var mTimePicker = TimePickerBuilder(context,
//            OnTimeSelectListener { date, v ->
//                if(start_time.text=="起始时间"){
//                    if(view==end_time){
//                        view.text= SimpleDateFormat("YYYY-MM-dd-HH-mm-ss").format(date)
//                        end=date
//                    }
//                    else {
//                        if(end_time.text=="终止时间"||end>=date){
//                            println("时间"+"走了这")
//                            view.text = SimpleDateFormat("YYYY-MM-dd-HH-mm-ss").format(date)
//                            start = date
//                        }else{
//                            XToast.warning(requireContext(), "请选择正确的时间").show()
//                        }
//                    }
//                }else{
//                    if(view==start_time&&(end_time.text=="终止时间"||end>=date)){
//                        view.text=  SimpleDateFormat("YYYY-MM-dd-HH-mm-ss").format(date)
//                    }else{
//                        if(view==start_time&&date<=end){
//                            view.text=  SimpleDateFormat("YYYY-MM-dd-HH-mm-ss").format(date)
//                            start=date
//                        }else{
//                            if(view==start_time&&date>end){
//                                XToast.warning(requireContext(), "请选择正确的时间").show()
//                            }else if(date<start){
//                                XToast.warning(requireContext(), "请选择正确的时间").show()
//                            }else{
//                                view.text= SimpleDateFormat("YYYY-MM-dd-HH-mm-ss").format(date)
//                                end=date
//                            }
//
//                        }
//                    }
//                }
//
//
//            })
//            .setTimeSelectChangeListener { Log.i("pvTime", "onTimeSelectChanged") }
//            .setType(TimePickerType.ALL)
//            .setTitleText("时间选择")
//            .isDialog(true)
//            .setOutSideCancelable(false)
//            .setDate(calendar)
//            .build()
//        mTimePicker.show()
//    }
}