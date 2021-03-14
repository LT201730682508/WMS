package com.example.WMS.MyFragment.Data_report.Ware_in_Record

import com.example.WMS.BaseCallback
import com.example.WMS.OkHttpHelper
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException
import java.lang.Exception

class Ware_In_Record_Model  {
    companion object{
        fun getData(parms: Any, result:Ware_Record  ){
            var okHttpHelper= OkHttpHelper.getInstance()
            okHttpHelper.get_for_list("http://121.199.22.134:8003/api-inventory/getInWarehouseRecordByWarehouseId/2",object :
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

    data class In_Record(val inId:Int,val userId:Int,val productId:Int,val productName:String,val productImg:String,val warehouseId:Int,val warehouseName:String,val amount:Int,val inPrice:Double,val supplierId:Int,val note:String,val createTime:String)
}