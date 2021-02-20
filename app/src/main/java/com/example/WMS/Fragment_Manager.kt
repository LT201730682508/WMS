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
        transaction.setCustomAnimations(R.anim.push_in,R.anim.pull_out_enter,R.anim.push_in_pop,R.anim.pull_out)
        transaction.hide(fragment_list[fragment_list.size-1])
        println("fragment的大小"+fragment_list.size+"   "+fragment_list[fragment_list.size-1])
        transaction.add(R.id.Fragment_First,fragment).addToBackStack(null).commit()
    }
    fun pop(){
        var manager=my_activity.supportFragmentManager
        manager.popBackStack()
    }
    fun ger_Top_Fragment():Fragment{
        var fragment_list=my_activity.supportFragmentManager.fragments
        return fragment_list[fragment_list.size-1]
    }

}