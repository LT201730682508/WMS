package com.example.WMS.MyFragment

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.WMS.*
import com.example.WMS.custom_Dialog.take_Album_Dialog
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.login.*
import java.io.IOException
import java.lang.Exception

class Login_fragment: Fragment() {

    lateinit var image:ImageView
    lateinit var signIn_signUpButton: Button
    lateinit var signIn_signInButton:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.login,container,false)
        image=view.findViewById(R.id.appIcon)
        image.setOnClickListener {
            var dialog=take_Album_Dialog(requireContext())
            dialog.show()
        }
        signIn_signInButton=view.findViewById(R.id.signIn_signInButton)
        signIn_signUpButton=view.findViewById(R.id.signIn_signUpButton)
        signIn_signUpButton.setOnClickListener {
            var  registerFragment= Register_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(registerFragment)
        }
        signIn_signInButton.setOnClickListener {
            var homeFragment= Home_Fragment()
            (activity as MainActivity).fragment_Manager.replace_all(homeFragment)
        }

        return view
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        println("拍照成功")
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Open_Album.TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                val bitmap =
                    BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(Open_Album.uri))
                Glide.with(this).load(bitmap).into(image)
                println("拍照成功")
            }
            Open_Album.CHOOSE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 19) Open_Album.handleImageOnKitKat(activity,data,image) else Open_Album.handleImageBeforeKitKat(activity,data,image)
            }
            else -> {
            }
        }
    }
}
