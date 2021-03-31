package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Title_Manager

import com.example.WMS.BaseCallback
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Imformation.Member_Imformation_Model
import com.example.WMS.OkHttpHelper
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.xuexiang.xui.widget.toast.XToast
import java.io.IOException

class Title_Manager_Model {

    companion object{
        fun get_title_list(token: String,warehouseId:Int,show:titleShow) {
            val ok = OkHttpHelper.getInstance()
            ok.get_for_list(
                "http://121.199.22.134:8003/api-authority/getAllRole/"+warehouseId+"?userToken="+token,
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
                        println("@@@@@2"+resultStr)
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
        fun add_title(addparams: addParams,token: String,modifyShow: modify_show){
            val ok = OkHttpHelper.getInstance()
            ok.post_for_object(
                "http://121.199.22.134:8003/api-authority/createRole?userToken="+token,addparams,
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
                        modifyShow.error(response.message().toString())
                    }

                    override fun onSuccess(response: Response?, t: String?) {
                        modifyShow.show(t!!)
                    }
                })
        }
        fun modify_member_title(changeParams: changeParams,token: String,modifyShow: modify_show) {
            val ok = OkHttpHelper.getInstance()
            ok.post_for_object(
                "http://121.199.22.134:8003/api-authority/changeRoleInfo?userToken="+token,changeParams,
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
                        modifyShow.error(response.message().toString())
                    }

                    override fun onSuccess(response: Response?, t: String?) {
                        modifyShow.show(t!!)
                    }
                })
        }

    }

    interface titleShow{
        fun show(list:Array<titleItem>)
    }

    interface modify_show{
        fun show(string: String)
        fun error(string: String)
    }

    data class titleItem(val id:Int,val role:String,val authorities:String)
    data class changeParams(val id:Int,val role:String,val authorities:String)
    data class addParams(val warehouse_id: Int,val role:String,val authorities:String)
}