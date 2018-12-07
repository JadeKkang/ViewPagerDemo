package com.example.viewpagerdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.mlibrary.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity(), RollViewPager.ItemClick {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        var imgList = arrayListOf<String>()
        imgList.add("https://graph.baidu.com/resource/15ad9b7aeed3c58062ea701544089593.jpg")
        imgList.add("https://graph.baidu.com/resource/16f27d8334c272eb7e53c01544089606.jpg")
        imgList.add("https://graph.baidu.com/resource/1b531df4368d9fe0029f001544089612.jpg")
        roll.setImg(imgList)
        roll.setItemClick(this)
        roll2.setPageTransformer(true, DepthPageTransformer2())
        roll2.setImg(imgList)
        roll3.setImg(imgList)
        roll3.setPageTransformer(true, DepthPageTransformer())
    }
    override fun itemClick(i: Int) {
       Toast.makeText(this,"点击了第"+i+"张",Toast.LENGTH_SHORT).show()
    }
}
