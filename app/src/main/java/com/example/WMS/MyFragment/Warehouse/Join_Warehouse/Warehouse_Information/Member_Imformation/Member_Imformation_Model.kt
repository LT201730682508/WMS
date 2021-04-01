package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Imformation

import com.example.WMS.BaseCallback
import com.example.WMS.OkHttpHelper
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.xuexiang.xui.widget.toast.XToast
import java.io.IOException

class Member_Imformation_Model {

    companion object{
        fun modify_member_title(token:String,modify_params:modify_params,result:result) {
            val ok = OkHttpHelper.getInstance()
            ok.post_for_object(
                "http://121.199.22.134:8003/api-authority/changeStaffInfo?userToken="+token,modify_params,
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
                        result.result(t!!)
                    }
                })
        }

    }

    data class modify_params(

        val warehouse_id: Int,
        val user_name:String,
        val role:String
    )
    interface result{
        fun result(string: String)
    }
}