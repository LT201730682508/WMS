package com.example.WMS.MyFragment.Warehouse.All_Warehouse

import com.example.WMS.BaseCallback
import com.example.WMS.OkHttpHelper
import com.example.WMS.WarehouseIn.Warehouse_New_Model
import com.example.WMS.domain.DataBean.ProductIn
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException

class All_Warehouse_Model {
    companion object{
           fun getData(show:Show) {
            val ok = OkHttpHelper.getInstance()
            ok.get_for_list(
                "http://121.199.22.134:8003/api-inventory/getWarehouseByCompanyId/1",
                object : BaseCallback<ProductIn?>() {
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
                            Array<Warehouse>::class.java
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

                    override fun onSuccess(response: Response?, t: ProductIn?) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }

    interface Show{
        fun show(wares: Array<Warehouse>)
    }

    data class Warehouse(var warehouseId:Int,var warehouseName:String)
}