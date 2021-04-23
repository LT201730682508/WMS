package com.example.WMS.WareOperation.WarehouseOut;
/**
 * 出库商品列表
 * */
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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


import com.example.WMS.MyFragment.Data_report.Ware_out_Record.Ware_out_Record_Model;
import com.example.WMS.MyFragment.Warehouse.Warehouse_authority_Model;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.WareOperation.Categroy.Category_Adapter;
import com.example.WMS.WareOperation.Categroy.SelectItem;
import com.example.WMS.WareOperation.MyAdapter;
import com.example.WMS.WareOperation.Receiver_Supplier.Receiver_Fragment;

import com.example.WMS.domain.DataBean;

import com.google.gson.Gson;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xuexiang.xui.widget.edittext.ClearEditText;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

public class WarehouseOutList_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    private static RecyclerView rv_pager;
    private static RecyclerView category_List;
    private static TextView tv_nomedia;
    private static ProgressBar pb_loading;
    private static Spinner spinner;
    private static Button btn_select;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Base_Topbar base_topbar;
    private Button btn_scan;
    private static ArrayList<DataBean.Category> categories;
    private static Category_Adapter category_adapter;
    private static MyAdapter<MyAdapter.VH> adapter;
    private static ArrayList<DataBean.ProductOut> warehouseItems;
    private static ArrayAdapter<String> spinnerAdapter;
    private static ArrayList<Ware_out_Record_Model.Out_Record> warehouseName;
    private static int pos=0;
    private static String selectWarehouseName;
    private static int wareHouseId;
    private String productCode;
    private static String receiverName="";
    private static String receiverId;
    private static Fragment fragment;
    private static String token;
    //private MyHandler handler=new MyHandler((MainActivity) getActivity());
    private static MyHandler handler;
    private long lastClickTime=0;
    private long now=0;
    private int userId;
    private static Warehouse_authority_Model.authority roleList;
    private ImageView btn_search;
    private ImageView make_sure;
    private ImageView search_detail_back;
    private LinearLayout search_lin;
    private LinearLayout search_detail;
    private ClearEditText search_content;
    private LinearLayout detail_list;
    private ImageView detail_make_sure;
    private ClearEditText detail_name;
    private RadioGroup radio_topbar;
    private RadioGroup radio_num;
    private ImageView clear_select;
    private String[] tags = new String[2];
    private static Spinner category;
    private static String selectCategory = "";
    private static ArrayAdapter<String> category_spinnerAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment=this;
        handler=new MyHandler((MainActivity) getActivity());
        context=getActivity();
        token=((MainActivity)getActivity()).fragment_Manager.userinfo.getToken();
        userId = ((MainActivity)getActivity()).fragment_Manager.userinfo.getUserInfo().getUserId();
        getWarehouseList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }
    
    public View initView(){
        pos = 0;
        View view=View.inflate(context,R.layout.outlist_fragment,null);
        base_topbar=new Base_Topbar(view,(MainActivity) getActivity(),true);
        base_topbar.setTitle("出库");
        rv_pager=view.findViewById(R.id.lv_video_pager);
        rv_pager.setLayoutManager(new LinearLayoutManager(context));
        category_List = view.findViewById(R.id.category_List);
        category_List.setLayoutManager(new LinearLayoutManager(context));
        btn_search = base_topbar.getSearch();
        make_sure = base_topbar.getMake_sure();
        detail_make_sure = base_topbar.getDetail_make_sure();
        search_lin = base_topbar.getSearch_lin();
        search_detail = base_topbar.getSearch_detail();
        search_detail_back = base_topbar.getSearch_detail_back();
        search_content = base_topbar.getSearch_content();
        detail_name = base_topbar.getDetail_name();
        radio_topbar = base_topbar.getRadio_topbar();
        radio_topbar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.checkbox_e:
                        tags[1] = "e";
                        break;
                    case R.id.checkbox_g:
                        tags[1] = "g";
                        break;
                    case R.id.checkbox_f:
                        tags[1] = "f";
                        break;
                    case R.id.checkbox_h:
                        tags[1] = "h";
                        break;
                    default:
                        tags[1] = "";
                        break;
                }
            }
        });
        radio_num = view.findViewById(R.id.radio_num);
        radio_num.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.checkbox_a:
                        tags[0] = "a";
                        break;
                    case R.id.checkbox_b:
                        tags[0] = "b";
                        break;
                    case R.id.checkbox_c:
                        tags[0] = "c";
                        break;
                    case R.id.checkbox_d:
                        tags[0] = "d";
                        break;
                    default:
                        tags[0] = "";
                        break;
                }
            }
        });
        radio_num = view.findViewById(R.id.radio_num);
        clear_select = view.findViewById(R.id.clear_select);
        detail_list = view.findViewById(R.id.detail_list);
        search_content.setHint("简单搜索支持名称或标签快捷搜索~");
        btn_search.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                search_detail.setVisibility(View.VISIBLE);
                Toast.makeText(context,"详细搜索模式", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * 刷新操作在这里实现
                 * */
                //这里获取数据的逻辑
                if(pos == 0){
                    getData(wareHouseId);
                }
                else {
                    getCategoryData(categories.get(pos).getCategoryName(),wareHouseId);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        tv_nomedia=view.findViewById(R.id.tv_nomedia);
        pb_loading=view.findViewById(R.id.pb_loading);
        spinner=view.findViewById(R.id.spinner);
        btn_scan=view.findViewById(R.id.scan);
        btn_select=view.findViewById(R.id.select_receiver);
        //设置适配器
        category=view.findViewById(R.id.spinner_category);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectCategory = categories.get(position).getCategoryName();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //todo-something
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectWarehouseName = warehouseName.get(position).getWarehouseName();
                getRole(token, warehouseName.get(position).getWarehouseId());
                wareHouseId= warehouseName.get(position).getWarehouseId();
                getCategoryList();
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
        btn_search.setOnClickListener(this);
        make_sure.setOnClickListener(this);
        detail_make_sure.setOnClickListener(this);
        search_detail_back.setOnClickListener(this);
        clear_select.setOnClickListener(this);
    }
    private void getWarehouseList() {
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getJoinedWarehouse?userToken="+token,new BaseCallback<Ware_out_Record_Model.Out_Record>(){
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
                warehouseName = new ArrayList<Ware_out_Record_Model.Out_Record>();
                Gson gson= new Gson();
                Ware_out_Record_Model.Out_Record[] wares=gson.fromJson(resultStr,Ware_out_Record_Model.Out_Record[].class);
                warehouseName.addAll(Arrays.asList(wares));
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onSuccess(Response response, Ware_out_Record_Model.Out_Record out_record) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }
        });
    }
    public void getRole(String token, final int warehouseId){
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-authority/getAuthoritiesOfUser?userToken="+token+"&warehouseId="+warehouseId,
                new BaseCallback<Warehouse_authority_Model.authority>(){
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
                        Gson gson= new Gson();
                        Warehouse_authority_Model.authority wares=gson.fromJson(resultStr,Warehouse_authority_Model.authority.class);
                        getData(warehouseId);
                        roleList = wares;
                    }

                    @Override
                    public void onSuccess(Response response, Warehouse_authority_Model.authority productIn) {
                        System.out.println("Success"+response);
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {
                        System.out.println("error"+response+e);
                    }
                });
    }
    private void getCategoryList() {
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getCategoryListByWarehouseId/"+wareHouseId+"?userToken="+token,new BaseCallback<DataBean.Category>(){

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
                categories = new ArrayList<DataBean.Category>();
                categories.add(new DataBean.Category(-1,"全部种类"));
                Gson gson= new Gson();
                DataBean.Category[] wares=gson.fromJson(resultStr,DataBean.Category[].class);
                for (int i=0;i<wares.length;i++){
                    categories.add(wares[i]);
                }
                handler.sendEmptyMessage(3);
            }

            @Override
            public void onSuccess(Response response, DataBean.Category category) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }
        });
    }
    private static void getCategoryData(String categoryName, int wareid) {
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getOutInventoryProductByWarehouseIdAndCategory/"+categoryName+"/"+wareid+"?userToken="+token,
                new BaseCallback<DataBean.ProductIn>(){
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
                        warehouseItems = new ArrayList<DataBean.ProductOut>();
                        Gson gson= new Gson();
                        DataBean.ProductOut[] wares=gson.fromJson(resultStr,DataBean.ProductOut[].class);
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
                });
    }
    private static void getData(int wareHouseId) {
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getOutInventoryProductByWarehouseId/"+wareHouseId+"?userToken="+token,new BaseCallback<DataBean.ProductOut>(){
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
    private void detailSearch(String tags, String productName){
        String urlString = "";
        if(selectCategory != "全部种类"){
            urlString += "&category=" + selectCategory;
        }
        if(productName != ""){
            urlString += "&productName=" + productName;
        }
        if(tags != ""){
            urlString += "&tags=" + tags;
        }
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getOutInventoryProductByConditions/?userToken="
                        +token+"&warehouseId="+wareHouseId+urlString,
                new BaseCallback<DataBean.ProductOut>(){
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
                        warehouseItems = new ArrayList<DataBean.ProductOut>();
                        Gson gson= new Gson();
                        DataBean.ProductOut[] wares=gson.fromJson(resultStr,DataBean.ProductOut[].class);
                        warehouseItems.addAll(Arrays.asList(wares));
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
    private void easySearch(int wareid, String guess) {
        OkHttpHelper ok= OkHttpHelper.getInstance();
        if(guess.contains("优")|guess.contains("良")|guess.contains("合格")|guess.contains("残")|guess.contains("次")){
            String tag = "";
            if(guess.contains("优")){
                tag = "e";
            }
            else if(guess.contains("良")){
                tag = "g";
            }
            else if(guess.contains("合格")){
                tag = "f";
            }
            else if(guess.contains("残")||guess.contains("次")){
                tag = "h";
            }
            ok.get_for_list("http://121.199.22.134:8003/api-inventory/getOutInventoryProductByWarehouseIdAndTags/"+tag+"/"+wareid+"?userToken="+token,
                    new BaseCallback<DataBean.ProductOut>(){
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
                            warehouseItems = new ArrayList<DataBean.ProductOut>();
                            Gson gson= new Gson();
                            DataBean.ProductOut[] wares=gson.fromJson(resultStr,DataBean.ProductOut[].class);
                            warehouseItems.addAll(Arrays.asList(wares));
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
        else{//根据名称进行简单搜索
            ok.get_for_list("http://121.199.22.134:8003/api-inventory/getOutInventoryProductByWarehouseIdAndProductName/"+guess+"/"+wareid+"?userToken="+token,
                    new BaseCallback<DataBean.ProductOut>(){
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
                            warehouseItems = new ArrayList<DataBean.ProductOut>();
                            Gson gson= new Gson();
                            DataBean.ProductOut[] wares=gson.fromJson(resultStr,DataBean.ProductOut[].class);
                            warehouseItems.addAll(Arrays.asList(wares));
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
                switch (msg.what) {
                    case 0:
                        if(warehouseItems!=null&&warehouseItems.size()>0){
                            tv_nomedia.setVisibility(View.GONE);
                            pb_loading.setVisibility(View.GONE);
                            rv_pager.setVisibility(View.VISIBLE);
                            adapter=new MyAdapter<MyAdapter.VH>(R.layout.item_outlist, warehouseItems, 1,activity,fragment,selectWarehouseName,receiverName,token,receiverId, roleList.getAuthorities());
                            rv_pager.setAdapter(adapter);
                        }
                        else{
                            rv_pager.setVisibility(View.GONE);
                            tv_nomedia.setVisibility(View.VISIBLE);
                            pb_loading.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 1:
                        String[] warehouseList=new String[warehouseName.size()];
                        for(int i = 0; i < warehouseName.size(); i++){
                            warehouseList[i] = warehouseName.get(i).getWarehouseName();
                        }
                        if(warehouseList != null && warehouseList.length > 0){
                            spinnerAdapter=new ArrayAdapter<String>(activity, R.layout.myspinner, warehouseList);
                            selectWarehouseName = warehouseList[0];
                            spinner.setAdapter(spinnerAdapter);
                        }
                        break;
                    case 3:
                        category_adapter = new Category_Adapter<Category_Adapter.VH>(R.layout.item_category, categories, activity, token, 0);
                        category_adapter.setOnItemClickListener(new Category_Adapter.OnItemClickListener() {
                            public void onItemClick(View view, int position) {
                                SelectItem.setId(position);//自定义的方法，告诉adpter被点击item
                                pos = position;
                                if(pos == 0){
                                    getData(wareHouseId);
                                }
                                else {
                                    getCategoryData(categories.get(position).getCategoryName(),wareHouseId);
                                }

                                category_adapter.notifyDataSetChanged();
                            }
                        });
                        category_List.setAdapter(category_adapter);
                        String[] list=new String[categories.size()];
                        list[0] = "空";
                        for(int i = 1; i < categories.size(); i++){
                            list[i] = categories.get(i).getCategoryName();
                        }
                        if(list != null && list.length > 0){
                            category_spinnerAdapter=new ArrayAdapter<String>(activity, R.layout.myspinner, list);
                            selectCategory = list[0];
                            category.setAdapter(category_spinnerAdapter);
                        }
                    case 4:
                        SelectItem.setId(0);
                        pos = 0;
                        getData(wareHouseId);
                        category_adapter.notifyDataSetChanged();
                    default:
                        break;
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
            int result = ScanUtil.startScan(getActivity(), REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create());

        }
        else if(v==btn_select){
            now = System.currentTimeMillis();
            if(now - lastClickTime >1000) {
                lastClickTime = now;
                Receiver_Fragment receiver_fragment = new Receiver_Fragment(token, roleList.getAuthorities());
                ((MainActivity) getActivity()).fragment_Manager.hide_all(receiver_fragment);
            }
        }
        else if(v == btn_search){
            search_lin.setVisibility(View.VISIBLE);
            Toast.makeText(context,"当前是简单搜索模式，多关键词搜索请返回并长按搜索", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessage(4);
        }
        else if(v == make_sure){
            String guess = search_content.getText().toString();
            easySearch(wareHouseId, guess);
        }
        else if(v == search_detail_back){
            detail_name.setText(null);
            radio_num.clearCheck();
            radio_topbar.clearCheck();
            search_detail.setVisibility(View.GONE);
        }
        else if(v == detail_make_sure){
            String name = detail_name.getText().toString();
            String str = tags[0] + tags[1];
            detailSearch(str, name);
        }
        else if(v == clear_select){
            detail_name.setText(null);
            radio_num.clearCheck();
            radio_topbar.clearCheck();
        }
    }
    private static final int CAMERA_REQ_CODE = 3;
    private static final int RESULT_OK = 4;
    private static final int REQUEST_CODE_SCAN = 5;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || grantResults == null || grantResults.length < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (requestCode == CAMERA_REQ_CODE) {
            int result = ScanUtil.startScan(getActivity(), REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //当扫码页面结束后，处理扫码结果
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK || data == null) {
            return;
        }
        //从onActivityResult返回data中，用 ScanUtil.RESULT作为key值取到HmsScan返回值
        if (requestCode == REQUEST_CODE_SCAN) {
            Object obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj instanceof HmsScan) {
                if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
                    Toast.makeText(context, ((HmsScan) obj).getOriginalValue(),      Toast.LENGTH_SHORT).show();
                    productCode = ((HmsScan) obj).getOriginalValue();
                    WarehouseOut_scan_detail_Fragment warehouseOut_scan_detail_fragment = new WarehouseOut_scan_detail_Fragment(productCode, token, selectWarehouseName, wareHouseId);
                    ((MainActivity) getActivity()).fragment_Manager.hide_all(warehouseOut_scan_detail_fragment);
                }
                return;
            }
        }
    }
}