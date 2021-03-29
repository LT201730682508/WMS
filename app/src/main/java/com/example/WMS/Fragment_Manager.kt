package com.example.WMS

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.example.WMS.MyFragment.Home_Fragment
import com.example.WMS.MyFragment.Login_fragment


class Fragment_Manager {
    var my_activity:MainActivity

     var loginFragment:Login_fragment
    lateinit var userinfo:Login_fragment.user_Login
    lateinit var homeFragment: Home_Fragment
    val sps: SharedPreferences
    constructor(activity:MainActivity){
        my_activity=activity
        loginFragment=Login_fragment()
        sps= activity.getSharedPreferences("userinfo",Context.MODE_PRIVATE)
        var username=sps.getString("userName","")
        var password=sps.getString("passWord","")
        if(username!=""&&password!=""){
            loginFragment.login(username!!,password!!,object :Login_fragment.show{
                override fun show(
                    t: Login_fragment.user_Login,
                    username: String,
                    password: String
                ) {
                    userinfo=t!!
                    homeFragment= Home_Fragment()
                    my_activity.supportFragmentManager.beginTransaction().add(R.id.Fragment_First,homeFragment).commit()
                }
            })
        }else{
            my_activity.supportFragmentManager.beginTransaction().add(R.id.Fragment_First,loginFragment).commit()
        }




    }


    fun replace_all(fragment: Fragment) {
        var transaction= my_activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Fragment_First,fragment).commit()
    }

    fun return_login(){
        var transaction= my_activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Fragment_First,loginFragment).commit()
    }
    fun hide_all(fragment: Fragment){
        var transaction=my_activity.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.push_in,R.anim.pull_out_enter,R.anim.push_in_pop,R.anim.pull_out)

        transaction.hide(ger_Top_Fragment())
        transaction.add(R.id.Fragment_First,fragment).addToBackStack(null).commit()
    }
    fun pop(){
        var manager=my_activity.supportFragmentManager
        manager.popBackStack()
    }
    fun ger_Top_Fragment():Fragment{
        var fragment_list=my_activity.supportFragmentManager.fragments
        var transaction=my_activity.supportFragmentManager.beginTransaction()
        println("fragment的大小"+fragment_list.size+"   "+fragment_list[fragment_list.size-1])
        if(fragment_list[fragment_list.size-1] is SupportRequestManagerFragment){
            transaction.remove(fragment_list[fragment_list.size-1])
            transaction.commit()
            return fragment_list[fragment_list.size-2]
        }
        return fragment_list[fragment_list.size-1]
    }

}