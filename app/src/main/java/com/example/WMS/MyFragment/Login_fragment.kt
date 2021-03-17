package com.example.WMS.MyFragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.WMS.*
import com.example.WMS.custom_Dialog.take_Album_Dialog
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.login.*
import java.io.IOException
import java.lang.Exception

class Login_fragment: Fragment() {

    lateinit var image:ImageView
    lateinit var signIn_signUpButton: Button
    lateinit var signIn_signInButton:Button
    lateinit var signIn_account:EditText
    lateinit var signIn_passWord:EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.login,container,false)
        signIn_signInButton=view.findViewById(R.id.signIn_signInButton)
        signIn_signUpButton=view.findViewById(R.id.signIn_signUpButton)
        image=view.findViewById(R.id.appIcon)
        signIn_account=view.findViewById(R.id.signIn_account)
        signIn_passWord=view.findViewById(R.id.signIn_passWord)
        image.setOnClickListener {
            var dialog=take_Album_Dialog(requireContext())
            dialog.show()
        }

        signIn_signUpButton.setOnClickListener {
            var  registerFragment= Register_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(registerFragment)
        }
        signIn_signInButton.setOnClickListener {
            if(check_null()){
                var homeFragment= Home_Fragment()
                (activity as MainActivity).fragment_Manager.replace_all(homeFragment)
            }else{
                XToast.warning(requireContext(),"请输入正确的账号密码").show()
            }
        }

        return view
    }

    fun check_null():Boolean{
        if (signIn_account.text.isEmpty()||signIn_passWord.text.isEmpty()){
            return false
        }
        return true
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        println("拍照成功")
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Open_Album.TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                val bitmap =
                    BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(Open_Album.uri))
                Glide.with(this).load(bitmap).into(image)
                println("拍照成功")
            }
            Open_Album.CHOOSE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 19) Open_Album.handleImageOnKitKat(activity,data,image) else Open_Album.handleImageBeforeKitKat(activity,data,image)
            }
            else -> {
            }
        }
    }

    fun login(username:String,password:String){
        var map=HashMap<String,String>()
        map.put("userName",username)
        map.put("passWord",password)
        var okHttpHelper= OkHttpHelper.getInstance()
        okHttpHelper.post_for_object("http://121.199.22.134:8003/api-user/login",map,object :
            BaseCallback<user_Login>(){
            override fun onFailure(request: Request?, e: IOException?) {
                println("@@@@@1"+e)
            }
            override fun onResponse(response: Response?) {
                println("@@@@@2"+response)
            }

            override fun onSuccess_List(resultStr: String?) {
                println("@@@@@3"+resultStr)
            }

            override fun onSuccess(response: Response?, t: user_Login?) {
                println("@@@@@3"+t)
                val editor = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE).edit()
                editor.putString("userName",username)
                editor.putString("passWord",password)
                editor.putString("token",t!!.token)
                editor.putString("companyId",t.companyId.toString())
                editor.apply()

            }

            override fun onError(response: Response?, code: Int, e: Exception?) {
                println("@@@@@4"+code+e)
            }

        })
    }
    data class user_Login(val userId:String,val token:String,val companyId:Int)
}
