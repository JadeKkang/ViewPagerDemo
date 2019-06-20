package com.example.mlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 韩晓康
 * @date :2019/6/20 14:42
 * @description:
 */
public class RollViewPager2 extends RelativeLayout {
    private Context mContext;
    /*设置图片地址集合*/
    private List<String> imgList;
    /*设置本地图片地址集合*/
    private List<Integer> imgLists;
    /*viewpager*/
    private RollView3 roll_pager;
    private FrameLayout fl;
    /*装小圆点的LinearLayout*/
    private LinearLayout ll_doc;
    /*装小圆点的ImageView*/
    private List<ImageView> imgDoc = new ArrayList<>();
    /*小圆点的Drawable*/
    private List<Drawable> imgDocDrawable = new ArrayList<>();
    /*小圆点选中颜色*/
    private int colorDotTrue = 0;
    /*小圆点没有选中颜色*/
    private int colorDotFalse = 0;
    /*装小圆点容器的背景色*/
    private int llColorDoc = 0;
    /*小圆点的大小*/
    private int colorDotSize = 20;
    /*是否自动播放*/
    private boolean isStart = true;
    /*小圆点是否显示*/
    private boolean isDoc = true;
    /*小圆点显示位置 左  中  右*/
    private int gravityDoc = 0;
    /*小圆点显示左边  距离左边距离*/
    private int leftMargin = 0;
    /*小圆点显示右边  距离右边距离*/
    private int rightMargin = 0;
    /*小圆点显示  距离底部距离*/
    private int bottomMargin = 20;
    /*几个小圆点之间的左间距*/
    private int leftPadding = 10;
    /*几个小圆点之间的右间距*/
    private int rightPadding = 10;
    /*设置播放间隔*/
    private int time = 4200;
    /*切换图片需要的时间*/
    private int time2 = 800;
    /*切换图片间距*/
    private int pagemargins = 10;
    /*点击回调*/
    private RollView3.OnPagerClickCallback click;

    public RollViewPager2(Context context) {
        this(context, null);
    }

