package com.example.WMS.MyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.WMS.MainActivity
import com.example.WMS.R

class Register_Fragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.register,container,false)
        var signUn_goBackButton=view.findViewById<Button>(R.id.signUn_goBackButton)
        signUn_goBackButton.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
        return view
    }
}