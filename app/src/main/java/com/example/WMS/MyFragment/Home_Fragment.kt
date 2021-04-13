package com.example.WMS.MyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Data_report.Data_report_fragment
import com.example.WMS.MyFragment.Message_Notify.Message_Notify_Fragment
import com.example.WMS.MyFragment.Set_User_Information.Set_User_Information_Fragment
import com.example.WMS.MyFragment.Warehouse.Warehouse_Fragment
import com.example.WMS.R
import com.example.WMS.WareOperation.WarehouseIn.WarehouseInList_Fragment
import com.example.WMS.WareOperation.WarehouseOut.WarehouseOutList_Fragment
import com.example.WMS.custom_Dialog.Create_Company_Dialog
import com.example.WMS.custom_Dialog.Join_or_Create
import com.xuexiang.xui.XUI

class Home_Fragment: Fragment() {
    lateinit var item1: RelativeLayout
    lateinit var item2: RelativeLayout
    lateinit var item3: RelativeLayout
    lateinit var item4: RelativeLayout
    lateinit var item5: RelativeLayout
    lateinit var item6: RelativeLayout
    lateinit var click_join:TextView
    lateinit var join_create_re:LinearLayout
    lateinit var set_user:ImageView
    lateinit var hongdian:ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.home,container,false)
        jump(view)
        XUI.initTheme(activity)
        return view
    }
    fun jump(view:View){

        item1=view.findViewById(R.id.item1)
        item1.setOnClickListener {
            var warehouseFragment=Warehouse_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(warehouseFragment)
        }
        item2=view.findViewById(R.id.item2)
        item2.setOnClickListener {
            var warehouseInListFragment= WarehouseInList_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(warehouseInListFragment)
        }
        item3=view.findViewById(R.id.item3)
        item3.setOnClickListener {
            var warehouseOutListFragment= WarehouseOutList_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(warehouseOutListFragment)
        }
        item4=view.findViewById(R.id.item4)
        item4.setOnClickListener {
            var Message_Notify_Fragment= Message_Notify_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(Message_Notify_Fragment)
        }
        item6=view.findViewById(R.id.item6)
        item6.setOnClickListener {
            var dataReportFragment= Data_report_fragment()
            (activity as MainActivity).fragment_Manager.hide_all(dataReportFragment)
        }
        join_create_re=view.findViewById(R.id.join_create)
        if((activity as MainActivity).fragment_Manager.userinfo.userInfo.companyId!=0){
            join_create_re.visibility=View.GONE
        }
        click_join=view.findViewById(R.id.click_join)
        click_join.setOnClickListener {
            var joinOrCreate=Join_or_Create(requireContext(),(activity as MainActivity).fragment_Manager.userinfo.token,object :Create_Company_Dialog.change_Info{
                override fun change(createResult: Create_Company_Dialog.create_result) {
                    (activity as MainActivity).fragment_Manager.userinfo.userInfo.companyId=createResult.companyId
                    (activity as MainActivity).fragment_Manager.userinfo.userInfo.companyName=createResult.companyName
                    join_create_re.visibility=View.GONE
                }

            })
            joinOrCreate.show()
        }
        hongdian=view.findViewById(R.id.hongdian)
        if((activity as MainActivity).fragment_Manager.userinfo.userInfo.hasInvitation==0){
            hongdian.visibility=View.GONE
        }
        set_user=view.findViewById(R.id.set_user_information)
        set_user.setOnClickListener {
            var setUserInformationFragment= Set_User_Information_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(setUserInformationFragment)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (isHidden) {
        } else {
            if((activity as MainActivity).fragment_Manager.userinfo.userInfo.companyId!=0){
                join_create_re.visibility=View.GONE
            }
            if((activity as MainActivity).fragment_Manager.userinfo.userInfo.hasInvitation==0){
                hongdian.visibility=View.GONE
            }
        }
    }
}