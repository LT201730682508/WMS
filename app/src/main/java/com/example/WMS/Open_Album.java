package com.example.WMS;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Open_Album {
    private static String imagePath;
    public static Uri uri;
    public static final int CHOOSE_PHOTO=2;
    public  static  final  int TAKE_PHOTO=1;




     public static void takePhoto(Activity activity){
        File  file =createImageFile();
        uri=getSaveUri70(activity,file);
        openCamera70(activity,uri,1);
     }
    /**
     * 创建一个保存图片的文件
     * @return
     */
    public static File createImageFile() {
        //获取保存到的文件夹路劲
        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM");
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, getPhotoPath());//
        return file;
    }
    /**
     * 保存文件的名字
     * @return
     */
    public static String getPhotoPath(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd_HHmmss");//格式大小写有区别
        String sysDatetime = fmt.format(Calendar.getInstance().getTime());//2016年02月25日  13:23:40
        String imageFileName = "JPEG_" + sysDatetime + ".jpg";
        return imageFileName;
    }

    public static  void openCamera70(Activity activity,Uri uri, int flag) {
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent, flag);
    }
    public static Uri getSaveUri70(Activity activity, File file){
        //兼容android7.0 使用共享文件的形式
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
        Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        return uri;
    }

    public static void openAlbum(Activity activity)
    {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        activity.startActivityForResult(intent,CHOOSE_PHOTO);
    }
 /*   public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)
    {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(activity, "you denied the permission", Toast.LENGTH_SHORT);
                }
                break;
            default:
        }
    }
*/
    @TargetApi(19)
    public static void handleImageOnKitKat(Activity activity,Intent data,ImageView imageView)
    {
        imagePath=data.getData().toString();
        displayImage(activity,imagePath,imageView);
    }
    public static void handleImageBeforeKitKat(Activity activity,Intent data,ImageView imageView)
    {
        Uri uri=data.getData();
        String imagePath=getImagePath(activity,uri,null);
        displayImage(activity,imagePath,imageView);
    }
    private static String getImagePath(Activity activity,Uri uri,String selection)
    {
        String path=null;
        Cursor cursor=activity.getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }

        return path;
    }
    private static void  displayImage(Activity activity,String imagepath,ImageView imageView)
    {

        if(imagepath!=null)
        {
            if (Build.VERSION.SDK_INT >= 23) {
                int REQUEST_CODE_CONTACT = 101;
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //验证是否许可权限
                for (String str : permissions) {
                    if (activity.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限
                        activity.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                        return;
                    }
                }
            }
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath,op);
            setImge(activity,imagepath,imageView);
            Toast.makeText(activity, imagepath, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(activity,"fail to get image",Toast.LENGTH_SHORT).show();
        }
    }

    public static void setImge(Activity activity,String str,ImageView imageView){
        Glide.with(activity).load(str).into(imageView);
    }

    /**
     * 解决Android 6.0 或以上版本不能读取外部存储权限的问题，哪里需要读写SD卡的权限，就调用这个方法，必须在一个Activity里
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
            return false;
        }
        return true;

    }
}
