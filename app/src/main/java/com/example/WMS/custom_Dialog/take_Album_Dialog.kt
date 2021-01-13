package com.example.WMS.custom_Dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import com.example.WMS.Open_Album
import com.example.WMS.R
import kotlinx.android.synthetic.main.take_albun_dialog.*


class take_Album_Dialog( context: Context) : Dialog(context,R.style.CustomDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view= View.inflate(context,R.layout.take_albun_dialog,null)

        setContentView(view)
        initView()
    }
    fun initView(){
        take.setOnClickListener {
            Open_Album.takePhoto(scanForActivity(context))
            dismiss()
        }
        open.setOnClickListener {
            Open_Album.openAlbum(scanForActivity(context))
            dismiss()
        }
        cancel.setOnClickListener { dismiss() }
    }
    private fun scanForActivity(cont: Context?): Activity? {
        if (cont == null) return null else if (cont is Activity) return cont else if (cont is ContextWrapper) return scanForActivity(
            cont.baseContext
        )
        return null
    }
}