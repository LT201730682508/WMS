package com.example.WMS.WarehouseIn;

/**
 * 入库商品列表
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WMS.MainActivity;
import com.example.WMS.MyAdapter;
import com.example.WMS.My_Thread;
import com.example.WMS.R;
import com.example.WMS.domain.WarehouseItem;
import com.example.WMS.execute_IO;
import com.example.WMS.perform_UI;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WarehouseInList_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    private static RecyclerView rv_pager;
    private static TextView tv_nomedia;
    private static ProgressBar pb_loading;
    private static Spinner spinner;
    private ImageView btn_fanhui;
    private ImageView btn_gengduo;
    private Button btn_add;
    private static ArrayList<WarehouseItem> warehouseItems;
    private static MyAdapter<MyAdapter.VH> adapter;
    private static final String[] warehouseName={"深圳","上海","北京","山西"};
    private static String selectWarehouseName;
    //private MyHandler handler=new MyHandler((MainActivity) getActivity());
    private MyHandler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new MyHandler((MainActivity) getActivity());
        context=getActivity();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }
    //子类实现方法
    public View initView(){
        View view=View.inflate(context,R.layout.inlist_fragment,null);
        rv_pager=view.findViewById(R.id.lv_video_pager);
        rv_pager.setLayoutManager(new LinearLayoutManager(context));
        tv_nomedia=view.findViewById(R.id.tv_nomedia);
        pb_loading=view.findViewById(R.id.pb_loading);
        spinner=view.findViewById(R.id.spinner);
        btn_fanhui=view.findViewById(R.id.fanhui);
        btn_gengduo=view.findViewById(R.id.gengduo);
        btn_add=view.findViewById(R.id.add);

        //设置适配器
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(context, R.layout.myspinner,warehouseName);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectWarehouseName=warehouseName[position];
                //根据选中仓库加载对应的RecycleView
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_fanhui.setOnClickListener(this);
        btn_gengduo.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        initData();
    }

    public void initData(){
        //getData();
        My_Thread.Companion.new_thread(new perform_UI() {
            @Override
            public void show() {
                handler.sendEmptyMessage(0);
            }
        }, new execute_IO() {
            @Override
            public void execute() {
                warehouseItems = new ArrayList<WarehouseItem>();
                WarehouseItem warehouseItem=new WarehouseItem();
                //赋予初始化仓库名
                selectWarehouseName=warehouseName[0];
                //假数据
                warehouseItem.setName("仓库1");
                warehouseItem.setSize(433333);
                warehouseItem.setWarehouse_name("深圳");
                warehouseItems.add(warehouseItem);

                warehouseItem=new WarehouseItem();
                //假数据
                warehouseItem.setName("仓库2");
                //warehouseItem.setWarehouse_name("上海");
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
                warehouseItems.add(warehouseItem);
            }
        });
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//
//
//            }
//        }.start();
    }
    private static class MyHandler extends Handler{
        private final WeakReference<MainActivity> mActivity;
        public MyHandler(MainActivity activity){
            mActivity =new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            final MainActivity activity=mActivity.get();
            super.handleMessage(msg);
            if(activity!=null){
                if(warehouseItems!=null&&warehouseItems.size()>0){
                    tv_nomedia.setVisibility(View.GONE);
                    pb_loading.setVisibility(View.GONE);

                    //lv_video_pager.setAdapter(new WarehouseInList_Fragment.WarehouseInListAdapter(warehouseItems));
                    adapter=new MyAdapter<MyAdapter.VH>(warehouseItems, R.layout.item_inlist,0,activity,selectWarehouseName);
                    rv_pager.setAdapter(adapter);
                }
                else{
                    tv_nomedia.setVisibility(View.VISIBLE);
                    pb_loading.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    //返回该framgent时刷新数据
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(isHidden()){
        }else {
            onResume();
        }
    }
    @Override
    public void onClick(View v) {
        if(v==btn_fanhui){
            ((MainActivity)getActivity()).fragment_Manager.pop();
        }
        else if(v==btn_gengduo){
            Toast.makeText(context,"更多",Toast.LENGTH_SHORT).show();
        }
        else if(v==btn_add){
            Warehouse_New_Fragment warehouse_new_fragment = new Warehouse_New_Fragment(selectWarehouseName);
            ((MainActivity)getActivity()).fragment_Manager.hide_all(warehouse_new_fragment);
        }
    }

//    public class WarehouseInListAdapter extends RecyclerView.Adapter<WarehouseInListAdapter.VH>{
//        //② 创建ViewHolder
//        public class VH extends RecyclerView.ViewHolder{
//            public final TextView title;
//            public VH(View v) {
//                super(v);
//                title = (TextView) v.findViewById(R.id.tv_name);
//            }
//        }
//
//        private ArrayList<WarehouseItem> mDatas;
//        public WarehouseInListAdapter(ArrayList<WarehouseItem> data) {
//            this.mDatas = data;
//        }
//
//        //③ 在Adapter中实现3个方法
//        @Override
//        public void onBindViewHolder(VH holder, int position) {
//            holder.title.setText(mDatas.get(position).getName());
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    WarehouseInDetailFragment warehouseInDetailFragment=new WarehouseInDetailFragment();
//                    ((MainActivity)getActivity()).fragment_Manager.hide_all(warehouseInDetailFragment);
//                    //Toast.makeText(context,"111",Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return mDatas.size();
//        }
//
//        @Override
//        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
//            //LayoutInflater.from指定写法
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inlist, parent, false);
//            return new VH(v);
//        }
//    }
}