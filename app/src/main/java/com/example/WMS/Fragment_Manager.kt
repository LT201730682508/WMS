package com.example.WMS

import androidx.fragment.app.Fragment
import com.example.WMS.MyFragment.Login_fragment


class Fragment_Manager {
    var my_activity:MainActivity
     var fragment_show:Fragment

    var loginFragment:Login_fragment
    constructor(activity:MainActivity){
        my_activity=activity
        loginFragment=Login_fragment()

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
        for(frag in fragment_list){
           transaction.hide(frag)
        }
       transaction.add(R.id.Fragment_First,fragment).addToBackStack(null).commit()
    }

}