package com.example.WMS.MyFragment.Data_report.Ware_in_Record

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.WMS.BaseCallback
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Login_fragment
import com.example.WMS.OkHttpHelper
import com.example.WMS.R
import com.example.WMS.domain.DataBean
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException
import java.lang.Exception

class Ware_In_Record_Model  {
    companion object{
        fun getData(warehouseId: Int, result:Ware_Record,userLogin: Login_fragment.user_Login ){
            var okHttpHelper= OkHttpHelper.getInstance()
            okHttpHelper.get_for_list("http://121.199.22.134:8003/api-inventory/getInWarehouseRecordByWarehouseId/"+warehouseId+"?userToken="+userLogin.token,object :
                BaseCallback<In_Record>(){
                override fun onFailure(request: Request?, e: IOException?) {
                    println("@@@@@1"+e)
                }
                override fun onResponse(response: Response?) {
                    println("@@@@@2"+response)
                }

                override fun onSuccess_List(resultStr: String?) {
                    println("@@@@@3"+resultStr)
                    val gson = Gson()
                    val record_list = gson.fromJson(
                        resultStr,
                        Array<In_Record>::class.java
                    )
                    result.result(record_list)
                }


                override fun onError(response: Response?, code: Int, e: Exception?) {
                    println("@@@@@4"+code+e)
                }

                override fun onSuccess(response: Response?, t: In_Record?) {
                    TODO("Not yet implemented")
                }

            })
        }
        }


    interface Ware_Record{
        fun result(record_list:Array<In_Record>)
    }

    data class In_Record(val inId:Int,val userId:Int,val productId:Int,val productName:String,val userName:String,val productImg:String,val warehouseId:Int,val warehouseName:String,val amount:Int,val inPrice:Double,val supplierId:Int,val supplierName:String,val note:String,val createTime:String)
}