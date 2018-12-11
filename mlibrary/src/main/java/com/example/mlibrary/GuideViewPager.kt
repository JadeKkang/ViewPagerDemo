package com.example.mlibrary

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

/**
 * 时间：2018/12/5 13:12
 * 姓名：韩晓康
 * 功能：引导页ViewPager
 */
class GuideViewPager : ViewPager {
    var imgList: List<Int>? = null
    var mContext: Context
    var click: ItemClick?= null;

    constructor(mContext: Context) : super(mContext) {
        this.mContext = mContext
    }

    constructor(mContext: Context, attributeSet: AttributeSet) : super(mContext, attributeSet) {
        this.mContext = mContext

    }

    /**
     * 设置资源图片
     */
    fun setImg(imgList: List<Int>) {
        this.imgList = imgList;
        this.adapter = GuideAdapter(mContext, imgList);
    }

    /**
     * 设置点击回调监听
     */
    fun setItemClick(click: ItemClick) {
        this.click = click;
    }

    inner  class GuideAdapter : PagerAdapter {
        var imgList: List<Int>? = null
        var context: Context? = null


        constructor(context: Context, imgList: List<Int>) {
            this.imgList = imgList
            this.context = context
        }

        override fun isViewFromObject(p0: View, p1: Any): Boolean {
            return p0 == p1
        }

        override fun getCount(): Int {
            if(imgList!=null) {
                return imgList!!.size
            }
            return 0
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            if(imgList!=null) {
                imageView.setImageResource(imgList!![position])
            }
            imageView.setOnClickListener {
                if (click!=null){
                    click!!.itemClick(position)
                }
            }
            (container as ViewPager).addView(imageView)
            return imageView
        }

        override fun destroyItem(container: View, position: Int, `object`: Any) {
            (container as ViewPager).removeView(`object` as View)
        }


    }

    /**
     * 会带接口
     */
    interface ItemClick {
        fun itemClick(i: Int)
    }
}