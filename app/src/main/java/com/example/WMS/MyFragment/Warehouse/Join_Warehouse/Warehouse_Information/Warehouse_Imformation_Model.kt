package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information

import com.example.WMS.BaseCallback
import com.example.WMS.MyFragment.Login_fragment
import com.example.WMS.MyFragment.Warehouse.Create_Warehouse.Create_Warehouse_Fragment
import com.example.WMS.MyFragment.Warehouse.Create_Warehouse.Create_Warehouse_Model
import com.example.WMS.OkHttpHelper
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException
import java.lang.Exception
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class Warehouse_Imformation_Model {
    companion object{
        fun getData_Delete(warehouseId: Int, show: Warehouse_imfor_Show,userLogin: Login_fragment.user_Login){
            var okHttpHelper= OkHttpHelper.getInstance()
            okHttpHelper.post_for_object("http://121.199.22.134:8003/api-inventory/deleteWarehouseByWarehouseId?userToken="+userLogin.token,warehouseId,object :
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
        fun getData_Modefy(parms:Any, show: Warehouse_imfor_Show,userLogin: Login_fragment.user_Login){
            var okHttpHelper= OkHttpHelper.getInstance()
            okHttpHelper.post_for_object("http://121.199.22.134:8003/api-inventory/modifyWarehouseName?userToken="+userLogin.token,parms,object :
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

    data class Modify_Params(var warehouseId:Int,var warehouseName:String)

    interface Warehouse_imfor_Show{
        fun show(str:String)
    }
}