# WMS
仓库管理系统

打开相册与拍照功能｛
  封装类：Open_Album
  在需要使用到该功能的activity或者fragment中 调用 take_Album_Dialog,呈现dialog
    var dialog=take_Album_Dialog(this）
            dialog.show()
  在activity或者fragment中，调用回调函数，获取相片：
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        
        when (requestCode) {
            TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                val bitmap =
                    BitmapFactory.decodeStream(contentResolver.openInputStream(Open_Album.uri))
                    Glide.with(this).load(bitmap).into(image)
            }
               CHOOSE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 19) Open_Album.handleImageOnKitKat(this,data,image) else Open_Album.handleImageBeforeKitKat(this,data,image)
            }
            else -> {
            }
        }
    }
    
｝
