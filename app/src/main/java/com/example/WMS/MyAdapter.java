package com.example.WMS;



import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WMS.WarehouseIn.WarehouseInDetailFragment;
import com.example.WMS.WarehouseIn.Warehouse_Add_Fragment;
import com.example.WMS.WarehouseIn.Warehouse_New_Fragment;
import com.example.WMS.WarehouseOut.WarehouseOutDetailFragment;
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
            holder=new VH(v);
            return holder;
        }
        public VH(View v) {
            super(v);
            rl=v.findViewById(R.id.item_inlist_rl);
            fl=v.findViewById(R.id.item_inlist_fl);
            btn_add=v.findViewById(R.id.btn_add);
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
        public VH setSize(int id,int size){
            TextView textView = getView(id);
            textView.setText(size+"");
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
    private int opType;//WAREHOUSE_IN是入库，WAREHOUSE_OUT是出库后续继续添加
    private int mResId;
    private ArrayList<WarehouseItem> mDatas;
    private MainActivity activity;
    public MyAdapter(ArrayList<WarehouseItem> data,int mResId, int opType,MainActivity activity) {
        this.mDatas = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
    }
    private String warehouseName;
    private String productName;
    public MyAdapter(ArrayList<WarehouseItem> data,int mResId, int opType,MainActivity activity,String warehouseName) {
        this.mDatas = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
        this.warehouseName=warehouseName;
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
    public void onClickMethod(VH holder, final int position){
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opType==0){//入库Adater
                    WarehouseInDetailFragment warehouseInDetailFragment = new WarehouseInDetailFragment(warehouseName,mDatas.get(position).getName());
                    Toast.makeText(activity,"仓库名："+warehouseName,Toast.LENGTH_SHORT).show();
                    activity.fragment_Manager.hide_all(warehouseInDetailFragment);
                }
                else if(opType==1){//出库Adater
                    WarehouseOutDetailFragment warehouseOutDetailFragment = new WarehouseOutDetailFragment();
                    activity.fragment_Manager.hide_all(warehouseOutDetailFragment);
                }
            }
        });
        holder.fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opType==WAREHOUSE_IN){//入库Adater
                    if(mDatas.get(position).getSize()==0){
                        //执行删除list刷新ui操作
                        Toast.makeText(activity,"当前可删除",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(activity,"不为0不可删除",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(opType==WAREHOUSE_OUT){//出库Adater
                    Toast.makeText(activity,"删除",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opType==WAREHOUSE_IN){//入库Adater
                    //Warehouse_Add_Fragment warehouse_add_fragment=new Warehouse_Add_Fragment();
                    //activity.fragment_Manager.hide_all(warehouse_add_fragment);
                }
                else if(opType==WAREHOUSE_OUT){//出库Adater
                    Toast.makeText(activity,mDatas.get(position).getName()+"入库",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //public abstract void bindView(VH holder,int position);
    public void bindView(VH holder,int position){
        if(opType==WAREHOUSE_IN){
            holder.setText(R.id.tv_name, mDatas.get(position).getName());
            holder.setSize(R.id.tv_quantity,mDatas.get(position).getSize());
        }
        else if(opType==WAREHOUSE_OUT){
            holder.setText(R.id.tv_name, mDatas.get(position).getName());
        }
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