    public RollViewPager2(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RollViewPager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.roll_rl2, this, true);
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RollViewPager);
        if (typedArray != null) {
            colorDotTrue = typedArray.getColor(R.styleable.RollViewPager_color_doc_true, ContextCompat.getColor(context, R.color.colorDotTrue));
            colorDotFalse = typedArray.getColor(R.styleable.RollViewPager_color_doc_false, ContextCompat.getColor(context, R.color.colorDotFalse));
            llColorDoc = typedArray.getColor(R.styleable.RollViewPager_ll_color_Doc, ContextCompat.getColor(context, R.color.llColorDoc));
            isStart = typedArray.getBoolean(R.styleable.RollViewPager_isStart, true);
            isDoc = typedArray.getBoolean(R.styleable.RollViewPager_isDoc, true);
            gravityDoc = typedArray.getInteger(R.styleable.RollViewPager_gravity_doc, 1);
            time = typedArray.getInteger(R.styleable.RollViewPager_time, 4200);
            time2 = typedArray.getInteger(R.styleable.RollViewPager_time2, 800);
            pagemargins = typedArray.getInteger(R.styleable.RollViewPager_pagemargins, 10);

            float colorDot = typedArray.getDimension(R.styleable.RollViewPager_color_size, 20f);
            float left = typedArray.getDimension(R.styleable.RollViewPager_doc_leftMargin, 0f);
            float right = typedArray.getDimension(R.styleable.RollViewPager_doc_rightMargin, 0f);
            float bottom = typedArray.getDimension(R.styleable.RollViewPager_doc_bottomMargin, 20f);
            float leftP = typedArray.getDimension(R.styleable.RollViewPager_doc_leftPadding, 10f);
            float rightp = typedArray.getDimension(R.styleable.RollViewPager_doc_leftPadding, 10f);
            colorDotSize = pxtodp(colorDot);
            leftMargin = pxtodp(left);
            rightMargin = pxtodp(right);
            bottomMargin = pxtodp(bottom);
            leftPadding = pxtodp(leftP);
            rightPadding = pxtodp(rightp);

        }
        typedArray.recycle();

        roll_pager = view.findViewById(R.id.roll_pager);
        fl = view.findViewById(R.id.fl);
        ll_doc = view.findViewById(R.id.ll_doc);
    }

    /**
     * 设置图片地址
     */
    public void setImg(List<String> imgList) {
        this.imgList = imgList;
        roll_pager.setResImageId(imgList);
        init(imgList);//初始化控件属性
    }
    public void setImgS(List<Integer> imgLists) {
        this.imgLists = imgLists;
        roll_pager.setResImageIds(imgLists);
        initS(imgLists);//初始化控件属性
    }
    /**
     * 停止播放
     */
    private void stopPlay() {
        roll_pager.stopPlay();
    }

    /**
     * 设置Viewpager  PageTransformer属性
     */
    private void setPageTransformer(Boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        roll_pager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /**
     * 点击回调
     */
    public void setItemClick(RollView3.OnPagerClickCallback click) {
        this.click = click;
        roll_pager.setCallOnclick(click);
    }

    /**
     * 产生shape类型的drawable
     */
    private GradientDrawable getDrawable(int solidColor, int width, int height) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(solidColor);
        drawable.setSize(width, height);
        return drawable;
    }

    /**
     * px转dp
     */
    private int pxtodp(Float dpValue) {
        float density = mContext.getResources().getDisplayMetrics().density;
        float d = dpValue / density + 0.5f;
        return (int) d;
    }

    /**
     * 初始化控件属性
     */
    private void init(List<String> imgList) {
        switch (gravityDoc) {
            case 0:
                ll_doc.setGravity(Gravity.LEFT);
                break;
            case 1:
                ll_doc.setGravity(Gravity.CENTER);
                break;
            case 2:
                ll_doc.setGravity(Gravity.RIGHT);
                break;
        }
        fl.removeView(roll_pager);//防止设置setCurrentItem 过大引起anr
        fl.removeView(ll_doc);//防止设置setCurrentItem 过大引起anr
        fl.addView(roll_pager);//防止设置setCurrentItem 过大引起anr
        fl.addView(ll_doc);//防止设置setCurrentItem 过大引起anr
        ll_doc.setBackgroundColor(llColorDoc);
        roll_pager.setTime(time).setDuration(time2).setPageMargins(pagemargins).setAadpter().setCurrentItem((Integer.MAX_VALUE/2)-1);
        if (isStart) {
            roll_pager.startPlay();
        }
        if (isDoc) {//是否显示小圆点
            ll_doc .removeAllViews();
            for (int i = 0; i < imgList.size(); i++){
                ImageView docView =new  ImageView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (gravityDoc == 0) {
                    if (i == 0) {
                        layoutParams.leftMargin = leftMargin;
                    }
                }
                if (gravityDoc == 2) {
                    if (i == (imgList.size() - 1)) {
                        layoutParams.rightMargin = rightMargin;
                    }
                }
                layoutParams.bottomMargin = bottomMargin;
                layoutParams.topMargin = bottomMargin;
                docView.setLayoutParams(layoutParams);
                docView.setPadding(leftPadding, 0, rightPadding, 0);
                imgDoc.add(docView);
                if (i == 0) {
                    imgDoc.get(i).setImageDrawable(getDrawable(colorDotTrue, colorDotSize, colorDotSize));
                } else {
                    imgDoc.get(i).setImageDrawable(getDrawable(colorDotFalse, colorDotSize, colorDotSize));
                }
                ll_doc.addView(imgDoc.get(i));
            }
            imgDocDrawable.add(getDrawable(colorDotTrue, colorDotSize, colorDotSize));
            imgDocDrawable.add(getDrawable(colorDotFalse, colorDotSize, colorDotSize));
            roll_pager.setDot(imgDoc).setImgDocDrawable(imgDocDrawable);
        }
    }
    private void initS(List<Integer> imgList) {
        switch (gravityDoc) {
            case 0:
                ll_doc.setGravity(Gravity.LEFT);
                break;
            case 1:
                ll_doc.setGravity(Gravity.CENTER);
                break;
            case 2:
                ll_doc.setGravity(Gravity.RIGHT);
                break;
        }
        fl.removeView(roll_pager);//防止设置setCurrentItem 过大引起anr
        fl.removeView(ll_doc);//防止设置setCurrentItem 过大引起anr
        fl.addView(roll_pager);//防止设置setCurrentItem 过大引起anr
        fl.addView(ll_doc);//防止设置setCurrentItem 过大引起anr
        ll_doc.setBackgroundColor(llColorDoc);
        roll_pager.setTime(time).setDuration(time2).setPageMargins(pagemargins).setAadpter().setCurrentItem((Integer.MAX_VALUE/2)-1);
        if (isStart) {
            roll_pager.startPlay();
        }
        if (isDoc) {//是否显示小圆点
            ll_doc .removeAllViews();
            for (int i = 0; i < imgList.size(); i++){
                ImageView docView =new  ImageView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (gravityDoc == 0) {
                    if (i == 0) {
                        layoutParams.leftMargin = leftMargin;
                    }
                }
                if (gravityDoc == 2) {
                    if (i == (imgList.size() - 1)) {
                        layoutParams.rightMargin = rightMargin;
                    }
                }
                layoutParams.bottomMargin = bottomMargin;
                layoutParams.topMargin = bottomMargin;
                docView.setLayoutParams(layoutParams);
                docView.setPadding(leftPadding, 0, rightPadding, 0);
                imgDoc.add(docView);
                if (i == 0) {
                    imgDoc.get(i).setImageDrawable(getDrawable(colorDotTrue, colorDotSize, colorDotSize));
                } else {
                    imgDoc.get(i).setImageDrawable(getDrawable(colorDotFalse, colorDotSize, colorDotSize));
                }
                ll_doc.addView(imgDoc.get(i));
            }
            imgDocDrawable.add(getDrawable(colorDotTrue, colorDotSize, colorDotSize));
            imgDocDrawable.add(getDrawable(colorDotFalse, colorDotSize, colorDotSize));
            roll_pager.setDot(imgDoc).setImgDocDrawable(imgDocDrawable);
        }
    }
}
