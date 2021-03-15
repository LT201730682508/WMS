package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Title_Manager

import com.example.WMS.BaseCallback
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Imformation.Member_Imformation_Model
import com.example.WMS.OkHttpHelper
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException

class Title_Manager_Model {

    companion object{
        fun get_title_list(token: String,warehouseId:Int,show:titleShow) {
            val ok = OkHttpHelper.getInstance()
            ok.get_for_list(
                "http://121.199.22.134:8003/api-authority/getAllRole/"+token+"/"+warehouseId,
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
                        val titleList = gson.fromJson(
                            resultStr,
                            Array<titleItem>::class.java
                        )
                        show.show(titleList)
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

        fun modify_member_title(changeParams: changeParams) {
            val ok = OkHttpHelper.getInstance()
            ok.post_for_object(
                "http://121.199.22.134:8003/api-authority/changeRoleInfo",changeParams,
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

    interface titleShow{
        fun show(list:Array<titleItem>)
    }
    data class titleItem(val role:String,val authorities:Int)
    data class changeParams(val token:String,val warehouseId:Int,val role:String,val authorities:String)
}