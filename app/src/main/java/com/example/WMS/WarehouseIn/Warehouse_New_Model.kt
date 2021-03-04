package com.example.WMS.WarehouseIn

import android.os.Handler
import android.os.Looper
import com.example.WMS.BaseCallback
import com.example.WMS.OkHttpHelper
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException
import java.lang.Exception


class Warehouse_New_Model {
    companion object{
         fun getData( parms:Object,show:show){


             var okHttpHelper=OkHttpHelper.getInstance()
             okHttpHelper.post_for_object("http://172.21.245.42:8003/api-order/inWarehouse",parms,object :BaseCallback<String>(){
                 override fun onFailure(request: Request?, e: IOException?) {
                    println("@@@@@1"+e)
                 }

                 override fun onResponse(response: Response?) {
                     println("@@@@@2"+response)
                 }

                 override fun onSuccess_List(resultStr: String?) {
                     println("@@@@@3"+resultStr)
                 }

                 override fun onSuccess(response: Response?, t: String?) {
                     println("@@@@@3"+t)
                     var hander = Handler(Looper.myLooper()!!)
                         hander.post{
                             show.show(t!!)
                         }
                 }

                 override fun onError(response: Response?, code: Int, e: Exception?) {
                     println("@@@@@4"+code+e)
                 }

             })
         }

    }
 interface show{
     fun show(string: String)
 }
    data class post_data(var id:Int,var supplierId:Int,var amount:Int,var inPrice:Int,var note:String)
}