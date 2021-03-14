package com.example.WMS.MyFragment.Warehouse.Create_Warehouse

import com.example.WMS.BaseCallback
import com.example.WMS.OkHttpHelper
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException
import java.lang.Exception

class Create_Warehouse_Model {

    companion object{
        fun getData(parms: Create_Warehouse_Fragment.Create_Warehouse_params, show: Show){
            var okHttpHelper= OkHttpHelper.getInstance()
            okHttpHelper.post_for_object("http://121.199.22.134:8003/api-inventory/addWarehouse/"+parms.warehouseName+"/"+1,parms,object :
                BaseCallback<String>(){
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
                    show.show(t!!)
                }

                override fun onError(response: Response?, code: Int, e: Exception?) {
                    println("@@@@@4"+code+e)
                }

            })
        }

    }
    interface Show{
        fun show(str:String)
    }
}