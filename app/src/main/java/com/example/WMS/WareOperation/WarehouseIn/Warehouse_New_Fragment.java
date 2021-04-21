package com.example.WMS.WareOperation.WarehouseIn;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.WMS.BaseCallback;
import com.example.WMS.Base_Topbar;
import com.example.WMS.MainActivity;
import com.example.WMS.MyFragment.Data_report.Ware_in_Record.Ware_In_Record_Model;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.Open_Album;
import com.example.WMS.R;

import com.example.WMS.custom_Dialog.take_Album_Dialog;
import com.example.WMS.domain.DataBean;
import com.google.gson.Gson;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.toast.XToast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 点击新增商品打开的输入信息页
 * */
public class Warehouse_New_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    private Button btn_commit;
    private ClearEditText name;
    private ClearEditText detail;
    private static Spinner category;
    private Base_Topbar base_topbar;
    private ImageView picture;
    private ClearEditText warehouse_name;
    private ClearEditText scan_code;
    private Button btn_scan;
    private RadioGroup radioGroup;
    private static String selectCategory = "";
    private static ArrayList<String> categoryList;
    private static ArrayAdapter<String> spinnerAdapter;
    private String warehouseName;
    private int warehouseId;
    private Dialog dialog;
    private String token;
    private Boolean hasImg=false;
    private String tags;
    private MyHandler handler;
    public Warehouse_New_Fragment(String warehouseName,int warehouseId,String token){
        this.warehouseName=warehouseName;
        this.warehouseId=warehouseId;
        this.token=token;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new MyHandler((MainActivity) getActivity());
        context=getActivity();
        dialog= new take_Album_Dialog(context);
        getCategoryList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    private View initView() {
        View view=View.inflate(context, R.layout.fragment_warehouse_in_new,null);
        base_topbar=new Base_Topbar(view,(MainActivity)getActivity(),false);
        base_topbar.setTitle("入库");
        btn_commit=view.findViewById(R.id.commit);
        btn_commit.setOnClickListener(this);
        warehouse_name=view.findViewById(R.id.et_warehousename);
        warehouse_name.setText(warehouseName);
        picture=view.findViewById(R.id.iv_picture);
        picture.setOnClickListener(this);
        name=view.findViewById(R.id.et_name);
        detail=view.findViewById(R.id.et_detail);
        category=view.findViewById(R.id.spinner_category);
        radioGroup = view.findViewById(R.id.radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.checkbox_1:
                        tags = "e";
                        break;
                    case R.id.checkbox_2:
                        tags = "g";
                        break;
                    case R.id.checkbox_3:
                        tags = "f";
                        break;
                    case R.id.checkbox_4:
                        tags = "h";
                        break;
                    default:
                        tags = "f";
                        break;
                }
            }
        });
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectCategory=categoryList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //todo-something
            }
        });
        btn_scan=view.findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(this);
        scan_code=view.findViewById(R.id.et_code);
        scan_code.setFocusable(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v==btn_commit){
            //更新数据
            //需要更新数据库信息代码
            // /api-inventory/addProduct
            if(detail.getText().toString().equals("")||name.getText().toString().equals("")
                    ||selectCategory.equals("")||!hasImg){
                XToast.warning(requireContext(), "请输入完整信息").show();
            }
            else{
                /**
                 * (var warehouseId:Int,var productName:String,var productDescription:String,
                 *                          var productCategory:String,var productCode:String,var productImg:String)
                 * */
                Map<String,String> map=new  HashMap<>();
                map.put("productName",name.getText().toString());
                map.put("productDescription",detail.getText().toString());
                map.put("productCategory", selectCategory);
                map.put("warehouseId",warehouseId+"");
                map.put("productCode",scan_code.getText().toString());
                map.put("productTags", tags);
                sendData(map,saveBitmapFile(((BitmapDrawable)picture.getDrawable()).getBitmap(),"productImg"),"productImg");
            }

        }
        else if (v==picture){
            dialog.show();
        }
        else if (v==btn_scan){
            int result = ScanUtil.startScan(getActivity(), REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create());
        }
    }

    public File saveBitmapFile(Bitmap bitmap,String img){
        File file = new File(requireContext().getFilesDir().getPath().toString()+img+".jpg");//将要保存图片的路径
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bos);
            bos.flush();
            bos.close();
        } catch ( Exception e) {

        }
        return file;
    }
    private void getCategoryList() {
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
                categoryList = new ArrayList<String>();
                Gson gson= new Gson();
                DataBean.Category[] wares=gson.fromJson(resultStr,DataBean.Category[].class);
                for (int i=0;i<wares.length;i++){
                    categoryList.add(wares[i].getCategoryName());
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
    public void sendData(Map<String,String> parms, File file,String img){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.post_for_form("http://121.199.22.134:8003/api-inventory/addProduct"+"?userToken="+token,parms,file,img,new BaseCallback<String>(){
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
            public void onSuccess(Response response, String str) {
                System.out.println("@@@@@3"+str);
                if(str.equals("创建商品成功")){
                    XToast.success(requireActivity(),str).show();
                    ((MainActivity)getActivity()).fragment_Manager.pop();
                }else {
                    XToast.warning(requireActivity(),str).show();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }
        });
    }
    public   void setImage(Activity activity,String str,ImageView imageView){
        Glide.with(activity).load(str).into(imageView);
    }
    private static final int CAMERA_REQ_CODE = 3;
    private static final int RESULT_OK = 4;
    private static final int REQUEST_CODE_SCAN = 5;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        hasImg=true;
        switch (requestCode){
            case Open_Album.TAKE_PHOTO:{
                if (resultCode == Activity.RESULT_OK){
                    try {
                        setImage(getActivity(),Open_Album.uri.toString(),picture);
                    }catch (Exception e){

                    }
                    break;
                }
            }
            case  Open_Album.CHOOSE_PHOTO:{
                if(resultCode == Activity.RESULT_OK){
                    if (Build.VERSION.SDK_INT >= 19) Open_Album.handleImageOnKitKat(getActivity(),data,picture);
                    else Open_Album.handleImageBeforeKitKat(getActivity(),data,picture);

                }else {}
                break;
            }
            case RESULT_OK:
                break;
            case REQUEST_CODE_SCAN:{
                Object obj = data.getParcelableExtra(ScanUtil.RESULT);
                if (obj instanceof HmsScan) {
                    if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
                        Toast.makeText(context, ((HmsScan) obj).getOriginalValue(), Toast.LENGTH_SHORT).show();
                        scan_code.setText(((HmsScan) obj).getOriginalValue());
                    }
                    return;
                }
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(isHidden()){
        }else {
            onResume();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || grantResults == null) {
            return;
        }
        if (grantResults.length < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (requestCode == CAMERA_REQ_CODE) {
            int result = ScanUtil.startScan(getActivity(), REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE).create());
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
            if(activity!=null) {
                String[] warehouseList=new String[categoryList.size()];
                for(int i = 0; i < categoryList.size(); i++){
                    warehouseList[i] = categoryList.get(i);
                }
                if(warehouseList != null && warehouseList.length > 0){
                    spinnerAdapter=new ArrayAdapter<String>(activity, R.layout.myspinner, warehouseList);
                    selectCategory = warehouseList[0];
                    category.setAdapter(spinnerAdapter);
                }
            }
        }
    }
}
