package com.example.viewpagerdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.mlibrary.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity(), RollViewPager.ItemClick, RollView3.OnPagerClickCallback {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        var imgList = arrayListOf<String>()
        imgList.add("http://pic37.nipic.com/20140113/8800276_184927469000_2.png")
        imgList.add("http://pic31.nipic.com/20130705/9527735_231540074000_2.jpg")
        imgList.add("http://pic38.nipic.com/20140301/6608733_073013180000_2.jpg")
        roll.setImg(imgList)
        roll.setItemClick(this)
        roll3.setImg(imgList)
        roll3.setPageTransformer(true, DepthPageTransformer())
        roll4.setImg(imgList)
        roll4.setItemClick(this)
    }
    override fun itemClick(i: Int) {
       Toast.makeText(this,"点击了第"+i+"张",Toast.LENGTH_SHORT).show()
    }
    override fun onPagerClick(position: Int) {
        Toast.makeText(this,"点击了第"+position+"张2",Toast.LENGTH_SHORT).show()
    }
}
