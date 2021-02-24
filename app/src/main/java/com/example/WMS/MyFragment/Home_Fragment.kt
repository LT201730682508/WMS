package com.example.WMS.MyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Data_report.Data_report_fragment
import com.example.WMS.MyFragment.Message_Notify.Message_Notify_Fragment
import com.example.WMS.MyFragment.Warehouse.Warehouse_Fragment
import com.example.WMS.R
import com.example.WMS.WarehouseIn.WarehouseInList_Fragment
import com.example.WMS.WarehouseOut.WarehouseOutList_Fragment
import kotlinx.android.synthetic.main.home.*

class Home_Fragment: Fragment() {
    lateinit var item1: RelativeLayout
    lateinit var item2: RelativeLayout
    lateinit var item3: RelativeLayout
    lateinit var item4: RelativeLayout
    lateinit var item5: RelativeLayout
    lateinit var item6: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.home,container,false)
        jump(view)
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
    }
}