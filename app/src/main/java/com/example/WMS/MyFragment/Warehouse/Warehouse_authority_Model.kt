package com.example.WMS.MyFragment.Warehouse

import com.example.WMS.BaseCallback
import com.example.WMS.OkHttpHelper
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException
import java.lang.Exception

class Warehouse_authority_Model {
    companion object{
        fun getRole(warehouseid:Int,token:String,getRole: getRole){
            var okHttpHelper= OkHttpHelper.getInstance()
            okHttpHelper.get_for_object("http://121.199.22.134:8003/api-authority/getAuthoritiesOfUser?userToken="+token+"&warehouseId="+warehouseid,object :
                BaseCallback<authority>(){
                override fun onFailure(request: Request?, e: IOException?) {
                    println("@@@@@1"+e)
                }
                override fun onResponse(response: Response?) {
                    println("@@@@@2"+response)
                }

                override fun onSuccess_List(resultStr: String?) {
                    println("@@@@@3"+resultStr)
                }

                override fun onSuccess(response: Response?, t: authority?) {
                    println("@@@@@3"+t)
                    getRole.get(t!!)
                }
                override fun onError(response: Response?, code: Int, e: Exception?) {
                    println("@@@@@4"+code+e)

                }
            })
        }
    }
    interface getRole{
        fun get(authority: authority)
    }
    data class authority(val role:String,val authorities: String)
}