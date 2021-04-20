package com.example.WMS.MyFragment.Data_report.Ware_in_Record

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.WMS.*
import com.example.WMS.MyFragment.Data_report.Ware_out_Record.Show_Sure
import com.example.WMS.MyFragment.Data_report.Ware_out_Record.Ware_Out_Record_Dialog
import com.example.WMS.MyFragment.Data_report.Ware_out_Record.Ware_out_Record_Model
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Join_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Member_Manager_Model
import com.example.WMS.MyFragment.Warehouse.Warehouse_Authority_List
import com.example.WMS.MyFragment.Warehouse.Warehouse_authority_Model
import com.example.WMS.domain.DataBean
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener
import com.xuexiang.xui.widget.toast.XToast
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Ware_out_Record_Fragment :Fragment(){
    lateinit var baseTopbar:Base_Topbar
    lateinit var ware_spinner:TextView
    lateinit var ware_in_recycle: MyListView
    lateinit var select_title:String
    lateinit var start_time:TextView
    lateinit var end_time:TextView
    lateinit var start:Date
    lateinit var end:Date
    lateinit var select_product: RelativeLayout
    lateinit var product_spinner:Spinner
    lateinit var select_execute: RelativeLayout
    lateinit var execute_spinner:Spinner
    lateinit var select_time:RelativeLayout
    lateinit var select_what:RadioGroup
    lateinit var product:RadioButton
    lateinit var executer:RadioButton
    lateinit var time:RadioButton
    var warehouse_id=0
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
        select_what=view.findViewById(R.id.select_what_out)
        product=view.findViewById(R.id.product)
        executer=view.findViewById(R.id.executer)
        time=view.findViewById(R.id.time)
        select_product=view.findViewById(R.id.select_product)
        product_spinner=view.findViewById(R.id.product_spinner)
        select_execute=view.findViewById(R.id.select_execute)
        execute_spinner=view.findViewById(R.id.execute_spinner)
        start_time=view.findViewById(R.id.start_time)
        end_time=view.findViewById(R.id.end_time)
        select_time=view.findViewById(R.id.select_time)
        ware_spinner.setOnClickListener {
            var wareOutRecordDialog=Ware_Out_Record_Dialog(requireContext(),object :Show_Sure{
                override fun show(str:String) {
                    ware_spinner.text=str
                    select_what.clearCheck()
                    select_execute.visibility=View.GONE
                    select_product.visibility=View.GONE
                    select_time.visibility=View.GONE
                }

            },object :Ware_out_Record_Model.Ware_Record{
                override fun result(record_list: Array<Ware_out_Record_Model.Out_Record>,wareid: Int) {
                            select_what.visibility=View.VISIBLE
                            warehouse_id=wareid
                             var wareOutAdapter = Ware_out_Adapter(
                              record_list,
                             activity as MainActivity
                            )
                           ware_in_recycle.adapter = wareOutAdapter
                }

            },(activity as MainActivity).fragment_Manager.userinfo)
            wareOutRecordDialog.show()
        }
//        Join_Warehouse_Model.getData(object : Join_Warehouse_Model.Show{
//            override fun show(wares: ArrayList<All_Warehouse_Model.Warehouse>) {
//                if (wares.size!=0){
//                    var mList = ArrayList<String>()
//                    mList.add("请选择")
//                    for (ware in wares){
//                        mList.add(ware.warehouseName)
//                    }
//                    var arrayAdapter= ArrayAdapter<String>(activity as MainActivity,R.layout.member_title_spinner,mList)
//                    arrayAdapter.setDropDownViewResource(R.layout.item_dropdown)
//                    ware_spinner.adapter=arrayAdapter
//                    ware_spinner.prompt="请选择仓库"
//                    ware_spinner.onItemSelectedListener=object :
//                        AdapterView.OnItemSelectedListener {
//                        override fun onItemSelected(
//                            parent: AdapterView<*>?,
//                            view: View,
//                            position: Int,
//                            id: Long
//                        ) {
//                            select_title=mList[position]
//                            if (position>0){
//                                select_what.clearCheck()
//                                select_execute.visibility=View.GONE
//                                select_product.visibility=View.GONE
//                                select_time.visibility=View.GONE
//                                var my_authority=Warehouse_Authority_List.authorityList_Map.get(wares.get(position-1).warehouseId.toString()+(activity as MainActivity).fragment_Manager.userinfo.token)
//                                if(my_authority==null){
//                                    Warehouse_authority_Model.getRole(wares.get(position-1).warehouseId,(activity as MainActivity).fragment_Manager.userinfo.token,object :Warehouse_authority_Model.getRole{
//                                        override fun get(authority: Warehouse_authority_Model.authority) {
//                                            my_authority=authority.authorities
//                                            Warehouse_Authority_List.authorityList_Map.put(wares.get(position-1).warehouseId.toString()+(activity as MainActivity).fragment_Manager.userinfo.token,authority.authorities)
//                                            Warehouse_Authority_List.roleList_Map.put(wares.get(position-1).warehouseId.toString()+(activity as MainActivity).fragment_Manager.userinfo.token,authority.role)
//                                            if(authority.authorities.contains('f')) {
//                                                select_what.visibility=View.VISIBLE
//                                                warehouse_id= wares.get(position - 1).warehouseId
//                                                Ware_out_Record_Model.getData(
//                                                    wares.get(position - 1).warehouseId,
//                                                    object : Ware_out_Record_Model.Ware_Record {
//                                                        override fun result(record_list: Array<Ware_out_Record_Model.Out_Record>) {
//                                                            var wareOutAdapter = Ware_out_Adapter(
//                                                                record_list,
//                                                                activity as MainActivity
//                                                            )
//                                                            ware_in_recycle.adapter = wareOutAdapter
//                                                        }
//                                                    },
//                                                    (activity as MainActivity).fragment_Manager.userinfo
//                                                )
//                                            }else{
//                                                XToast.warning(requireContext(),"您没有相关权限").show()
//                                            }
//                                        }
//
//                                    })
//                                }else{
//                                    if(my_authority!!.contains("f")) {
//                                        select_what.visibility=View.VISIBLE
//                                        warehouse_id=wares.get(position-1).warehouseId
//                                        Ware_out_Record_Model.getData(
//                                            wares.get(position - 1).warehouseId,
//                                            object : Ware_out_Record_Model.Ware_Record {
//                                                override fun result(record_list: Array<Ware_out_Record_Model.Out_Record>) {
//                                                    var wareOutAdapter = Ware_out_Adapter(
//                                                        record_list,
//                                                        activity as MainActivity
//                                                    )
//                                                    ware_in_recycle.adapter = wareOutAdapter
//                                                }
//                                            },
//                                            (activity as MainActivity).fragment_Manager.userinfo
//                                        )
//                                    }else{
//                                        XToast.warning(requireContext(),"您没有相关权限").show()
//                                    }
//                                }
//                                }else{
//                                select_what.visibility=View.GONE
//                                select_execute.visibility=View.GONE
//                                select_product.visibility=View.GONE
//                                select_time.visibility=View.GONE
//                            }
//
//
//                        }
//                        override fun onNothingSelected(parent: AdapterView<*>?) {}
//                    }
//                }
//
//            }},(activity as MainActivity).fragment_Manager.userinfo)





        start_time.setOnClickListener {
            getTimerPicker(start_time)
        }
        end_time.setOnClickListener {
            getTimerPicker(end_time)
        }
        select_what.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId==product.id){
                select_product.visibility=View.VISIBLE
                select_execute.visibility=View.GONE
                select_time.visibility=View.GONE
                getProductList(warehouse_id)
            }
            if(checkedId==executer.id){
                select_product.visibility=View.GONE
                select_execute.visibility=View.VISIBLE
                select_time.visibility=View.GONE
                getExecuterList(warehouse_id)
            }
            if(checkedId==time.id){
                select_product.visibility=View.GONE
                select_execute.visibility=View.GONE
                select_time.visibility=View.VISIBLE
            }
        }
    }
    fun getProductList(wareid:Int){
        val ok = OkHttpHelper.getInstance()
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getInInventoryProductByWarehouseId/" + wareid + "?userToken=" +(activity as MainActivity).fragment_Manager.userinfo.token,
            object : BaseCallback<DataBean.ProductIn?>() {
                override fun onFailure(
                    request: Request,
                    e: IOException
                ) {
                    println("failure$e")
                }

                override fun onResponse(response: Response) {
                    println("@@@@@@@@@@1$response")
                }

                override fun onSuccess_List(resultStr: String) {
                    val gson = Gson()
                    val list = gson.fromJson(
                        resultStr,
                        Array<DataBean.ProductIn>::class.java
                    )
                    setProductSpinner(list)
                }



                override fun onError(
                    response: Response,
                    code: Int,
                    e: Exception
                ) {
                    println("error$response$e")
                }

                override fun onSuccess(response: Response?, t: DataBean.ProductIn?) {
                    TODO("Not yet implemented")
                }
            })
    }
    fun setProductSpinner(list: Array<DataBean.ProductIn>){
        var mlist= arrayListOf<String>()
        mlist.add("所有")
        for(l in list){
            mlist.add(l.productName)
        }
        var arrayAdapter= ArrayAdapter<String>(activity as MainActivity,R.layout.member_title_spinner,mlist)
        arrayAdapter.setDropDownViewResource(R.layout.item_dropdown)
        product_spinner.adapter=arrayAdapter
        product_spinner.prompt="请选择商品"
        product_spinner.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position>0){
                    Ware_out_Record_Model.getDataWithProduct(warehouse_id,mlist[position].toString(),(activity as MainActivity).fragment_Manager.userinfo.token,object :Ware_out_Record_Model.Ware_Record{
                        override fun result(record_list: Array<Ware_out_Record_Model.Out_Record>,wareid: Int) {
                            var wareOutAdapter = Ware_out_Adapter(
                                record_list,
                                activity as MainActivity
                            )
                            ware_in_recycle.adapter = wareOutAdapter
                        }

                    })
                }
               else{
                    Ware_out_Record_Model.getData(
                        warehouse_id,
                        object : Ware_out_Record_Model.Ware_Record {
                            override fun result(record_list: Array<Ware_out_Record_Model.Out_Record>,wareid: Int) {
                                var wareOutAdapter = Ware_out_Adapter(
                                    record_list,
                                    activity as MainActivity
                                )
                                ware_in_recycle.adapter = wareOutAdapter
                            }
                        },
                        (activity as MainActivity).fragment_Manager.userinfo
                    )
                }
            }
        }
    }


    fun getExecuterList(wareid: Int){
        val ok = OkHttpHelper.getInstance()
        ok.get_for_list(
            "http://121.199.22.134:8003/api-authority/getAllStaff/"+wareid+"?userToken="+(activity as MainActivity).fragment_Manager.userinfo.token,
            object : BaseCallback<String>() {
                override fun onFailure(
                    request: Request,
                    e: IOException
                ) {
                    println("failure$e")
                }

                override fun onResponse(response: Response) {
                    println("response$response")
                }

                override fun onSuccess_List(resultStr: String) {
                    val gson = Gson()
                    val list = gson.fromJson(
                        resultStr,
                        Array<Member_Manager_Model.member_item>::class.java
                    )
                    setExecuterSpinner(list)
                }
                override fun onError(
                    response: Response,
                    code: Int,
                    e: Exception
                ) {
                    println("error$response$e")
                }

                override fun onSuccess(response: Response?, t: String?) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun setExecuterSpinner(list: Array<Member_Manager_Model.member_item>){
        var mlist= arrayListOf<String>()
        mlist.add("所有")
        for(l in list){
            mlist.add(l.user_name)
        }
        var arrayAdapter= ArrayAdapter<String>(activity as MainActivity,R.layout.member_title_spinner,mlist)
        arrayAdapter.setDropDownViewResource(R.layout.item_dropdown)
        execute_spinner.adapter=arrayAdapter
        execute_spinner.prompt="请选择执行人"
        execute_spinner.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position>0){
                    Ware_out_Record_Model.getDataWithExecuter(warehouse_id,mlist[position],(activity as MainActivity).fragment_Manager.userinfo.token,object :Ware_out_Record_Model.Ware_Record{
                        override fun result(record_list: Array<Ware_out_Record_Model.Out_Record>,wareid: Int) {
                            var wareOutAdapter = Ware_out_Adapter(
                                record_list,
                                activity as MainActivity
                            )
                            ware_in_recycle.adapter = wareOutAdapter
                        }

                    })
                }else{
                    Ware_out_Record_Model.getData(
                       warehouse_id,
                        object : Ware_out_Record_Model.Ware_Record {
                            override fun result(record_list: Array<Ware_out_Record_Model.Out_Record>,wareid: Int) {
                                var wareOutAdapter = Ware_out_Adapter(
                                    record_list,
                                    activity as MainActivity
                                )
                                ware_in_recycle.adapter = wareOutAdapter
                            }
                        },
                        (activity as MainActivity).fragment_Manager.userinfo
                    )
                }

            }
        }
    }
    fun getTimerPicker( view:TextView){
        var calendar = Calendar.getInstance()
        var mTimePicker = TimePickerBuilder(context,
            OnTimeSelectListener { date, v ->
                if(start_time.text=="起始时间"){
                    if(view==end_time){
                        view.text= SimpleDateFormat("YYYY-MM-dd").format(date)
                        end=date
                    }
                    else {
                        if(end_time.text=="终止时间"||end>=date){
                            view.text =SimpleDateFormat("YYYY-MM-dd").format(date)
                            start = date
                        }else{
                            XToast.warning(requireContext(), "请选择正确的时间").show()
                        }
                    }
                }else{
                    if(view==start_time&&(end_time.text=="终止时间"||end>=date)){
                        view.text= SimpleDateFormat("YYYY-MM-dd").format(date)
                    }else{
                        if(view==start_time&&date<=end){
                            view.text= SimpleDateFormat("YYYY-MM-dd").format(date)
                            start=date
                        }else{
                            if(view==start_time&&date>end){
                                XToast.warning(requireContext(), "请选择正确的时间").show()
                            }else if(date<start){
                                XToast.warning(requireContext(), "请选择正确的时间").show()
                            }else{
                                view.text= SimpleDateFormat("YYYY-MM-dd").format(date)
                                end=date
                            }

                        }
                    }
                }
                if(::start.isInitialized&&::end.isInitialized){
                    println("@@@@@123")
                  Ware_out_Record_Model.getDataWithTime(warehouse_id,(activity as MainActivity).fragment_Manager.userinfo.token,start_time.text.toString(),end_time.text.toString(),object :Ware_out_Record_Model.Ware_Record{
                      override fun result(record_list: Array<Ware_out_Record_Model.Out_Record>,wareid: Int) {
                          var wareOutAdapter = Ware_out_Adapter(
                              record_list,
                              activity as MainActivity
                          )
                          ware_in_recycle.adapter = wareOutAdapter
                      }

                  })
                }

            })
            .setTimeSelectChangeListener { Log.i("pvTime", "onTimeSelectChanged") }
            .setType(TimePickerType.DEFAULT)
            .setTitleText("时间选择")
            .isDialog(true)
            .setOutSideCancelable(false)
            .setDate(calendar)
            .build()
        mTimePicker.show()
    }
}