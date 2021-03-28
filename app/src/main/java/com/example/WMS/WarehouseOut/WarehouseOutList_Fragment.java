package com.example.WMS.WarehouseOut;
/**
 * 出库商品列表
 * */
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.WMS.BaseCallback;
import com.example.WMS.Base_Topbar;
import com.example.WMS.MainActivity;
import com.example.WMS.MyAdapter;

import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.Receiver_Supplier.Receiver_Fragment;

import com.example.WMS.domain.DataBean;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WarehouseOutList_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    private static RecyclerView rv_pager;
    private static TextView tv_nomedia;
    private static ProgressBar pb_loading;
    private static Spinner spinner;
    private static Button btn_select;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Base_Topbar base_topbar;
    private Button btn_scan;
    private static MyAdapter<MyAdapter.VH> adapter;
    private static ArrayList<DataBean.ProductOut> warehouseItems;
    private static final String[] warehouseName={"深圳","上海"};
    private static int pos=1;
    private static String selectWarehouseName=warehouseName[pos-1];
    private static String receiverName="";
    private static String receiverId;
    private static String token;
    //private MyHandler handler=new MyHandler((MainActivity) getActivity());
    private MyHandler handler;
    private long lastClickTime=0;
    private long now=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new MyHandler((MainActivity) getActivity());
        context=getActivity();
        token=((MainActivity)getActivity()).fragment_Manager.userinfo.getToken();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }
    
    public View initView(){
        View view=View.inflate(context,R.layout.outlist_fragment,null);
        base_topbar=new Base_Topbar(view,(MainActivity) getActivity(),true);
        rv_pager=view.findViewById(R.id.lv_video_pager);
        rv_pager.setLayoutManager(new LinearLayoutManager(context));
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * 刷新操作在这里实现
                 * */
                //这里获取数据的逻辑
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        tv_nomedia=view.findViewById(R.id.tv_nomedia);
        pb_loading=view.findViewById(R.id.pb_loading);
        spinner=view.findViewById(R.id.spinner);
        btn_scan=view.findViewById(R.id.scan);
        btn_select=view.findViewById(R.id.select_receiver);
        //设置适配器
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(context, R.layout.myspinner,warehouseName);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectWarehouseName=warehouseName[position];
                pos=position+1;
                warehouseItems = new ArrayList<DataBean.ProductOut>();
                getData();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //todo-something
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_scan.setOnClickListener(this);
        btn_select.setOnClickListener(this);
    }

    private void getData() {
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getOutInventoryProductByWarehouseId/"+pos+"?userToken="+token,new BaseCallback<DataBean.ProductOut>(){
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("failure"+e);
            }
            @Override
            public void onResponse(Response response) {
                System.out.println("response"+response);
            }
            @Override
            public void onSuccess_List(String resultStr) {
                warehouseItems = new ArrayList<DataBean.ProductOut>();
                Gson gson= new Gson();
                DataBean.ProductOut[] wares=gson.fromJson(resultStr,DataBean.ProductOut[].class);
                for (int i=0;i<wares.length;i++){
                    warehouseItems.add(wares[i]);
                }
                handler.sendEmptyMessage(0);
            }
            @Override
            public void onSuccess(Response response, DataBean.ProductOut productOut) {
                System.out.println("Success"+response);
            }
            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }
        });
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
                    adapter=new MyAdapter<MyAdapter.VH>(R.layout.item_outlist, warehouseItems, 1,activity,selectWarehouseName,receiverName,token,receiverId);
                    rv_pager.setAdapter(adapter);
                }
                else{
                    tv_nomedia.setVisibility(View.VISIBLE);
                    pb_loading.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    public static void setReceiverName(String string){
        btn_select.setText(string);
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
        if(v==btn_scan){
            //todo-something
        }
        else if(v==btn_select){
            now = System.currentTimeMillis();
            if(now - lastClickTime >1000) {
                lastClickTime = now;
                Receiver_Fragment receiver_fragment = new Receiver_Fragment(token);
                ((MainActivity) getActivity()).fragment_Manager.hide_all(receiver_fragment);
            }
        }
    }
}