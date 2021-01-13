package com.example.WMS

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.WMS.Open_Album.CHOOSE_PHOTO
import com.example.WMS.Open_Album.TAKE_PHOTO
import com.example.WMS.custom_Dialog.take_Album_Dialog

class MainActivity : AppCompatActivity() {
    lateinit var image:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image=findViewById<ImageView>(R.id.appIcon)

        image.setOnClickListener {
            var dialog=take_Album_Dialog(this)
             dialog.getAct(this )
            dialog.show()
        }
    }
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
}
