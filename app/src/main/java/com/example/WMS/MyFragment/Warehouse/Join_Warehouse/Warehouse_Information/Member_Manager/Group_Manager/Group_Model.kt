package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Group_Manager

import com.example.WMS.BaseCallback
import com.example.WMS.OkHttpHelper
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException

class Group_Model {
    companion object {
        fun get_group_list(token: String, warehouseId: Int, show: GroupShow) {
            val ok = OkHttpHelper.getInstance()
            ok.get_for_list(
                "http://121.199.22.134:8003/api-authority/getGroupListByWarehouseId?userToken=" + token+"&warehouseId="+warehouseId,
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
                        val groupList = gson.fromJson(
                            resultStr,
                            Array<Gropu_data>::class.java
                        )
                        show.show(groupList)
                        println("@@2@@@2" + resultStr)
                    }

                    override fun onError(
                        response: Response,
                        code: Int,
                        e: Exception
                    ) {
                        println("error$response$e")
                    }

                    override fun onSuccess(response: Response?, t: String?) {

                    }
                })
        }
    }

    interface GroupShow{
        fun show(g: Array<Gropu_data>)
    }
    data class Gropu_data(val group_id:Int,val group_name:String,val account:Int)
}