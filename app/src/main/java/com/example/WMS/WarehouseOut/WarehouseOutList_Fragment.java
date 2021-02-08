package com.example.WMS.WarehouseOut;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.WMS.R;
import com.example.WMS.WarehouseIn.WarehouseInDetailFragment;
import com.example.WMS.WarehouseIn.WarehouseInList_Fragment;
import com.example.WMS.domain.WarehouseItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WarehouseOutList_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    private static RecyclerView rv_pager;
    private static TextView tv_nomedia;
    private static ProgressBar pb_loading;
    private static Spinner spinner;
    private ImageView btn_fanhui;
    private ImageView btn_gengduo;
    private static ArrayList<WarehouseItem> warehouseItems;
    private static final String[] warehouseName={"深圳","上海","北京","山西"};
    //private MyHandler handler=new MyHandler((MainActivity) getActivity());
    private WarehouseOutList_Fragment.MyHandler handler;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new WarehouseOutList_Fragment.MyHandler((MainActivity) getActivity());
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_fanhui.setOnClickListener(this);
        btn_gengduo.setOnClickListener(this);
        initData();
    }

    public void initData(){
        //getData();
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                warehouseItems = new ArrayList<WarehouseItem>();
//                WarehouseItem warehouseItem=new WarehouseItem();
//                //假数据
//                warehouseItem.setName("仓库1");
//                warehouseItems.add(warehouseItem);
//                warehouseItem=new WarehouseItem();
//                //假数据
//                warehouseItem.setName("仓库2");
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//
//                handler.sendEmptyMessage(0);
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
                    //设置适配器
                    ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(activity, R.layout.myspinner,warehouseName);
                    spinner.setAdapter(spinnerAdapter);
                    //lv_video_pager.setAdapter(new WarehouseInList_Fragment.WarehouseInListAdapter(warehouseItems));
                    rv_pager.setAdapter(new MyAdapter<MyAdapter.VH>(warehouseItems, R.layout.item_inlist) {
                        @Override
                        public void onClickMethod(VH holder, int position) {
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    WarehouseOutDetailFragment warehouseOutDetailFragment = new WarehouseOutDetailFragment();
                                    activity.fragment_Manager.hide_all(warehouseOutDetailFragment);
                                    //Toast.makeText(context,"111",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void bindView(VH holder, int position) {
                            holder.setText(R.id.tv_name, warehouseItems.get(position).getName());
                        }
                    });
                }
                else{
                    tv_nomedia.setVisibility(View.VISIBLE);
                    pb_loading.setVisibility(View.VISIBLE);
                }
            }
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
    }

}