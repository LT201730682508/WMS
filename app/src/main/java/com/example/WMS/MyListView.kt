package com.example.WMS

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView

class MyListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ListView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = MeasureSpec.makeMeasureSpec(
            Int.MAX_VALUE shr 2,  //右移运算符，相当于除于4
            MeasureSpec.AT_MOST
        ) //测量模式取最大值

        super.onMeasure(widthMeasureSpec, heightMeasureSpec) //重新测量高度

    }
}