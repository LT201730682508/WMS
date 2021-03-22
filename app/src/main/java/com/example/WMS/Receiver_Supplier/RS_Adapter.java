package com.example.WMS.Receiver_Supplier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WMS.MainActivity;

import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model;
import com.example.WMS.R;
import com.example.WMS.WarehouseIn.WarehouseInList_Fragment;
import com.example.WMS.WarehouseIn.Warehouse_Add_Fragment;
import com.example.WMS.WarehouseOut.WarehouseOutList_Fragment;
import com.example.WMS.domain.DataBean;

import java.util.ArrayList;

public class RS_Adapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RS_Adapter.VH>{
    public static class VH extends RecyclerView.ViewHolder{
        private RelativeLayout rl;
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
    }
    private final int SUPPLIER=0;
    private final int RECEIVER=1;
    private ArrayList<DataBean.Receiver> mDatas_Receiver;
    private ArrayList<DataBean.Supplier> mDatas_Supplier;
    private int mResId;
    private int opType;
    private MainActivity activity;

    public RS_Adapter(int mResId, ArrayList<DataBean.Supplier> data,  int opType,MainActivity activity) {
        this.mDatas_Supplier = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;

    }
    public RS_Adapter( ArrayList<DataBean.Receiver> data,int mResId,  int opType,MainActivity activity) {
        this.mDatas_Receiver = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
    }
    @NonNull
    @Override
    public RS_Adapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
                    SharedPreferences preferences=activity.getSharedPreferences("supplier",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("supplierName",mDatas_Supplier.get(position).getSupplierName());
                    editor.putString("supplierId",mDatas_Supplier.get(position).getSupplierId()+"");
                    editor.commit();
                    WarehouseInList_Fragment.setSupplierName( mDatas_Supplier.get(position).getSupplierName());
                    activity.fragment_Manager.pop();
                }
        else if(opType==RECEIVER){
                    //选中返回
                    //返回当前选中客户进入textview中
                    SharedPreferences preferences=activity.getSharedPreferences("receiver",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("receiverName",mDatas_Receiver.get(position).getReceiverName());
                    editor.putString("receiverId",mDatas_Receiver.get(position).getReceiverId()+"");
                    editor.commit();
                    WarehouseOutList_Fragment.setReceiverName( mDatas_Receiver.get(position).getReceiverName());
                    activity.fragment_Manager.pop();
                }
            }
        });
    }

    private void bindView(VH holder, int position) {
        if(opType==SUPPLIER){
            holder.setText(R.id.tv_name, mDatas_Supplier.get(position).getSupplierName());
            holder.setText(R.id.tv_address,mDatas_Supplier.get(position).getSupplierAddress());
        }
        else if(opType==RECEIVER){
            holder.setText(R.id.tv_name, mDatas_Receiver.get(position).getReceiverName());
            holder.setText(R.id.tv_address,mDatas_Receiver.get(position).getReceiverAddress());
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
