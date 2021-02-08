package com.example.WMS;



import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WMS.domain.WarehouseItem;

import java.util.ArrayList;

public abstract class MyAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<MyAdapter.VH> {
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

    private int mResId;
    private ArrayList<WarehouseItem> mDatas;
    public MyAdapter(ArrayList<WarehouseItem> data,int mResId) {
        this.mDatas = data;
        this.mResId=mResId;
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

    public abstract void onClickMethod(VH holder, int position);

    public abstract void bindView(VH holder,int position);
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
