package com.example.WMS.WareOperation.Receiver_Supplier;

import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.WMS.BaseCallback;
import com.example.WMS.MainActivity;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.WareOperation.Categroy.SelectItem;
import com.example.WMS.domain.DataBean;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

public class History_Adapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<History_Adapter.VH>{
    public static class VH extends RecyclerView.ViewHolder{
        //private FrameLayout fl_write;
        private SparseArray<View> views = new SparseArray<>();
        private ImageView imageView;
        public VH(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.ware_in_item_img);
        }
        public static History_Adapter.VH getHolder(int mResId, ViewGroup parent, int viewType){
            History_Adapter.VH holder;
            View v = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
            holder = new History_Adapter.VH(v);
            return holder;
        }
        private <T extends View> T getView(int id) {
            T t = (T) views.get(id);
            if (t == null) {
                t = this.itemView.findViewById(id);
                views.put(id, t);
            }
            return t;
        }
        public History_Adapter.VH setText(int id, String text){
            TextView textView = getView(id);
            textView.setText(text);
            return this;
        }
    }
    private ArrayList<DataBean.Supplier_history> mData_Supplier;
    private ArrayList<DataBean.Receiver_history> mData_Receiver;
    private int mResId;
    private String token;
    private int opType;
    private Fragment fragment;
    public History_Adapter(int mResId, ArrayList<DataBean.Supplier_history> data, String token, int opType, Fragment fragment) {
        this.mData_Supplier = data;
        this.mResId = mResId;
        this.token=token;
        this.opType = opType;
        this.fragment = fragment;
    }
    public History_Adapter(int mResId, ArrayList<DataBean.Receiver_history> data, int opType, String token, Fragment fragment) {
        this.mData_Receiver = data;
        this.mResId = mResId;
        this.token=token;
        this.opType = opType;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public History_Adapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return History_Adapter.VH.getHolder(mResId,parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull History_Adapter.VH holder, int position) {
        bindView(holder,position);
    }

    private void bindView(History_Adapter.VH holder, int position) {
        String tags = "";
        String attr = "";
        if(opType == 0){
            tags = mData_Supplier.get(position).getProductTags();
            if(tags.contains("e")){
                attr = "优品";
            }
            else if(tags.contains("f")){
                attr = "合格品";
            }
            else if(tags.contains("g")){
                attr = "良品";
            }
            else if(tags.contains("h")){
                attr = "残次品";
            }
            else{
                attr = "无操作商品";
            }
            holder.setText(R.id.date, mData_Supplier.get(position).getCreateTime());
            holder.setText(R.id.supplier_or_other, attr);
            holder.setText(R.id.ware_in_item_name, mData_Supplier.get(position).getProductName());
            holder.setText(R.id.ware_in_item_price, mData_Supplier.get(position).getInPrice()+"");
            holder.setText(R.id.wae_in_item_number, "数量："+mData_Supplier.get(position).getAmount());
            holder.setText(R.id.wae_in_item_all_price, "总价："+mData_Supplier.get(position).getInPrice()*mData_Supplier.get(position).getAmount());
            holder.setText(R.id.note, mData_Supplier.get(position).getNote());
            holder.setText(R.id.executer, mData_Supplier.get(position).getUserName());
            Glide.with(fragment).load(mData_Supplier.get(position).getProductImg()).into(holder.imageView);
        }
        else if(opType == 1){
            tags = mData_Receiver.get(position).getProductTags();
            if(tags.contains("e")){
                attr = "优品";
            }
            else if(tags.contains("f")){
                attr = "合格品";
            }
            else if(tags.contains("g")){
                attr = "良品";
            }
            else if(tags.contains("h")){
                attr = "残次品";
            }
            else{
                attr = "无操作商品";
            }
            holder.setText(R.id.date, mData_Receiver.get(position).getCreateTime());
            holder.setText(R.id.supplier_or_other, attr);
            holder.setText(R.id.ware_in_item_name, mData_Receiver.get(position).getProductName());
            holder.setText(R.id.ware_in_item_price, mData_Receiver.get(position).getOutPrice()+"");
            holder.setText(R.id.wae_in_item_number, "数量："+mData_Receiver.get(position).getAmount());
            holder.setText(R.id.wae_in_item_all_price, "总价："+mData_Receiver.get(position).getOutPrice()*mData_Receiver.get(position).getAmount());
            holder.setText(R.id.note, mData_Receiver.get(position).getNote());
            holder.setText(R.id.executer, mData_Receiver.get(position).getUserName());
            Glide.with(fragment).load(mData_Receiver.get(position).getProductImg()).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if(opType == 0){
            return mData_Supplier.size();
        }
        else if(opType == 1){
            return mData_Receiver.size();
        }
        return 0;
    }
}