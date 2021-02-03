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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WMS.R;
import com.example.WMS.domain.WarehouseItem;

import java.util.ArrayList;
import java.util.List;

public class WarehouseInList_Fragment extends Fragment{
    protected Context context;
    private RecyclerView lv_video_pager;
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
        lv_video_pager.setLayoutManager(new LinearLayoutManager(context));
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
                warehouseItem.setName("仓库2");
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

                lv_video_pager.setAdapter(new WarehouseInList_Fragment.WarehouseInListAdapter(warehouseItems));
            }
            else{
                tv_nomedia.setVisibility(View.VISIBLE);
                pb_loading.setVisibility(View.VISIBLE);
            }
        }
    };
    public class WarehouseInListAdapter extends RecyclerView.Adapter<WarehouseInListAdapter.VH>{
        //② 创建ViewHolder
        public class VH extends RecyclerView.ViewHolder{
            public final TextView title;
            public VH(View v) {
                super(v);
                title = (TextView) v.findViewById(R.id.tv_name);
            }
        }

        private ArrayList<WarehouseItem> mDatas;
        public WarehouseInListAdapter(ArrayList<WarehouseItem> data) {
            this.mDatas = data;
        }

        //③ 在Adapter中实现3个方法
        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.title.setText(mDatas.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //item 点击事件
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            //LayoutInflater.from指定写法
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inlist, parent, false);
            return new VH(v);
        }
    }
}