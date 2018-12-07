package com.example.viewpagerdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.mlibrary.DepthPageTransformer
import com.example.mlibrary.DepthPageTransformer2
import com.example.mlibrary.GuideViewPager
import com.example.viewpagerdemo.R.id.guide_pager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GuideViewPager.ItemClick {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        var imgList = arrayListOf<Int>()
        imgList.add(R.mipmap.guide)
        imgList.add(R.mipmap.guide2)
        imgList.add(R.mipmap.guide3)
        guide_pager.setImg(imgList)
        guide_pager.setItemClick(this)
    }
    override fun itemClick(i: Int) {
        if (i==2){
            startActivity(Intent(this,SecondActivity::class.java))
        }
    }
}
