package com.example.WMS.MyFragment.Message_Notify

import android.content.Context
import com.example.WMS.BaseCallback
import com.example.WMS.OkHttpHelper
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException
import java.lang.Exception

class Message_Notify_Model {

    companion object{
        fun get_Invite(afterShow: after_Show){
            var okHttpHelper= OkHttpHelper.getInstance()
            okHttpHelper.get_for_list("http://121.199.22.134:8003/api-authority/getInvitationList",object :
                BaseCallback<invite_item>(){
                override fun onFailure(request: Request?, e: IOException?) {
                    println("@@@@@1"+e)
                }
                override fun onResponse(response: Response?) {
                    println("@@@@@2"+response)
                }

                override fun onSuccess_List(resultStr: String?) {
                    println("@@@@@3"+resultStr)

                    val gson = Gson()
                    val invites = gson.fromJson(
                        resultStr,
                        Array<invite_item>::class.java
                    )
                    afterShow.show(invites)

                }

                override fun onSuccess(response: Response?, t: invite_item?) {
                    println("@@@@@3"+t)
                }

                override fun onError(response: Response?, code: Int, e: Exception?) {
                    println("@@@@@4"+code+e)
                }

            })
        }

        fun accept_Invite(invitationId:Int,token: String){
             var acceptParams=invite_params(invitationId,token)
            var okHttpHelper= OkHttpHelper.getInstance()
            okHttpHelper.post_for_object("http://121.199.22.134:8003/api-authority/acceptInvitation",acceptParams,object :
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
                }

                override fun onError(response: Response?, code: Int, e: Exception?) {
                    println("@@@@@4"+code+e)
                }

            })
        }

        fun refuse_Invite(invitationId:Int,token: String){
            var acceptParams=invite_params(invitationId,token)
            var okHttpHelper= OkHttpHelper.getInstance()
            okHttpHelper.post_for_object("http://121.199.22.134:8003/api-authority/deleteInvitation",acceptParams,object :
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
                }

                override fun onError(response: Response?, code: Int, e: Exception?) {
                    println("@@@@@4"+code+e)
                }

            })
        }

    }






    data class invite_item(val InvitationId:String,val warehouseId:String,val warehouseName:String,val inviter:String,val role:String)

    interface after_Show{
        fun show(list:Array<invite_item>)
    }


    data class invite_params(val InvitationId:Int,val token:String)
}