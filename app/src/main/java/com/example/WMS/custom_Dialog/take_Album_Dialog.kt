package com.example.WMS.custom_Dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View

import com.example.WMS.Open_Album
import com.example.WMS.R
import kotlinx.android.synthetic.main.take_albun_dialog.*

class take_Album_Dialog( context: Context) : Dialog(context,R.style.CustomDialog) {

    private lateinit var activity:Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view= View.inflate(context,R.layout.take_albun_dialog,null)
        setContentView(view)
        initView()
    }
    fun initView(){
        take.setOnClickListener {
            Open_Album.takePhoto(activity)
            dismiss()
        }
        open.setOnClickListener {
            Open_Album.openAlbum(activity)
            dismiss()
        }
        cancel.setOnClickListener { dismiss() }
    }
    fun getAct(activity: Activity){
        this.activity=activity
     }
}