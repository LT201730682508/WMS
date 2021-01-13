package com.example.WMS


import android.R.attr.fragment
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.WMS.MyFragment.Login_fragment
import com.example.WMS.MyFragment.Register_Fragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
     lateinit var loginFragment:Login_fragment
    lateinit var registerFragment: Register_Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginFragment= Login_fragment()
        registerFragment=Register_Fragment()
        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.add(R.id.Fragment_First, loginFragment)
        transaction.commit()
    }
    fun replace(){
        supportFragmentManager.beginTransaction().hide(loginFragment).add(R.id.Fragment_First,registerFragment).addToBackStack(null).commit()
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        loginFragment.onActivityResult(requestCode,resultCode,data)
    }

}
