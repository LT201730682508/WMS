package com.example.WMS.WareOperation.Receiver_Supplier;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WMS.BaseCallback;
import com.example.WMS.MainActivity;

import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.WareOperation.WarehouseIn.WarehouseInList_Fragment;
import com.example.WMS.WareOperation.WarehouseOut.WarehouseOutList_Fragment;
import com.example.WMS.domain.DataBean;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

public class RS_Adapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RS_Adapter.VH>{
    public static class VH extends RecyclerView.ViewHolder{
        private LinearLayout rl;
        private FrameLayout fl_delete;
        //private FrameLayout fl_write;
        private SparseArray<View> views = new SparseArray<>();
        public VH(View itemView) {
            super(itemView);
            rl=itemView.findViewById(R.id.item_rl);
            fl_delete=itemView.findViewById(R.id.item_fl_2);
            //fl_write=itemView.findViewById(R.id.item_fl);
        }
        public static VH getHolder(int mResId, ViewGroup parent, int viewType){
            VH holder;
            View v = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
            holder = new VH(v);
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
        public VH setText(int id,String text){
            TextView textView = getView(id);
            textView.setText(text);
            return this;
        }
        public VH setBackground(int id){
            TextView textView = getView(id);
            textView.setBackgroundColor(Color.RED);
            return this;
        }
    }
    private final int SUPPLIER=0;
    private final int RECEIVER=1;
    private ArrayList<DataBean.Receiver> mDatas_Receiver;
    private ArrayList<DataBean.Supplier> mDatas_Supplier;
    private int mResId;
    private int opType;
    private MainActivity activity;
    private String token;

    public RS_Adapter(int mResId, ArrayList<DataBean.Supplier> data,  int opType,MainActivity activity,String token) {
        this.mDatas_Supplier = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
        this.token=token;
    }

    public RS_Adapter( ArrayList<DataBean.Receiver> data,int mResId,  int opType,MainActivity activity,String token) {
        this.mDatas_Receiver = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
        this.token=token;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return VH.getHolder(mResId,parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RS_Adapter.VH holder, int position) {
        bindView(holder,position);
        onClickMethod(holder,position);
    }

    private void onClickMethod(VH holder, final int position) {
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opType==SUPPLIER){
                    //选中返回
                    //返回当前选中供应商进入textview中
                    RS_Modify_Fragment rs_modify_fragment = new RS_Modify_Fragment(token, opType, mDatas_Supplier.get(position), activity);
//                    activity.fragment_Manager.pop();
                    activity.fragment_Manager.hide_all(rs_modify_fragment);
                }
                else if(opType==RECEIVER){
                    //选中返回
                    //返回当前选中客户进入textview中
                    RS_Modify_Fragment rs_modify_fragment = new RS_Modify_Fragment(token, opType, mDatas_Receiver.get(position), activity);
//                    activity.fragment_Manager.pop();
                    activity.fragment_Manager.hide_all(rs_modify_fragment);
                }
            }
        });
        holder.fl_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(opType==SUPPLIER){
                    DeleteData(mDatas_Supplier.get(position).getSupplierId());
                    notifyItemRemoved(position);
                    Toast.makeText(activity,"删除成功",Toast.LENGTH_SHORT).show();
                }
                else if(opType==RECEIVER){
                    DeleteData(mDatas_Receiver.get(position).getReceiverId());
                    notifyItemRemoved(position);
                    Toast.makeText(activity,"删除成功",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void DeleteData(int id){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        String opStr="";
        if(opType == 0){
            opStr="deleteSupplierById";
        }
        else if(opType == 1){
            opStr="deleteReceiverById";
        }
        if (opStr == "") {
            return;
        }
        okHttpHelper.delete_for_list("http://121.199.22.134:8003/api-inventory/"+opStr+"/"+id+"?userToken="+token,new BaseCallback<Integer>(){
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

    private void bindView(VH holder, int position) {
        String tag = "";
        String str = "";
        if(opType==SUPPLIER){
            holder.setText(R.id.tv_name, mDatas_Supplier.get(position).getSupplierName());
            holder.setText(R.id.tv_address, mDatas_Supplier.get(position).getSupplierAddress());
            holder.setText(R.id.tv_company, mDatas_Supplier.get(position).getSupplierCompany());
            holder.setText(R.id.tv_contract, mDatas_Supplier.get(position).getSupplierContact());
            tag = mDatas_Supplier.get(position).getTags();
            if(tag.contains("a")){
                str += "交货及时 ";
            }
            if(tag.contains("b")){
                str += "可以赊账 ";
            }
            if(tag.contains("c")){
                str += "质量上乘 ";
            }
            if(tag.contains("d")){
                str += "质量较差 ";
                holder.setBackground(R.id.tag);
            }
            holder.setText(R.id.tag,str);
        }
        else if(opType==RECEIVER){
            holder.setText(R.id.tv_name, mDatas_Receiver.get(position).getReceiverName());
            holder.setText(R.id.tv_address, mDatas_Receiver.get(position).getReceiverAddress());
            holder.setText(R.id.tv_company, mDatas_Receiver.get(position).getReceiverCompany());
            holder.setText(R.id.tv_contract, mDatas_Receiver.get(position).getReceiverContact());
            tag = mDatas_Receiver.get(position).getTags();
            if(tag.contains("a")){
                str += "拖欠尾款 ";
                holder.setBackground(R.id.tag);
            }
            if(tag.contains("b")){
                str += "付款及时 ";
            }
            if(tag.contains("c")){
                str += "提货量大 ";
            }
            if(tag.contains("d")){
                str += "提货量小 ";
                holder.setBackground(R.id.tag);
            }
            holder.setText(R.id.tag,str);
        }
    }

    @Override
    public int getItemCount() {
        if(opType==SUPPLIER){
            return mDatas_Supplier.size();
        }
        else if(opType==RECEIVER){
            return mDatas_Receiver.size();
        }
        return 0;
    }
}
