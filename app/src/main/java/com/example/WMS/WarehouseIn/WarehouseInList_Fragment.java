package com.example.WMS.WarehouseIn;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.WMS.R;
import com.example.WMS.domain.WarehouseItem;

import java.util.ArrayList;

public class WarehouseInList_Fragment extends Fragment{
    protected Context context;
    private ListView lv_video_pager;
    private TextView tv_nomedia;
    private ProgressBar pb_loading;
    private ArrayList<WarehouseItem> warehouseItems;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }
    //子类实现方法
    public View initView(){
        View view=View.inflate(context,R.layout.inlist_fragment,null);
        lv_video_pager=view.findViewById(R.id.lv_video_pager);
        tv_nomedia=view.findViewById(R.id.tv_nomedia);
        pb_loading=view.findViewById(R.id.pb_loading);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public void initData(){
        //getData();
        new Thread() {
            @Override
            public void run() {
                super.run();
                warehouseItems = new ArrayList<WarehouseItem>();
                WarehouseItem warehouseItem=new WarehouseItem();
                //假数据
                warehouseItem.setName("仓库1");
                warehouseItems.add(warehouseItem);
                warehouseItem.setName("仓库1");
                warehouseItems.add(warehouseItem);
                handler.sendEmptyMessage(0);
            }
        }.start();
    }
    private android.os.Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(warehouseItems!=null&&warehouseItems.size()>0){
                tv_nomedia.setVisibility(View.GONE);
                pb_loading.setVisibility(View.GONE);
                //设置适配器
                lv_video_pager.setAdapter(new WarehouseInList_Fragment.WarehouseInListAdapter());
            }
            else{
                tv_nomedia.setVisibility(View.VISIBLE);
                pb_loading.setVisibility(View.VISIBLE);
            }
        }
    };
    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
    }
    class WarehouseInListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return warehouseItems.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            WarehouseInList_Fragment.ViewHolder viewHolder;
            if(view==null){
                view=View.inflate(context, R.layout.item_inlist,null);
                viewHolder=new WarehouseInList_Fragment.ViewHolder();
                viewHolder.iv_icon=view.findViewById(R.id.iv_icon);
                viewHolder.tv_name=view.findViewById(R.id.tv_name);
                view.setTag(viewHolder);
            }
            else{
                viewHolder = (WarehouseInList_Fragment.ViewHolder) view.getTag();
            }
            WarehouseItem warehouseItem=warehouseItems.get(i);
            viewHolder.tv_name.setText(warehouseItem.getName());
            //viewHolder.iv_icon.setImageResource(R.drawable.music_default_bg);
            return view;
        }
    }
}