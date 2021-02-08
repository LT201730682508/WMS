package com.example.WMS;



import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WMS.WarehouseIn.WarehouseInDetailFragment;
import com.example.WMS.WarehouseOut.WarehouseOutDetailFragment;
import com.example.WMS.domain.WarehouseItem;

import java.util.ArrayList;

public class MyAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<MyAdapter.VH> {
    public static class VH extends RecyclerView.ViewHolder{
        //public final TextView title;

        private SparseArray<View> views = new SparseArray<>();
        public static  VH getHolder(int mResId, ViewGroup parent, int viewType){
            VH holder;
            View v = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
            holder=new VH(v);
            return holder;
        }
        public VH(View v) {
            super(v);
            //title = (TextView) v.findViewById(R.id.tv_name);
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
        public VH setChecked(int id, boolean isChecked) {
            CheckBox checkBox = getView(id);
            checkBox.setChecked(isChecked);
            return this;
        }
    }
    private int opType=0;//0是入库，1是出库后续继续添加
    private int mResId;
    private ArrayList<WarehouseItem> mDatas;
    private MainActivity activity;
    public MyAdapter(ArrayList<WarehouseItem> data,int mResId, int opType,MainActivity activity) {
        this.mDatas = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
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

    //public abstract void onClickMethod(VH holder, int position);
    public void onClickMethod(VH holder,int position){
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opType==0){//入库Adater
                    WarehouseInDetailFragment warehouseInDetailFragment = new WarehouseInDetailFragment();
                    activity.fragment_Manager.hide_all(warehouseInDetailFragment);
                }
                else if(opType==1){//出库Adater
                    WarehouseOutDetailFragment warehouseOutDetailFragment = new WarehouseOutDetailFragment();
                    activity.fragment_Manager.hide_all(warehouseOutDetailFragment);
                }
            }
        });
    }
    //public abstract void bindView(VH holder,int position);
    public void bindView(VH holder,int position){
        if(opType==0){
            holder.setText(R.id.tv_name, mDatas.get(position).getName());
        }
        else if(opType==1){
            holder.setText(R.id.tv_name, mDatas.get(position).getName());
        }
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
