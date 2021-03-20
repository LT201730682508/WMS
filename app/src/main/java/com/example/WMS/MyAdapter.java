package com.example.WMS;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WMS.WarehouseIn.WarehouseInDetailFragment;
import com.example.WMS.WarehouseIn.Warehouse_Add_Fragment;
import com.example.WMS.WarehouseOut.Warehouse_Delete_Dialog;
import com.example.WMS.domain.DataBean;
import com.example.WMS.domain.WarehouseItem;

import java.util.ArrayList;


import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WMS.WarehouseIn.WarehouseInDetailFragment;
import com.example.WMS.WarehouseIn.Warehouse_Add_Fragment;
import com.example.WMS.WarehouseIn.Warehouse_New_Fragment;
import com.example.WMS.WarehouseOut.WarehouseOutDetailFragment;
import com.example.WMS.WarehouseOut.Warehouse_Delete_Dialog;
import com.example.WMS.domain.WarehouseItem;

import java.util.ArrayList;

public class MyAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<MyAdapter.VH> {
    public static class VH extends RecyclerView.ViewHolder{
        //public final TextView title;
        private SparseArray<View> views = new SparseArray<>();
        private RelativeLayout rl;
        private FrameLayout fl;
        private Button btn_add;

        public static  VH getHolder(int mResId, ViewGroup parent, int viewType){
            VH holder;
            View v = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
            holder = new VH(v);
            return holder;


        }
        public VH(View v) {
            super(v);
            rl=v.findViewById(R.id.item_rl);
            fl=v.findViewById(R.id.item_fl);
            btn_add=v.findViewById(R.id.btn_add);

        }
        private <T extends View> T getView(int id) {
            T t = (T) views.get(id);
            if (t == null) {
                t = this.itemView.findViewById(id);
                views.put(id, t);
            }
            return t;
        }
        public VH setText(int id, String text) {
            TextView textView = getView(id);
            textView.setText(text);
            return this;
        }
        public VH setSize(int id,int size){
            TextView textView = getView(id);
            textView.setText(size+"");
            return this;
        }
        public VH setDetail(int id,String detail){
            TextView textView = getView(id);
            textView.setText(detail);
            return this;
        }
        public VH setInPrice(int id,int inPrice){
            TextView textView = getView(id);
            textView.setText(""+inPrice);
            return this;
        }

        public VH setChecked(int id, boolean isChecked) {
            CheckBox checkBox = getView(id);
            checkBox.setChecked(isChecked);
            return this;
        }
    }
    private final int WAREHOUSE_IN=0;
    private final int WAREHOUSE_OUT=1;
    //private final int WAREHOUSE_CHECK=2;
    private int opType;//WAREHOUSE_IN是入库，WAREHOUSE_OUT是出库后续继续添加
    private int mResId;
    private ArrayList<DataBean.ProductIn> mDatas;
    private ArrayList<DataBean.ProductOut> mDatasOut;
    private MainActivity activity;

    public MyAdapter(ArrayList<DataBean.ProductIn> data,int mResId, int opType,MainActivity activity) {
        this.mDatas = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
    }
    private String warehouseName;
    private String supplierName;
    private String receiverName;
    public MyAdapter(ArrayList<DataBean.ProductIn> data,int mResId, int opType,MainActivity activity,String warehouseName,String supplierName) {
        this.mDatas = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
        this.warehouseName=warehouseName;
    }
    public MyAdapter(int mResId,ArrayList<DataBean.ProductOut> data, int opType,MainActivity activity,String warehouseName,String receiverName) {
        this.mDatasOut = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
        this.warehouseName=warehouseName;
        this.receiverName=receiverName;
    }

    @NonNull
    @Override
    public MyAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inlist, parent, false);
        //return new MyAdapter.VH(v);
        return VH.getHolder(mResId,parent,viewType);
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.VH holder, int position) {
        bindView(holder,position);
        onClickMethod(holder,position);


    }

    public void onClickMethod(VH holder, final int position){
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opType==0){//入库Adater
                    WarehouseInDetailFragment warehouseInDetailFragment = new WarehouseInDetailFragment(warehouseName,mDatas.get(position));
                    activity.fragment_Manager.hide_all(warehouseInDetailFragment);
                }
                else if(opType==1){//出库Adater
//                    WarehouseOutDetailFragment warehouseOutDetailFragment = new WarehouseOutDetailFragment();
//                    activity.fragment_Manager.hide_all(warehouseOutDetailFragment);
                }

            }
        });
        holder.fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opType==WAREHOUSE_IN){//入库Adater
                    if(mDatas.get(position).getTotalAmount()==0){
                        //执行删除list刷新ui操作
                        Toast.makeText(activity,"当前可删除",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(activity,"不为0不可删除",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(opType==WAREHOUSE_OUT){//出库Adater
                    if(mDatas.get(position).getTotalAmount()==0){
                        //执行删除list刷新ui操作
                        Toast.makeText(activity,"当前可删除",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(activity,"不为0不可删除",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opType==WAREHOUSE_IN){//入库Adater
                    Warehouse_Add_Fragment warehouse_add_fragment=new Warehouse_Add_Fragment(activity,mDatas.get(position),supplierName);
                    activity.fragment_Manager.hide_all(warehouse_add_fragment);
                }
                else if(opType==WAREHOUSE_OUT){//出库Adater
                    Warehouse_Delete_Dialog warehouse_delete_dialog=new Warehouse_Delete_Dialog(activity,mDatasOut.get(position));
                    warehouse_delete_dialog.show();
                }

            }
        });

    }
    //public abstract void bindView(VH holder,int position);
    public void bindView(VH holder,int position){
        if(opType==WAREHOUSE_IN){
            holder.setText(R.id.tv_name, mDatas.get(position).getProductName());
            holder.setSize(R.id.tv_quantity,mDatas.get(position).getTotalAmount());
            holder.setInPrice(R.id.tv_inPrice,mDatas.get(position).getInPrice());
            holder.setDetail(R.id.tv_detail,mDatas.get(position).getProductDescription());
        }
        else if(opType==WAREHOUSE_OUT){
            holder.setText(R.id.tv_name, mDatasOut.get(position).getProductName());
            holder.setSize(R.id.tv_quantity,mDatasOut.get(position).getTotalAmount());
            holder.setDetail(R.id.tv_detail,mDatasOut.get(position).getProductDescription());
        }
    }
    @Override
    public int getItemCount() {
        if(opType==0){
            return mDatas.size();
        }
        else if(opType==1){
            return mDatasOut.size();
        }
        return 0;

    }


}
