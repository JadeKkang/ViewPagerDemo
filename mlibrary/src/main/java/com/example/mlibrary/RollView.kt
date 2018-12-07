package com.example.mlibrary

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*

/**
 * 时间：2018/12/5 14:24
 * 姓名：韩晓康
 * 功能：
 */
class RollView : ViewPager {
    var mContext: Context
    /*图片地址集合*/
    var imgList: List<String>? = null
    /*小圆点集合*/
    var imgDoc: List<ImageView>? = null
    /*小圆点Drawable*/
    var imgDocDrawable: List<Drawable>? = null
    /*点击回调*/
    var click: RollViewPager.ItemClick? = null;
    /*判断手势状态 是抬起  还是按下*/
    var isAutoPlay = false
    /*是否自动播放*/
    var isStart = true
    /*设置播放间隔*/
    var time=3000

    constructor(mContext: Context) : super(mContext) {
        this.mContext = mContext
    }

    constructor(mContext: Context, attributeSet: AttributeSet) : super(mContext, attributeSet) {
        this.mContext = mContext
        onPageChange()
    }

    /**
     * 设置资源图片
     */
    fun setImg(imgList: List<String>) {
        this.imgList = imgList;
        this.adapter = GuideAdapter();
    }
    /**
     * 设置小圆点
     */
    fun setDoc(imgDoc: List<ImageView>,imgDocDrawable: List<Drawable>) {
        this.imgDoc = imgDoc;
        this.imgDocDrawable=imgDocDrawable
    }
    /**
     * 设置点击回调监听
     */
    fun setItemClick(click: RollViewPager.ItemClick) {
        this.click = click;
    }

    /**
     * 设置是否自动播放
     */
    fun setIsPlay(isStart: Boolean) {
        this.isStart = isStart;
        if (isStart){
            startPlay()
        }
    }
    /**
     * 设置间隔时间
     */
    fun setGapTime(time: Int) {
        this.time = time;
    }
    /**
     * 处理自动播放
     */
    val handler2 = object : Handler() {
        override fun handleMessage(msg: android.os.Message) {
            if (isStart) {
                var item = (currentItem + 1) % imgList!!.size
                setCurrentItem(item)
                this.removeMessages(1)
                this.sendEmptyMessageDelayed(1, time.toLong())
            }
        }
    }

    /**
     * 开始自动播放
     */
     fun startPlay() {
        if (isStart) {
            var timer = Timer();
            var task = object : TimerTask() {
                override fun run() {
                    handler2.obtainMessage().sendToTarget()
                }
            };
            timer.schedule(task, time.toLong());
        }
    }

    /**
     * 开始自动播放
     */
    fun stopPlay() {
        handler2.removeMessages(1)
    }

    /**
     * 手指按下不自动播放
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (isStart) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    handler2.removeMessages(1)
                }
                MotionEvent.ACTION_UP -> {
                    handler2.sendEmptyMessageDelayed(1, time.toLong())
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    /**
     * 实现手势循环滑动
     */
    private fun onPageChange() {
        this.setOnPageChangeListener(object : OnPageChangeListener {
            /**
             * 0：什么都没做
             *1：开始滑动
             *2：滑动结束
             * 当用手指滑动翻页时，手指按下去的时候会触发这个方法，state值为1，
             * 手指抬起时，如果发生了滑动（即使很小），这个值会变为2，然后最后变为0 。
             * 总共执行这个方法三次。一种特殊情况是手指按下去以后一点滑动也没有发生，
             * 这个时候只会调用这个方法两次，state值分别是1,0 。
             * 当setCurrentItem翻页时，会执行这个方法两次，state值分别为2 , 0 。
             */
            override fun onPageScrollStateChanged(p0: Int) {
                if (isStart) {
                    handler2.removeMessages(1)
                    if (p0 == ViewPager.SCROLL_STATE_IDLE) {
                        handler2.sendEmptyMessageDelayed(1, time.toLong())
                    }
                }
                when (p0) {
                    1 -> {
                        isAutoPlay = false
                    }
                    2 -> {
                        isAutoPlay = true
                    }
                    0 -> {
                        if (currentItem == adapter!!.count - 1 && !isAutoPlay) {
                            setCurrentItem(0, false)
                        } else if (currentItem == 0 && !isAutoPlay) {
                            setCurrentItem(adapter!!.count - 1, false)
                        }
                    }
                }
            }

            /**
             * 这个方法会在屏幕滚动过程中不断被调用。
             */
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            /*这个方法有一个参数position，代表哪个页面被选中。当用手指滑动翻页的时候，如果翻动成功了
                   （滑动的距离够长），
                   手指抬起来就会立即执行这个方法，position就是当前滑动到的页面。
                   如果直接setCurrentItem翻页
                   ，那position就和setCurrentItem的参数一致，
                   这种情况在onPageScrolled执行方法前就会立即执行。*/
            override fun onPageSelected(p0: Int) {
                if (imgDoc!=null){
                    var count = imgDoc!!.size
                    for (i in 0..count - 1) {
                        if (p0 == i) {
                            imgDoc!!.get(i).setImageDrawable(imgDocDrawable!!.get(0))
                        } else {
                            imgDoc!!.get(i).setImageDrawable(imgDocDrawable!!.get(1))
                        }
                    }
                }
            }
        })
    }

    inner class GuideAdapter : PagerAdapter() {

        override fun isViewFromObject(p0: View, p1: Any): Boolean {
            return p0 == p1
        }

        override fun getCount(): Int {
            return imgList!!.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            var imageView = ImageView(context);
            imageView.scaleType=ImageView.ScaleType.FIT_XY
            Glide.with(context).load(imgList!![position]).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView)
            imageView.setOnClickListener {
                if (click != null) {
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

}