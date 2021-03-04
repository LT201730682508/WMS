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
import com.example.WMS.WarehouseIn.Warehouse_New_Fragment;
import com.example.WMS.WarehouseOut.WarehouseOutDetailFragment;
import com.example.WMS.WarehouseOut.Warehouse_Delete_Dialog;
import com.example.WMS.domain.WarehouseItem;

import java.util.ArrayList;

public class MyAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<MyAdapter.VH> {
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    public static class VH extends RecyclerView.ViewHolder{
        //public final TextView title;
        private SparseArray<View> views = new SparseArray<>();
        private RelativeLayout rl;
        private FrameLayout fl;
        private Button btn_add;

        private ProgressBar pbLoading;
        private TextView tvLoading;
        private LinearLayout llEnd;

        public static  VH getHolder(int mResId, ViewGroup parent, int viewType){
            VH holder;
            if(viewType==TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
                holder = new VH(v);
                return holder;
            }
            else if(viewType==TYPE_FOOTER){
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_refresh_footer, parent, false);
                holder = new VH(v);
                return holder;
            }
            return null;
        }
        public VH(View v) {
            super(v);
            rl=v.findViewById(R.id.item_rl);
            fl=v.findViewById(R.id.item_fl);
            btn_add=v.findViewById(R.id.btn_add);


            pbLoading=v.findViewById(R.id.list_pb_loading);
            tvLoading=v.findViewById(R.id.tv_loading);
            llEnd=v.findViewById(R.id.ll_end);
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
    private final int WAREHOUSE_CHECK=2;
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
    public MyAdapter(ArrayList<WarehouseItem> data,int mResId, int opType,MainActivity activity,String warehouseName) {
        this.mDatas = data;
        this.mResId = mResId;
        this.opType = opType;
        this.activity=activity;
        this.warehouseName=warehouseName;
    }
    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
    @NonNull
    @Override
    public MyAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inlist, parent, false);
        //return new MyAdapter.VH(v);
        return VH.getHolder(mResId,parent,viewType);
    }
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.VH holder, int position) {
        if(opType==WAREHOUSE_IN||opType==WAREHOUSE_OUT){
            if(getItemViewType(position)==TYPE_ITEM){
                bindView(holder,position);
                onClickMethod(holder,position);
            }
            else if(getItemViewType(position)==TYPE_FOOTER){
                switch (loadState) {
                    case LOADING: // 正在加载
                        holder.pbLoading.setVisibility(View.VISIBLE);
                        holder.tvLoading.setVisibility(View.VISIBLE);
                        holder.llEnd.setVisibility(View.GONE);
                        break;

                    case LOADING_COMPLETE: // 加载完成
                        holder.pbLoading.setVisibility(View.INVISIBLE);
                        holder.tvLoading.setVisibility(View.INVISIBLE);
                        holder.llEnd.setVisibility(View.GONE);
                        break;

                    case LOADING_END: // 加载到底
                        holder.pbLoading.setVisibility(View.GONE);
                        holder.tvLoading.setVisibility(View.GONE);
                        holder.llEnd.setVisibility(View.VISIBLE);
                        break;

                    default:
                        break;
                }
            }
        }
        else if(opType==WAREHOUSE_CHECK){
            bindView(holder,position);
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


    }
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
    //public abstract void onClickMethod(VH holder, int position);
    public void onClickMethod(VH holder, final int position){
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opType==0){//入库Adater
                    WarehouseInDetailFragment warehouseInDetailFragment = new WarehouseInDetailFragment(warehouseName,mDatas.get(position).getProduct().getProductName());
                    Toast.makeText(activity,"仓库名："+warehouseName,Toast.LENGTH_SHORT).show();
                    activity.fragment_Manager.hide_all(warehouseInDetailFragment);
                }
                else if(opType==1){//出库Adater
//                    WarehouseOutDetailFragment warehouseOutDetailFragment = new WarehouseOutDetailFragment();
//                    activity.fragment_Manager.hide_all(warehouseOutDetailFragment);
                }
                else if(opType==2){

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
                else if(opType==2){

                }
            }
        });
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opType==WAREHOUSE_IN){//入库Adater
                    Warehouse_Add_Fragment warehouse_add_fragment=new Warehouse_Add_Fragment(activity,mDatas.get(position));
                    warehouse_add_fragment.show();
                }
                else if(opType==WAREHOUSE_OUT){//出库Adater
                    Warehouse_Delete_Dialog warehouse_delete_dialog=new Warehouse_Delete_Dialog(activity,mDatas.get(position));
                    warehouse_delete_dialog.show();
                }
                else if(opType==2){

                }
            }
        });

    }
    //public abstract void bindView(VH holder,int position);
    public void bindView(VH holder,int position){
        if(opType==WAREHOUSE_IN){
            holder.setText(R.id.tv_name, mDatas.get(position).getProduct().getProductName());
            holder.setSize(R.id.tv_quantity,mDatas.get(position).getTotalAmount());
        }
        else if(opType==WAREHOUSE_OUT){
            holder.setText(R.id.tv_name, mDatas.get(position).getProduct().getProductName());
        }
        else if(opType==WAREHOUSE_CHECK){

        }
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }


}
