package com.example.WMS.WarehouseIn;

/**
 * 入库商品列表
 */

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.WMS.BaseCallback;
import com.example.WMS.Base_Topbar;
import com.example.WMS.EndlessRecyclerOnScrollListener;
import com.example.WMS.MainActivity;
import com.example.WMS.MyAdapter;
import com.example.WMS.My_Thread;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.domain.Product;
import com.example.WMS.domain.WarehouseItem;
import com.example.WMS.execute_IO;
import com.example.WMS.perform_UI;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private Base_Topbar base_topbar;
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
                handler.sendEmptyMessage(0);
//                warehouseItems = new ArrayList<WarehouseItem>();
//                WarehouseItem warehouseItem=new WarehouseItem();
//                //赋予初始化仓库名
//                selectWarehouseName=warehouseName[0];
//                //假数据
//                warehouseItem.setName("仓库3");
//                warehouseItem.setSize(433333);
//                warehouseItem.setWarehouse_name("深圳");
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                handler.sendEmptyMessage(0);
//                //adapter.notifyDataSetChanged();
//                //这里获取数据的逻辑
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        btn_add=view.findViewById(R.id.add);
        btn_scan=view.findViewById(R.id.scan);
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

        btn_add.setOnClickListener(this);
        btn_scan.setOnClickListener(this);
        initData();
    }

    public void initData(){

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
                Product product=new Product();
                product.setProductName("广州");
                warehouseItem.setProduct(product);
                warehouseItem.setId(1);
                warehouseItem.setTotalAmount(1000);
                warehouseItems.add(warehouseItem);
                //getData();
                warehouseItem=new WarehouseItem();
                product=new Product();
                product.setProductName("深圳");
                warehouseItem.setProduct(product);
                warehouseItem.setId(2);
                warehouseItem.setTotalAmount(12000);
                warehouseItems.add(warehouseItem);
//                warehouseItems = new ArrayList<WarehouseItem>();
//                WarehouseItem warehouseItem=new WarehouseItem();
//                //赋予初始化仓库名
//                selectWarehouseName=warehouseName[0];
//                //假数据
//                warehouseItem.setName("仓库1");
//                warehouseItem.setSize(433333);
//                warehouseItem.setWarehouse_name("深圳");
//                warehouseItems.add(warehouseItem);
//
//                warehouseItem=new WarehouseItem();
//                //假数据
//                warehouseItem.setName("仓库2");
//                //warehouseItem.setWarehouse_name("上海");
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
//                warehouseItems.add(warehouseItem);
            }
        });
    }

    private void getData() {
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://172.21.245.42:8003/api-order/getInventory/1",new BaseCallback<WarehouseItem>(){

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override

            public void onSuccess_List(String resultStr) {

                Gson gson= new Gson();
                WarehouseItem[] wares=gson.fromJson(resultStr,WarehouseItem[].class);
                //System.out.println("a"+wares[0]);
        //        warehouseItems.add(wares[0]);


//                    String resultStr = null;
//                    try {
//                        resultStr = response.body().string();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    Gson gson= new Gson();
//                    WarehouseItem[] wares=gson.fromJson(resultStr,WarehouseItem[].class);
//                    System.out.println("a  "+resultStr);
//                    System.out.println("a  "+wares[0].getId());
//                    warehouseItems.add(wares[0]);
//                    warehouseItems.add(wares[1]);

                //WarehouseItem warehouseItem=new WarehouseItem();
                //warehouseItem.setId(wares[0].getId());
                //warehouseItem.setProduct(wares[0].getProduct());
            }

            @Override
            public void onSuccess(Response response, WarehouseItem warehouseItem) {

                warehouseItems.add(warehouseItem);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("aaa"+response+e);
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

                    //lv_video_pager.setAdapter(new WarehouseInList_Fragment.WarehouseInListAdapter(warehouseItems));
                    adapter=new MyAdapter<MyAdapter.VH>(warehouseItems, R.layout.item_inlist,0,activity,selectWarehouseName);
                    rv_pager.setAdapter(adapter);
//                    rv_pager.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//                        @Override
//                        public void onLoadMore() {
//                            /**
//                             * 下拉加载在此处实现
//                             * */
//
//                            adapter.setLoadState(adapter.LOADING_END);
//                        }
//                    });
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
        if(v==btn_add){
            Warehouse_New_Fragment warehouse_new_fragment = new Warehouse_New_Fragment(selectWarehouseName);
            ((MainActivity)getActivity()).fragment_Manager.hide_all(warehouse_new_fragment);
        }
        else if(v==btn_scan){
            //Warehouse_Add_Fragment warehouse_add_fragment=new Warehouse_Add_Fragment(context);
            //warehouse_add_fragment.show();
        }
    }
    class myTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }
}