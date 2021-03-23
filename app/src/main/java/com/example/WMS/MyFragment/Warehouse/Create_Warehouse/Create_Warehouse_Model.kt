package com.example.WMS.MyFragment.Warehouse.Create_Warehouse

import com.example.WMS.BaseCallback
import com.example.WMS.MyFragment.Login_fragment
import com.example.WMS.OkHttpHelper
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.File
import java.io.IOException
import java.lang.Exception

class Create_Warehouse_Model {

    companion object{
        fun getData(parms: Map<String,String>, file:File,img:String,show: Show,userLogin: Login_fragment.user_Login){
            var okHttpHelper= OkHttpHelper.getInstance()
            okHttpHelper.post_for_form("http://121.199.22.134:8003/api-inventory/addWarehouse" +"?userToken="+userLogin.token ,parms,file,img,object :
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
                    println("@@@@@4"+response+code+e)
                }

            })
        }

    }
    interface Show{
        fun show(str:String)
    }
}