package com.example.WMS.WareOperation;

import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.WMS.WareOperation.WarehouseIn.WarehouseInDetailFragment;
import com.example.WMS.WareOperation.WarehouseIn.Warehouse_Add_Fragment;
import com.example.WMS.WareOperation.WarehouseOut.Warehouse_Delete_Dialog;
import com.example.WMS.domain.DataBean;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<MyAdapter.VH> {
    public static class VH extends RecyclerView.ViewHolder{
        private SparseArray<View> views = new SparseArray<>();
        private RelativeLayout rl;
        private FrameLayout fl;
        private Button btn_add;
        private ImageView imageView;
        public static  VH getHolder(int mResId, ViewGroup parent, int viewType){
            VH holder;
            View v = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
            holder = new VH(v);
            return holder;
        }
        public VH(View v) {
            super(v);
            rl=v.findViewById(R.id.item_rl);
//            fl=v.findViewById(R.id.item_fl);
            btn_add=v.findViewById(R.id.btn_add);
            imageView=v.findViewById(R.id.iv_icon);
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
        public VH setInPrice(int id,double inPrice){
            TextView textView = getView(id);
            textView.setText(inPrice+"元(RMB)");
            return this;
        }
    }

    private final int WAREHOUSE_IN=0;
    private final int WAREHOUSE_OUT=1;
    private int opType;//WAREHOUSE_IN是入库，WAREHOUSE_OUT是出库后续继续添加
    private int mResId;
    private ArrayList<DataBean.ProductIn> mDatas;
    private ArrayList<DataBean.ProductOut> mDatasOut;
    private MainActivity activity;
    private Fragment fragment;
    private String token;
    private String id;
    private String warehouseName;
    private String supplierName;
    private String receiverName;
    private String role;
    public MyAdapter(ArrayList<DataBean.ProductIn> data, int mResId, int opType, MainActivity activity,Fragment fragment, String warehouseName, String supplierName, String token, String id, String role) {
        this.mDatas = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
        this.warehouseName=warehouseName;
        this.supplierName=supplierName;
        this.token=token;
        this.id=id;
        this.role = role;
        this.fragment=fragment;
    }

    public MyAdapter(int mResId,ArrayList<DataBean.ProductOut> data, int opType,MainActivity activity,Fragment fragment,String warehouseName,String receiverName,String token,String id, String role) {
        this.mDatasOut = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
        this.warehouseName=warehouseName;
        this.receiverName=receiverName;
        this.token=token;
        this.fragment=fragment;
        this.id=id;
        this.role = role;
    }

    @NonNull
    @Override
    public MyAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
                    WarehouseInDetailFragment warehouseInDetailFragment = new WarehouseInDetailFragment(warehouseName,mDatas.get(position),token);
                    activity.fragment_Manager.hide_all(warehouseInDetailFragment);
                }
                else if(opType==1){//出库Adater

                }

            }
        });
