package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information

import com.example.WMS.BaseCallback
import com.example.WMS.MyFragment.Login_fragment
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.OkHttpHelper
import com.example.WMS.domain.DataBean
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException

class Join_Warehouse_Model {
    companion object{
        fun getData(show:Show,userLogin: Login_fragment.user_Login) {
            val ok = OkHttpHelper.getInstance()
            ok.get_for_list(
                "http://121.199.22.134:8003/api-inventory/getJoinedWarehouse?userToken="+userLogin.token,
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
                        val wares = gson.fromJson(
                            resultStr,
                            Array<All_Warehouse_Model.Warehouse>::class.java
                        )
                        show.show(wares)
                        for (ware in wares){
                            println("@@@@@2"+ware)
                        }


                    }
                    override fun onError(
                        response: Response,
                        code: Int,
                        e: Exception
                    ) {
                        println("error$response$e")
                    }

                    override fun onSuccess(response: Response?, t: String?) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }

    interface Show{
        fun show(wares: Array<All_Warehouse_Model.Warehouse>)
    }


}