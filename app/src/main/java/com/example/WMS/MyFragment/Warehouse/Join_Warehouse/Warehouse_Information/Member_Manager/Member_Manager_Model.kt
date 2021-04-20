package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager

import android.content.Context
import com.example.WMS.BaseCallback
import com.example.WMS.MyFragment.Login_fragment
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.OkHttpHelper
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.xuexiang.xui.widget.toast.XToast
import java.io.IOException

class Member_Manager_Model {
    companion object{
        fun getData(show:Show,userLogin: Login_fragment.user_Login,warehouseId:Int) {
            val ok = OkHttpHelper.getInstance()
            ok.get_for_list(
                "http://121.199.22.134:8003/api-authority/getAllStaff/"+warehouseId+"?userToken="+userLogin.token,
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
                            Array<member_item>::class.java
                        )
                        var arrayList= arrayListOf<member_item>()
                        arrayList.addAll(wares)
                        show.show(arrayList)
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


        fun getGroupMemberData(show:Show,token:String,groupid:Int) {
            val ok = OkHttpHelper.getInstance()
            ok.get_for_list(
                "http://121.199.22.134:8003/api-authority/getStaffListByGroupId?userToken="+token+"&groupId="+groupid,
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
                            Array<member_item>::class.java
                        )
                        var arrayList= arrayListOf<member_item>()
                        arrayList.addAll(wares)
                        show.show(arrayList)
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
       fun groupAddMember(context: Context,token: String,groupid: Int,user_name: String,notifychange: notifychange
       ){
           val ok = OkHttpHelper.getInstance()
           val add_member_parmas=add_member_parmas(groupid,user_name)
           ok.post_for_object(
               "http://121.199.22.134:8003/api-authority/addStaffToGroup?userToken="+token,add_member_parmas,
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
                           Array<member_item>::class.java
                       )
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
                       XToast.success(context,t!!).show()
                       notifychange.change()
                   }
               })
       }
        fun groupDeleteMember(context: Context,token: String,groupid: Int,user_name: String){
            val ok = OkHttpHelper.getInstance()
            val deleteMemberParams=delete_member_params(groupid,user_name)
            ok.post_for_object(
                "http://121.199.22.134:8003/api-authority/removeStaffFromGroup?userToken="+token,deleteMemberParams,
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
                          if(t=="OK"){
                              XToast.success(context,t).show()
                          }else{
                              XToast.warning(context,t!!).show()
                          }
                    }
                })
        }

    }
    interface Show{
        fun show(wares: ArrayList<member_item>)
    }
    interface notifychange{
        fun change()
    }
    data class add_member_parmas(val group_id:Int,val user_name:String)
    data class delete_member_params(val groupid: Int,val user_name: String)
    data class member_item(val user_name:String,val role:String)
}