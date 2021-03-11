package com.example.WMS

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException


class MainActivity : AppCompatActivity() {

    lateinit var fragment_Manager:Fragment_Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment_Manager= Fragment_Manager(this)

    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        println("已执行"+fragment_Manager.ger_Top_Fragment())
        fragment_Manager.ger_Top_Fragment().onActivityResult(requestCode,resultCode,data)

    }

}
