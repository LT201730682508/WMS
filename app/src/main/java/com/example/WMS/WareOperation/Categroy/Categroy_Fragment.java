package com.example.WMS.WareOperation.Categroy;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.WMS.WareOperation.Receiver_Supplier.RS_Add_Dialog;
import com.example.WMS.domain.DataBean;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class Categroy_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    private static ArrayList<DataBean.Category> categories;
    private static RecyclerView rv_pager;
    private static ImageView im_empty;
    private static TextView tv_empty;
    private Button btn_add;
    private MyHandler handler;
    private Base_Topbar base_topbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static Category_Adapter<Category_Adapter.VH> adapter;
    private static String token;
    private int warehouseId;
    public Categroy_Fragment(String token, int warehouseId) {
        this.token=token;
        this.warehouseId = warehouseId;
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
        View view=View.inflate(context,R.layout.fragment_category,null);
        base_topbar=new Base_Topbar(view,(MainActivity)getActivity(),true);
        rv_pager=view.findViewById(R.id.rv_pager);
        rv_pager.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_pager.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        im_empty=view.findViewById(R.id.im_empty);
        tv_empty=view.findViewById(R.id.tv_nomedia);
        btn_add=view.findViewById(R.id.btn_add);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_add.setOnClickListener(this);
        initData();
    }

    private void initData() {
        categories=new ArrayList<DataBean.Category>();
        getData();
    }

    private void getData() {
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getCategoryListByWarehouseId/"+warehouseId+"?userToken="+token,new BaseCallback<DataBean.Category>(){

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
                DataBean.Category[] wares=gson.fromJson(resultStr,DataBean.Category[].class);
                for (int i=0;i<wares.length;i++){
                    categories.add(wares[i]);
                }
                handler.sendEmptyMessage(0);
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

    @Override
    public void onClick(View v) {
        if(v==btn_add){
            Category_Add_Dialog category_add_dialog=new Category_Add_Dialog(context, token, warehouseId);
            category_add_dialog.show();
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
                if(categories!=null&&categories.size()>0){
                    im_empty.setVisibility(View.GONE);
                    tv_empty.setVisibility(View.GONE);
                    adapter=new Category_Adapter<Category_Adapter.VH>(R.layout.item_category, categories, activity, token);
                    rv_pager.setAdapter(adapter);
                }
                else{
                    im_empty.setVisibility(View.VISIBLE);
                    tv_empty.setVisibility(View.VISIBLE);
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
