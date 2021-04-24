package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.AreaManager

import com.example.WMS.BaseCallback
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Group_Manager.Group_Model
import com.example.WMS.OkHttpHelper
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException

class AreaManagerModel {
    companion object{
        fun get_area_list(token: String, warehouseId: Int, show: AreaShow) {
            val ok = OkHttpHelper.getInstance()
            ok.get_for_list(
                "http://121.199.22.134:8003/api-inventory/getAreaListByWarehouseId/"+warehouseId+"?userToken=" + token,
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
                        val areaList = gson.fromJson(
                            resultStr,
                            Array<Area>::class.java
                        )
                        val arrayList= arrayListOf<Area>()
                        if(areaList.size>0){
                            for(l in areaList){
                                arrayList.add(l)
                            }
                        }
                        show.show(arrayList)
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
    interface AreaShow{
        fun show(areaList:ArrayList<Area>)
    }
    data class Area(val areaId:Int,val areaName:String,val currVolume:Int,val totalVolume:Int)
}