package com.example.WMS.WareOperation.Receiver_Supplier;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.WMS.BaseCallback;
import com.example.WMS.Base_Topbar;
import com.example.WMS.MainActivity;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.WareOperation.Categroy.Category_Adapter;
import com.example.WMS.WareOperation.Categroy.Category_Add_Dialog;
import com.example.WMS.WareOperation.Categroy.Categroy_Fragment;
import com.example.WMS.WareOperation.WarehouseIn.WarehouseInList_Fragment;
import com.example.WMS.WareOperation.WarehouseOut.WarehouseOutList_Fragment;
import com.example.WMS.domain.DataBean;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xuexiang.xui.widget.edittext.ClearEditText;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RS_Modify_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    private static ArrayList<DataBean.Supplier_history> supplier_histories;
    private static ArrayList<DataBean.Receiver_history> receiver_histories;
    private static RecyclerView rv_pager;
    private static TextView tv_empty;
    private MyHandler handler;
    private Base_Topbar base_topbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static History_Adapter<History_Adapter.VH> adapter;
    private static String token;
    private static int opType;
    private Button btn_modify;
    private Button btn_commit;
    private DataBean.Supplier supplier;
    private DataBean.Receiver receiver;
    private ClearEditText et_name;
    private ClearEditText et_address;
    private ClearEditText et_contact;
    private ClearEditText et_company;
    private CheckBox checkbox1;
    private CheckBox checkbox2;
    private CheckBox checkbox3;
    private CheckBox checkbox4;
    private String tags = "";
    private static  Fragment fragment;
    private MainActivity activity;
    public RS_Modify_Fragment(String token, int opType, DataBean.Supplier supplier, MainActivity activity) {
        this.token=token;
        this.supplier = supplier;
        this.opType = opType;
        this.activity = activity;
    }
    public RS_Modify_Fragment(String token, int opType, DataBean.Receiver receiver, MainActivity activity) {
        this.token=token;
        this.receiver = receiver;
        this.opType = opType;
        this.activity = activity;
    }

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

    private View initView() {
        View view=View.inflate(context,R.layout.fragment_rs_modify,null);
        base_topbar=new Base_Topbar(view,(MainActivity)getActivity(),true);
        rv_pager=view.findViewById(R.id.rv_pager);
        rv_pager.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_pager.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        tv_empty=view.findViewById(R.id.tv_nomedia);
        btn_modify = view.findViewById(R.id.btn_modify);
        btn_commit = view.findViewById(R.id.btn_commit);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * 刷新操作在这里实现
                 * */
                initData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        fragment = this;
        et_name = view.findViewById(R.id.et_name);
        et_address = view.findViewById(R.id.et_address);
        et_contact = view.findViewById(R.id.et_contract);
        et_company = view.findViewById(R.id.et_company);
        checkbox1 = view.findViewById(R.id.checkbox1);
        checkbox2 = view.findViewById(R.id.checkbox2);
        checkbox3 = view.findViewById(R.id.checkbox3);
        checkbox4 = view.findViewById(R.id.checkbox4);
        tags = "";
        if(opType == 0) {
            et_name.setText(supplier.getSupplierName());
            et_address.setText(supplier.getSupplierAddress());
            et_company.setText(supplier.getSupplierCompany());
            et_contact.setText(supplier.getSupplierContact());
            checkbox1.setText("交货及时");
            checkbox2.setText("可以赊账");
            checkbox3.setText("质量上乘");
            checkbox4.setText("质量较差");
            tags = supplier.getTags();
            if (tags.contains("a")) {
                checkbox1.setChecked(true);
            }
            if (tags.contains("b")) {
                checkbox2.setChecked(true);
            }
            if (tags.contains("c")) {
                checkbox3.setChecked(true);
            }
            if (tags.contains("d")) {
                checkbox4.setChecked(true);
            }
        }
        else if(opType == 1){
            et_name.setText(receiver.getReceiverName());
            et_address.setText(receiver.getReceiverAddress());
            et_company.setText(receiver.getReceiverCompany());
            et_contact.setText(receiver.getReceiverContact());
            checkbox1.setText("拖欠尾款");
            checkbox2.setText("付款及时");
            checkbox3.setText("提货量大");
            checkbox4.setText("提货量小");
            tags = receiver.getTags();
            if (tags.contains("a")) {
                checkbox1.setChecked(true);
            }
            if (tags.contains("b")) {
                checkbox2.setChecked(true);
            }
            if (tags.contains("c")) {
                checkbox3.setChecked(true);
            }
            if (tags.contains("d")) {
                checkbox4.setChecked(true);
            }
        }


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_commit.setOnClickListener(this);
        btn_modify.setOnClickListener(this);
        initData();
    }

    private void initData() {
        if(opType == 0){
            supplier_histories=new ArrayList<DataBean.Supplier_history>();
            getSupplierData();
        }
        else if(opType == 1){
            receiver_histories=new ArrayList<DataBean.Receiver_history>();
            getReceiverData();
        }
    }

    private void getSupplierData() {
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getInWarehouseRecordBySupplierId?userToken="+token+"&supplierId="+supplier.getSupplierId(),new BaseCallback<DataBean.Supplier_history>(){

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
                Gson gson= new Gson();
                DataBean.Supplier_history[] wares=gson.fromJson(resultStr,DataBean.Supplier_history[].class);
                for (int i=0;i<wares.length;i++){
                    supplier_histories.add(wares[i]);
                }
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onSuccess(Response response, DataBean.Supplier_history supplier_history) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }
        });
    }
    private void getReceiverData() {
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getOutWarehouseRecordByReceiverId?userToken="+token+"&receiverId="+receiver.getReceiverId(),new BaseCallback<DataBean.Receiver_history>(){

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
                Gson gson= new Gson();
                DataBean.Receiver_history[] wares=gson.fromJson(resultStr,DataBean.Receiver_history[].class);
                for (int i=0;i<wares.length;i++){
                    receiver_histories.add(wares[i]);
                }
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onSuccess(Response response, DataBean.Receiver_history receiver_history) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }
        });
    }
    public void sendSupplierData(Map<String,String> parms){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.post_for_form("http://121.199.22.134:8003/api-inventory/modifySupplier/?userToken="+token,parms,null,null,new BaseCallback<DataBean.Supplier>(){
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
                Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Response response, DataBean.Supplier supplier) {
                System.out.println("@@@@@3"+response);
                Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);

            }

        });
    }
    public void sendReceiverData(Map<String,String> parms){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.post_for_form("http://121.199.22.134:8003/api-inventory/modifyReceiver?userToken="+token,parms,null,null,new BaseCallback<DataBean.Receiver>(){
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
                Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Response response, DataBean.Receiver receiver) {
                System.out.println("@@@@@3"+response);
                Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }

        });
    }
    @Override
    public void onClick(View v) {
        if(v == btn_commit){
            //返回出入库主列表
            if(opType == 0){
                SharedPreferences preferences = activity.getSharedPreferences("supplier",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("supplierName",supplier.getSupplierName());
                editor.putString("supplierId",supplier.getSupplierId()+"");
                editor.commit();
                WarehouseInList_Fragment.setSupplierName( supplier.getSupplierName());
            }
            else if(opType == 1){
                SharedPreferences preferences = activity.getSharedPreferences("receiver",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("receiverName",receiver.getReceiverName());
                editor.putString("receiverId",receiver.getReceiverId()+"");
                editor.commit();
                WarehouseOutList_Fragment.setReceiverName( receiver.getReceiverName());
            }
            activity.fragment_Manager.pop();
        }
        else if(v == btn_modify){
            tags = "";
            if(opType == 0){
                if(checkbox1.isChecked()){
                    tags += "a";
                }
                if(checkbox2.isChecked()){
                    tags += "b";
                }
                if(checkbox3.isChecked()){
                    tags += "c";
                }
                if(checkbox4.isChecked()){
                    tags += "d";
                }
                Map<String,String> map=new HashMap<>();
                map.put("supplierId",supplier.getSupplierId()+"");
                map.put("supplierName",et_name.getText().toString());
                map.put("supplierAddress", et_address.getText().toString());
                map.put("supplierContact",et_contact.getText().toString());
                map.put("tags",tags);
                map.put("supplierCompany", et_company.getText().toString());
//                DataBean.Supplier post_data=new DataBean.Supplier(supplier.getSupplierId(), et_name.getText().toString(),
//                        et_address.getText().toString(), et_contact.getText().toString(), tags, et_company.getText().toString());
//                sendSupplierData(post_data);
                sendSupplierData(map);
                Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
            }
            else if(opType == 1){
                if(checkbox1.isChecked()){
                    tags += "a";
                }
                if(checkbox2.isChecked()){
                    tags += "b";
                }
                if(checkbox3.isChecked()){
                    tags += "c";
                }
                if(checkbox4.isChecked()){
                    tags += "d";
                }
                Map<String,String> map=new HashMap<>();
                map.put("receiverId",receiver.getReceiverId()+"");
                map.put("receiverName",et_name.getText().toString());
                map.put("receiverAddress", et_address.getText().toString());
                map.put("receiverContact",et_contact.getText().toString());
                map.put("tags",tags);
                map.put("receiverCompany", et_company.getText().toString());
                sendReceiverData(map);
                Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;
        public MyHandler(MainActivity activity){
            mActivity =new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            final MainActivity activity=mActivity.get();
            super.handleMessage(msg);
            if(activity!=null){
                if(opType == 0){
                    if(supplier_histories!=null&&supplier_histories.size()>0){
                        tv_empty.setVisibility(View.GONE);
                        adapter=new History_Adapter(R.layout.ware_in_record_item, supplier_histories, token, opType, fragment);
                        rv_pager.setAdapter(adapter);
                    }
                    else{
                        tv_empty.setVisibility(View.VISIBLE);
                    }
                }
                else if(opType == 1){
                    if(receiver_histories!=null&&receiver_histories.size()>0){
                        tv_empty.setVisibility(View.GONE);
                        adapter=new History_Adapter(R.layout.ware_in_record_item, receiver_histories, opType, token, fragment);
                        rv_pager.setAdapter(adapter);
                    }
                    else{
                        tv_empty.setVisibility(View.VISIBLE);
                    }
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
}
