package com.example.WMS

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException


class MainActivity : AppCompatActivity() {

    lateinit var fragment_Manager:Fragment_Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment_Manager= Fragment_Manager(this)

    }

    fun test(){
        var ok=OkHttpHelper.getInstance()
        var list= arrayListOf<mydata>()
        ok.get_for_list("http://172.21.245.42:8003/api-order/getInventory/2",object : BaseCallback<mydata>(){
            override fun onFailure(request: Request?, e: IOException?) {
                println("aaaa"+e)
            }

            override fun onResponse(response: Response?) {
                println("aaaa"+response)
            }

            override fun onSuccess(response: Response?, t: mydata){
                println("aaaa"+response)
            }

            override fun onError(response: Response?, code: Int, e: Exception?) {
                println("aaaa"+response+e)
            }

            override fun onSuccess_List(response: Response?) {
                val resultStr = response!!.body().string()
                var gson=Gson()
                val list = gson.fromJson(
                    resultStr,
                    Array<mydata>::class.java
                )
                for(l in list){
                    println("aaa"+l)
                }
            }
        })
    }
   data class product(var productId:Int,var productName:String,var productDescription:String,var productCategory:String,var productCode:Int)
   data class mydata(var id:Int,var warehouseId:Int,var totalAmount:Int,var product:product)
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        println("已执行"+fragment_Manager.ger_Top_Fragment())
        fragment_Manager.ger_Top_Fragment().onActivityResult(requestCode,resultCode,data)

    }

}
