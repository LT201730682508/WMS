package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Imformation

import com.example.WMS.BaseCallback
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Member_Manager_Model
import com.example.WMS.OkHttpHelper
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException

class Member_Imformation_Model {

    companion object{
        fun modify_member_title(modify_params:modify_params) {
            val ok = OkHttpHelper.getInstance()
            ok.post_for_object(
                "http://121.199.22.134:8003/api-authority/changeStaffInfo",modify_params,
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

    data class modify_params(val token:String,val warehouseId:Int,val userId:Int,val role:String)
}