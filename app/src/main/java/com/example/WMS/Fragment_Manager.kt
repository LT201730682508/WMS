package com.example.WMS

import androidx.fragment.app.Fragment
import com.example.WMS.MyFragment.Login_fragment
import com.example.WMS.WarehouseIn.Warehouse_New_Fragment


class Fragment_Manager {
    var my_activity:MainActivity
    var fragment_show:Fragment
    var loginFragment:Login_fragment
    var warehousenewfragment:Warehouse_New_Fragment
    constructor(activity:MainActivity){
        my_activity=activity
        loginFragment=Login_fragment()
        warehousenewfragment=Warehouse_New_Fragment("仓库名")
        fragment_show=loginFragment
        my_activity.supportFragmentManager.beginTransaction().add(R.id.Fragment_First,loginFragment).commit()
    }

    fun replace_all(fragment: Fragment) {
        var transaction= my_activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Fragment_First,fragment).commit()
    }
    fun hide_all(fragment: Fragment){
        var fragment_list=my_activity.supportFragmentManager.fragments
        var transaction=my_activity.supportFragmentManager.beginTransaction()
        transaction.hide(fragment_list[fragment_list.size-1])
        println("fragment的大小"+fragment_list.size+"   "+fragment_list[fragment_list.size-1])
        transaction.add(R.id.Fragment_First,fragment).addToBackStack(null).commit()
    }
    fun pop(){
        var manager=my_activity.supportFragmentManager
        manager.popBackStack()
    }

}