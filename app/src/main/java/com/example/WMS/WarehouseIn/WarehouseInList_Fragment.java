package com.example.WMS.WarehouseIn;

/**
 * 入库商品列表
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


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

import com.example.WMS.My_Thread;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.Receiver_Supplier.Supplier_Fragment;
import com.example.WMS.domain.DataBean;

import com.example.WMS.execute_IO;
import com.example.WMS.perform_UI;
import com.google.gson.Gson;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;



import java.io.IOException;
import java.lang.ref.WeakReference;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


public class WarehouseInList_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    private static RecyclerView rv_pager;
    private static TextView tv_nomedia;
    private static ProgressBar pb_loading;
    private static Spinner spinner;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btn_add;
    private Button btn_scan;
    private static Button btn_select;
    private Base_Topbar base_topbar;
    private static ArrayList<DataBean.ProductIn> warehouseItems;
    private static MyAdapter<MyAdapter.VH> adapter;
    private static final String[] warehouseName={"深圳","上海"};
    private static int pos=1;//替代warehouseId
    private static String selectWarehouseName=warehouseName[pos-1];
    private static String supplierName="111";
    private static String supplierId="";
    //private MyHandler handler=new MyHandler((MainActivity) getActivity());
    private MyHandler handler;
    private long lastClickTime=0;
    private long now=0;
    private static String token;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new MyHandler((MainActivity) getActivity());
        context=getActivity();
        //token="385e5f984e094268b7b04510063242ee";
        token=((MainActivity)getActivity()).fragment_Manager.userinfo.getToken();


    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }
    //子类实现方法
    public View initView(){
        View view=View.inflate(context,R.layout.inlist_fragment,null);
        base_topbar=new Base_Topbar(view,(MainActivity)getActivity(),true);
        rv_pager=view.findViewById(R.id.lv_video_pager);
        rv_pager.setLayoutManager(new LinearLayoutManager(context));

        tv_nomedia=view.findViewById(R.id.tv_nomedia);
        pb_loading=view.findViewById(R.id.pb_loading);
        spinner=view.findViewById(R.id.spinner);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * 刷新操作在这里实现
                 * */
                getData();
                //这里获取数据的逻辑
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        btn_add=view.findViewById(R.id.add);
        btn_scan=view.findViewById(R.id.scan);
        btn_select=view.findViewById(R.id.select_supplier);
        //设置适配器
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(context, R.layout.myspinner,warehouseName);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectWarehouseName=warehouseName[position];
                pos=position+1;
                System.out.println("-----"+pos);

                warehouseItems = new ArrayList<DataBean.ProductIn>();
                getData();
                //根据选中仓库加载对应的RecycleView
                //handler.sendEmptyMessage(0);
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

        //getData();
        btn_add.setOnClickListener(this);
        btn_scan.setOnClickListener(this);
        btn_select.setOnClickListener(this);
    }

    public static void setSupplierName(String string,String id){
        //有数据后记得测试这里
        btn_select.setText(string);
        supplierName=string;
        supplierId=id;
    }

//    public void initData(){
//
//        getData();
//    }


    private void getData() {
        SharedPreferences settings = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        //token=settings.getString("token","0");
        System.out.println("_______________"+token);

        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getInInventoryProductByWarehouseId/"+pos+"?userToken="+token,new BaseCallback<DataBean.ProductIn>(){

            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("failure"+e);
            }

            @Override
            public void onResponse(Response response) {
                System.out.println("@@@@@@@@@@1"+response);
            }

            @Override


            public void onSuccess_List(final String resultStr) {
                warehouseItems = new ArrayList<DataBean.ProductIn>();
                Gson gson= new Gson();
                DataBean.ProductIn[] wares=gson.fromJson(resultStr,DataBean.ProductIn[].class);
                warehouseItems.addAll(Arrays.asList(wares));
                handler.sendEmptyMessage(0);


            }

            @Override
            public void onSuccess(Response response, DataBean.ProductIn productIn) {

                System.out.println("Success"+response);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }
        } );
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
                    rv_pager.setVisibility(View.VISIBLE);
                    //lv_video_pager.setAdapter(new WarehouseInList_Fragment.WarehouseInListAdapter(DataBean.ProductIns));
                    System.out.println("----------------------");
                    adapter=new MyAdapter<MyAdapter.VH>(warehouseItems, R.layout.item_inlist,0,activity,selectWarehouseName,supplierName,token,supplierId);
                    rv_pager.setAdapter(adapter);

                }
                else{
                    System.out.println("@@@@@@@@@@222");
                    rv_pager.setVisibility(View.GONE);

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

        if(v==btn_add){
            now = System.currentTimeMillis();
            if(now - lastClickTime >1000) {
                lastClickTime = now;
                Warehouse_New_Fragment warehouse_new_fragment = new Warehouse_New_Fragment(selectWarehouseName,pos,token);
                ((MainActivity)getActivity()).fragment_Manager.hide_all(warehouse_new_fragment);
            }

        }
        else if(v==btn_scan){

        }
        else if(v==btn_select){
            now = System.currentTimeMillis();
            if(now - lastClickTime >1000) {
                lastClickTime = now;
                Supplier_Fragment supplier_fragment = new Supplier_Fragment(token);
                ((MainActivity) getActivity()).fragment_Manager.hide_all(supplier_fragment);
            }
        }
    }
    class myTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }
}