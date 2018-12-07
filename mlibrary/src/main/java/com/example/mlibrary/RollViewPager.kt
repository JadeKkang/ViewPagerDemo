package com.example.mlibrary

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
 * 时间：2018/12/6 17:01
 * 姓名：韩晓康
 * 功能：
 */
class RollViewPager : RelativeLayout {
    var mContext: Context
    /*设置图片地址集合*/
    var imgList: List<String>? = null
    /*viewpager*/
    var roll_pager: RollView? = null
    /*装小圆点的LinearLayout*/
    var ll_doc: LinearLayout? = null
    /*装小圆点的ImageView*/
    var imgDoc = arrayListOf<ImageView>()
    /*小圆点的Drawable*/
    var imgDocDrawable = arrayListOf<Drawable>()
    /*小圆点选中颜色*/
    var colorDotTrue = 0
    /*小圆点没有选中颜色*/
    var colorDotFalse = 0
    /*装小圆点容器的背景色*/
    var llColorDoc = 0
    /*小圆点的大小*/
    var colorDotSize = 20
    /*是否自动播放*/
    var isStart = true
    /*小圆点是否显示*/
    var isDoc = true
    /*小圆点显示位置 左  中  右*/
    var gravityDoc = 0
    /*小圆点显示左边  距离左边距离*/
    var leftMargin = 0
    /*小圆点显示右边  距离右边距离*/
    var rightMargin = 0
    /*小圆点显示  距离底部距离*/
    var bottomMargin = 20
    /*几个小圆点之间的左间距*/
    var leftPadding = 10
    /*几个小圆点之间的右间距*/
    var rightPadding = 10
    /*设置播放间隔*/
    var time=3000
    /*点击回调*/
    var click: ItemClick? = null;

    constructor(mContext: Context) : super(mContext, null) {
        this.mContext = mContext
    }

    constructor(mContext: Context, attributeSet: AttributeSet) : this(mContext, attributeSet, -1) {
        this.mContext = mContext

    }

    constructor(mContext: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(mContext, attributeSet) {
        this.mContext = mContext
        var view = LayoutInflater.from(context).inflate(R.layout.roll_rl, this, true)
        var typedArray = mContext.obtainStyledAttributes(attributeSet, R.styleable.RollViewPager)
        if (typedArray != null) {
            colorDotTrue = typedArray!!.getColor(R.styleable.RollViewPager_color_doc_true, ContextCompat.getColor(context, R.color.colorDotTrue))
            colorDotFalse = typedArray!!.getColor(R.styleable.RollViewPager_color_doc_false, ContextCompat.getColor(context, R.color.colorDotFalse))
            llColorDoc = typedArray!!.getColor(R.styleable.RollViewPager_ll_color_Doc, ContextCompat.getColor(context, R.color.llColorDoc))
            isStart = typedArray!!.getBoolean(R.styleable.RollViewPager_isStart, true)
            isDoc = typedArray!!.getBoolean(R.styleable.RollViewPager_isDoc, true)
            gravityDoc = typedArray!!.getInteger(R.styleable.RollViewPager_gravity_doc, 1)
            time = typedArray!!.getInteger(R.styleable.RollViewPager_time, 3000)

            var colorDot = typedArray!!.getDimension(R.styleable.RollViewPager_color_size, 20f)
            var left = typedArray!!.getDimension(R.styleable.RollViewPager_doc_leftMargin, 0f)
            var right = typedArray!!.getDimension(R.styleable.RollViewPager_doc_rightMargin, 0f)
            var bottom = typedArray!!.getDimension(R.styleable.RollViewPager_doc_bottomMargin, 20f)
            var leftP = typedArray!!.getDimension(R.styleable.RollViewPager_doc_leftPadding, 10f)
            var rightp = typedArray!!.getDimension(R.styleable.RollViewPager_doc_leftPadding, 10f)
            colorDotSize = pxtodp(colorDot)
            leftMargin = pxtodp(left)
            rightMargin = pxtodp(right)
            bottomMargin = pxtodp(bottom)
            leftPadding = pxtodp(leftP)
            rightPadding = pxtodp(rightp)

        }
        typedArray.recycle()

        roll_pager = view.findViewById<RollView>(R.id.roll_pager);
        ll_doc = view.findViewById<LinearLayout>(R.id.ll_doc);
    }

    /**
     * 设置图片地址
     */
    fun setImg(imgList: List<String>) {
        this.imgList = imgList
        roll_pager!!.setImg(imgList)
        init(imgList)//初始化控件属性
    }

    /**
     * 停止播放
     */
    fun stopPlay() {
        roll_pager!!.stopPlay()
        roll_pager!!.setIsPlay(false)
    }

    fun setPageTransformer(reverseDrawingOrder: Boolean, transformer: ViewPager.PageTransformer?) {
        roll_pager!!.setPageTransformer(reverseDrawingOrder,transformer)
    }
    /**
     * 点击回调
     */
    fun setItemClick(click: ItemClick) {
        this.click = click;
        roll_pager!!.setItemClick(click)
    }
    /**
     * 产生shape类型的drawable
     */
    fun getDrawable(solidColor: Int, width: Int, height: Int): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.OVAL
        drawable.setColor(solidColor)
        drawable.setSize(width, height)
        return drawable
    }

    /**
     * px转dp
     */
    private fun pxtodp(dpValue: Float): Int {
        var density = mContext.resources.displayMetrics.density
        val d = dpValue / density + 0.5f
        return d.toInt()
    }

    /**
     * 会带接口
     */
    interface ItemClick {
        fun itemClick(i: Int)
    }
    /**
     * 初始化控件属性
     */
    private fun init(imgList: List<String>) {
        when(gravityDoc){
            0-> ll_doc!!.gravity = Gravity.LEFT
            1-> ll_doc!!.gravity = Gravity.CENTER
            2-> ll_doc!!.gravity = Gravity.RIGHT
        }
        ll_doc!!.setBackgroundColor(llColorDoc)
        roll_pager!!.setIsPlay(isStart)
        roll_pager!!.setGapTime(time)
        if (isDoc) {//是否显示小圆点
            ll_doc!!.removeAllViews()
            for (i in 0..(imgList.size - 1)) {
                var docView = ImageView(mContext)
                var layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                if (gravityDoc == 0) {
                    if (i == 0) {
                        layoutParams.leftMargin = leftMargin
                    }
                }
                if (gravityDoc == 2) {
                    if (i == (imgList.size - 1)) {
                        layoutParams.rightMargin = rightMargin
                    }
                }
                layoutParams.bottomMargin = bottomMargin
                layoutParams.topMargin = bottomMargin
                docView.layoutParams = layoutParams
                docView.setPadding(leftPadding, 0, rightPadding, 0)
                imgDoc.add(docView)
                if (i == 0) {
                    imgDoc.get(i).setImageDrawable(getDrawable(colorDotTrue, colorDotSize, colorDotSize))
                } else {
                    imgDoc.get(i).setImageDrawable(getDrawable(colorDotFalse, colorDotSize, colorDotSize))
                }
                ll_doc!!.addView(imgDoc.get(i))
            }
            imgDocDrawable.add(getDrawable(colorDotTrue, colorDotSize, colorDotSize))
            imgDocDrawable.add(getDrawable(colorDotFalse, colorDotSize, colorDotSize))
            roll_pager!!.setDoc(imgDoc, imgDocDrawable)
        }
    }

}