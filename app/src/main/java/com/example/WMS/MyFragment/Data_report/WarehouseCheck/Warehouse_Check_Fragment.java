package com.example.WMS.MyFragment.Data_report.WarehouseCheck;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WMS.EndlessRecyclerOnScrollListener;
import com.example.WMS.MainActivity;
import com.example.WMS.MyAdapter;
import com.example.WMS.My_Thread;
import com.example.WMS.R;
import com.example.WMS.domain.WarehouseItem;
import com.example.WMS.execute_IO;
import com.example.WMS.perform_UI;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Warehouse_Check_Fragment extends Fragment {
    protected Context context;
    private TextView size;
    private static RecyclerView rv_pager;
    private static ProgressBar pb_loading;
    private static TextView tv_nomedia;
    private static ArrayList<WarehouseItem> warehouseItems;
    private static MyAdapter<MyAdapter.VH> adapter;
    private MyHandler handler;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        handler=new MyHandler((MainActivity) getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }
    public View initView(){
        View view=View.inflate(context, R.layout.fragment_warehouse_check,null);
        size=view.findViewById(R.id.tv_size);
        rv_pager=view.findViewById(R.id.rv_pager);
        rv_pager.setLayoutManager(new LinearLayoutManager(context));
        tv_nomedia=view.findViewById(R.id.tv_nomedia);
        pb_loading=view.findViewById(R.id.pb_loading);
        return view;
    }
    public void initData(){
        My_Thread.Companion.new_thread(new perform_UI() {
            @Override
            public void show() {
                handler.sendEmptyMessage(0);
            }
        }, new execute_IO() {
            @Override
            public void execute() {
                warehouseItems = new ArrayList<WarehouseItem>();
                WarehouseItem warehouseItem=new WarehouseItem();
                //假数据
                warehouseItem.setWarehouse_name("深圳");
                warehouseItems.add(warehouseItem);
                warehouseItem=new WarehouseItem();
                //假数据
                warehouseItem.setWarehouse_name("广州");
                warehouseItems.add(warehouseItem);
                warehouseItem=new WarehouseItem();
                //假数据
                warehouseItem.setWarehouse_name("中山");
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
            }
        });

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(isHidden()){
        }else {
            onResume();
        }
    }
    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;
        public MyHandler(MainActivity activity){
            mActivity =new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            final MainActivity activity=mActivity.get();
            super.handleMessage(msg);
            if(activity!=null){
                if(warehouseItems!=null&&warehouseItems.size()>0){
                    tv_nomedia.setVisibility(View.GONE);
                    pb_loading.setVisibility(View.GONE);

                    //lv_video_pager.setAdapter(new WarehouseInList_Fragment.WarehouseInListAdapter(warehouseItems));
                    adapter=new MyAdapter<MyAdapter.VH>(warehouseItems, R.layout.item_checklist,2,activity);
                    rv_pager.setAdapter(adapter);
                }
                else{
                    tv_nomedia.setVisibility(View.VISIBLE);
                    pb_loading.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }
}
