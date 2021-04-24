package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.AreaManager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyRecyclerView
import com.example.WMS.R
import com.example.WMS.custom_Dialog.Create_Area_Dialog
import kotlinx.android.synthetic.main.warehouse_information.*

class AreaManagerFragment(var wareHouseid:Int):Fragment() {
    lateinit var areaRecyclerView: RecyclerView
    lateinit var addArea:Button
    lateinit var baseTopbar: Base_Topbar
    lateinit var areaListAdapter: AreaListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.ware_area_manager,container,false)
        init(view)
        return view
    }
    fun init(view: View){
        baseTopbar= Base_Topbar(view,activity as MainActivity)
        areaRecyclerView=view.findViewById(R.id.area_list)
        addArea=view.findViewById(R.id.add_area)
        getData()
        addArea.setOnClickListener {
            var createAreaDialog=Create_Area_Dialog(requireContext(),(activity as MainActivity).fragment_Manager.userinfo.token,wareHouseid,object :Create_Area_Dialog.AddResult{
                override fun result() {
                    getData()
                }

            })
            createAreaDialog.show()
        }
    }

    fun getData(){
        AreaManagerModel.get_area_list((activity as MainActivity).fragment_Manager.userinfo.token,wareHouseid,object :AreaManagerModel.AreaShow{
            override fun show(areaList: ArrayList<AreaManagerModel.Area>) {
                areaRecyclerView.layoutManager=LinearLayoutManager(requireContext())
                areaListAdapter=AreaListAdapter(activity as MainActivity,areaList)
                areaRecyclerView.adapter=areaListAdapter
            }

        })
    }
}