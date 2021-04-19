package com.example.WMS.WareOperation.Categroy;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WMS.BaseCallback;
import com.example.WMS.MainActivity;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.WareOperation.Receiver_Supplier.RS_Adapter;
import com.example.WMS.WareOperation.WarehouseIn.WarehouseInList_Fragment;
import com.example.WMS.WareOperation.WarehouseOut.WarehouseOutList_Fragment;
import com.example.WMS.domain.DataBean;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

public class Category_Adapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<Category_Adapter.VH>{
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
        public VH setText(int id, String text){
            TextView textView = getView(id);
            textView.setText(text);
            return this;
        }
    }
    private ArrayList<DataBean.Category> mData;
    private int mResId;
    private MainActivity activity;
    private String token;
    private int opType;
    public Category_Adapter(int mResId, ArrayList<DataBean.Category> data, MainActivity activity,String token, int opType) {
        this.mData = data;
        this.mResId = mResId;
        this.activity=activity;
        this.token=token;
        this.opType = opType;
    }

    @NonNull
    @Override
    public Category_Adapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return VH.getHolder(mResId,parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull Category_Adapter.VH holder, int position) {
        bindView(holder,position);
        onClickMethod(holder,position);
    }

    private int selectedPos = 0;
    private int oldPos = 0;

    private void onClickMethod(final VH holder, final int position) {
        if(opType == 0){
            if (position == SelectItem.getId()) {
                holder.itemView.setBackgroundColor(Color.parseColor("#85C1E9"));
            } else {
                holder.itemView.setBackgroundColor(Color.LTGRAY);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        else if(opType == 1){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                }
            });
            holder.fl_delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    DeleteData(mData.get(position).getCategoryId());
                    notifyItemRemoved(position);
                    Toast.makeText(activity,"删除成功",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    private OnItemClickListener onItemClickListener = null;

    /*暴露给外部的方法*/
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }


    public void DeleteData(int id){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.delete_for_list("http://121.199.22.134:8003/api-inventory/deleteCategoryById/"+id+"?userToken="+token,new BaseCallback<Integer>(){
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
        holder.setText(R.id.tv_category, mData.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