//        holder.fl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(opType==WAREHOUSE_IN){//入库Adater
//                    if(mDatas.get(position).getTotalAmount()==0){
//                        //执行删除list刷新ui操作
//                        DeleteData(mDatas.get(position).getId());
//                        notifyItemRemoved(position);
//                        Toast.makeText(activity,"删除成功",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(activity,"不为0不可删除",Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else if(opType==WAREHOUSE_OUT){//出库Adater
//                    if(mDatasOut.get(position).getTotalAmount()==0){
//                        //执行删除list刷新ui操作
//                        DeleteData(mDatasOut.get(position).getId());
//                        notifyItemRemoved(position);
//                        Toast.makeText(activity,"删除成功",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(activity,"不为0不可删除",Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//        });
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opType==WAREHOUSE_IN){//入库Adater
                    Warehouse_Add_Fragment warehouse_add_fragment=new Warehouse_Add_Fragment(activity,mDatas.get(position),token);
                    //activity.fragment_Manager.hide_all(warehouse_add_fragment);
                    warehouse_add_fragment.show();
                }
                else if(opType==WAREHOUSE_OUT){//出库Adater
                    Warehouse_Delete_Dialog warehouse_delete_dialog=new Warehouse_Delete_Dialog(activity,mDatasOut.get(position),token);
                    warehouse_delete_dialog.show();
                }

            }
        });

    }


    public void DeleteData(int id){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.delete_for_list("http://121.199.22.134:8003/api-inventory/deleteInventoryProductById/"+id+"?userToken="+token,new BaseCallback<Integer>(){
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("failure"+e);
            }

            @Override
            public void onResponse(Response response) {
                System.out.println("@@@@@@@@@@1"+response);
            }

            @Override
            public void onSuccess_List(String resultStr) {
                System.out.println("@@@@@3"+resultStr);
            }

            @Override
            public void onSuccess(Response response, Integer integer) {
                System.out.println("@@@@@3"+response);
            }


            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }

        });
    }

    public void bindView(VH holder,int position){
        holder.btn_add.setVisibility(View.VISIBLE);
//        holder.fl.setVisibility(View.VISIBLE);
        String temp = "";
//        if(!role.contains("g")){
//            holder.fl.setVisibility(View.GONE);
//        }
        if(opType==WAREHOUSE_IN){
            if(!role.contains("b")){
                holder.btn_add.setVisibility(View.GONE);
            }
            temp = mDatas.get(position).getProductTags();
            holder.setText(R.id.tv_name, mDatas.get(position).getProductName());
            holder.setSize(R.id.tv_quantity,mDatas.get(position).getTotalAmount());
            holder.setInPrice(R.id.tv_inPrice,mDatas.get(position).getInPrice());
            holder.setDetail(R.id.tv_detail,mDatas.get(position).getProductDescription());
            holder.setText(R.id.tv_time, mDatas.get(position).getUpdateTime());
            Glide.with(fragment).load(mDatas.get(position).getProductImg()).into(holder.imageView);
        }
        else if(opType==WAREHOUSE_OUT){
            if(!role.contains("c")){
                holder.btn_add.setVisibility(View.GONE);
            }
            temp = mDatasOut.get(position).getProductTags();
            holder.setText(R.id.tv_name, mDatasOut.get(position).getProductName());
            holder.setSize(R.id.tv_quantity,mDatasOut.get(position).getTotalAmount());
            holder.setInPrice(R.id.tv_outPrice,mDatasOut.get(position).getOutPrice());
            holder.setDetail(R.id.tv_detail,mDatasOut.get(position).getProductDescription());
            holder.setText(R.id.tv_time, mDatasOut.get(position).getUpdateTime());
            Glide.with(fragment).load(mDatasOut.get(position).getProductImg()).into(holder.imageView);
        }
        if(temp.contains("a")){
            holder.setText(R.id.labeled, "售罄");
            holder.itemView.setBackgroundColor(Color.parseColor("#641E16"));
        }
        else if(temp.contains("b")){
            holder.setText(R.id.labeled, "库存告警");
            holder.itemView.setBackgroundColor(Color.parseColor("#F1948A"));
        }
        else if(temp.contains("c")){
            holder.setText(R.id.labeled, "库存正常");
            holder.itemView.setBackgroundColor(Color.parseColor("#D1F2EB"));
        }
        else if(temp.contains("d")){
            holder.setText(R.id.labeled, "库存积压");
            holder.itemView.setBackgroundColor(Color.parseColor("#ABB2B9"));
        }
        else{
            holder.setText(R.id.labeled, "无状态");
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        if(temp.contains("e")){
            holder.setText(R.id.labeled_tag, "优品");
        }
        else if(temp.contains("f")){
            holder.setText(R.id.labeled_tag, "合格品");
        }
        else if(temp.contains("g")){
            holder.setText(R.id.labeled_tag, "良品");
        }
        else if(temp.contains("h")){
            holder.setText(R.id.labeled_tag, "残次品");
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
