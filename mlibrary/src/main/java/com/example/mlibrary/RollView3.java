package com.example.mlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 轮播广告
 * 2017/6/1
 * 韩晓康
 */
@SuppressLint("HandlerLeak")
public class RollView3 extends ViewPager {
    private Context context;
    private List<Integer> resImageIds;/* 图片数组 */
    private List<String> resImageId;/* 图片数组 */
    private List<ImageView> dots; /* 点的所位置不固定，以需要让调用者传入 */
    private List<Drawable> imgDocDrawable; /* 小圆点 */
    private OnPagerClickCallback onPagerClickCallback;/* 点的回调 */
    private Timer timer = null;//计时器
    private TimerTask timerTask = null;
    private int time = 3000;//循环播放时间
    private int duration = 0;//循环播放时间
    private int pageMargins = 10;//循环播放间隔
    private boolean isLoop = true;
    private boolean hasSetAdapter = false;//是否设置适配器

    public RollView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public RollView3(Context context) {
        this(context, null);
        this.context = context;
    }

    /**
     * 设置适配器
     */
    public RollView3 setAadpter() {
        try {
            if (!hasSetAdapter) {
                hasSetAdapter = true;
                this.setOnPageChangeListener(new MyPageChangeListener());
                this.setAdapter(new ViewPagerAdapter());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设置点击回调
     *
     * @param onPagerClickCallback
     */
    public RollView3 setCallOnclick(OnPagerClickCallback onPagerClickCallback) {
        this.onPagerClickCallback = onPagerClickCallback;
        return this;
    }
    /**
     * 设置本地资源图片
     *
     * @param resImageIds
     */
    public RollView3 setResImageIds(List<Integer> resImageIds) {
        this.resImageIds = resImageIds;
        return this;
    }

    /**
     * 设置本地资源图片
     *
     * @param resImageId
     */
    public RollView3 setResImageId(List<String> resImageId) {
        this.resImageId = resImageId;
        return this;
    }

    /**
     * 设置小圆点
     *
     * @param dots
     */
    public RollView3 setDot(List<ImageView> dots) {
        this.dots = dots;
        return this;
    }
    /**
     * 设置小圆点
     *
     * @param imgDocDrawable
     */
    public RollView3 setImgDocDrawable(List<Drawable> imgDocDrawable) {
        this.imgDocDrawable = imgDocDrawable;
        return this;
    }

    /**
     * 设置间隔时间
     *
     * @param time
     */
    public RollView3 setTime(int time) {
        this.time = time;
        return this;
    }

    /**
     * 设置每一张切换时间
     *
     * @param duration
     */
    public RollView3 setDuration(int duration) {
        this.duration = duration;
        setFixed();
        return this;
    }

    /**
     * 设置 图片之间的间隔
     * @param pageMargins
     * @return
     */
    public RollView3 setPageMargins(int pageMargins) {
        this.pageMargins = pageMargins;
        setPageMargin(pageMargins);
        return this;
    }

    //设置指示器
    private void setIndicator(int position) {

        if (dots != null && dots.size() > 0) {
            position = position % dots.size();
            for (int i = 0; i < dots.size(); i++) {
                dots.get(i).setImageDrawable(imgDocDrawable.get(0));
                if (position != i) {
                    dots.get(i).setImageDrawable(imgDocDrawable.get(1));
                }
            }
        }

    }

    /**
     * 设置动画
     */
    public RollView3 setPageTf() {
        this.setPageTransformer(true, new DepthPageTransformer2());
        return this;
    }


    /**
     * 开始轮播图切换
     */
    public RollView3 startPlay() {
        try {
            if (timer == null) {
                timer = new Timer();
            }
            timerTask = new TimerTask() {

                @Override
                public void run() {

                    if (isLoop) {
                        handler2.obtainMessage().sendToTarget();
                    }
                }
            };
            timer.schedule(timerTask, time);//1000ms执行一次
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 停止自动播放
     */
    public RollView3 stopPlay() {
        try {
            hasSetAdapter = false;
            handler2.removeMessages(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    private Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            try {
                if (getCurrentItem() >= Integer.MAX_VALUE / 2) {
                    setCurrentItem(getCurrentItem() + 1);
                } else {
                    setCurrentItem(Integer.MAX_VALUE / 2);
                }
                handler2.removeMessages(1);
                handler2.sendEmptyMessageDelayed(1, time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 处理page点击的回调接口
     */
    public interface OnPagerClickCallback {
        void onPagerClick(int position);
    }

    /**
     * 设置每一张切换时间
     */
    private void setFixed() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(this.getContext(),
                    new AccelerateInterpolator());
            field.set(this, scroller);
            scroller.setmDuration(duration);
        } catch (Exception e) {
        }
    }

    /**
     * 适配器
     */
    class ViewPagerAdapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(context);
            try {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                if (resImageIds != null && resImageIds.size() > 0) {
                    Glide.with(context).load(resImageIds.get(position % resImageIds.size())).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
                } else {
                    Glide.with(context).load(resImageId.get(position % resImageId.size())).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
                }
                    imageView.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (resImageIds != null && resImageIds.size() > 0) {
                                onPagerClickCallback.onPagerClick(position % resImageIds.size());
                            } else {
                                onPagerClickCallback.onPagerClick(position % resImageId.size());
                            }

                        }
                    });
                ((ViewPager) container).addView(imageView, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

    }

    class MyPageChangeListener implements OnPageChangeListener {

        /**
         * 0（END）,1(PRESS) , 2(UP) 。
         * 当用手指滑动翻页时，手指按下去的时候会触发这个方法，state值为1，
         * 手指抬起时，如果发生了滑动（即使很小），这个值会变为2，然后最后变为0 。
         * 总共执行这个方法三次。一种特殊情况是手指按下去以后一点滑动也没有发生，
         * 这个时候只会调用这个方法两次，state值分别是1,0 。
         * 当setCurrentItem翻页时，会执行这个方法两次，state值分别为2 , 0 。
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            handler2.removeMessages(1);
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                handler2.sendEmptyMessageDelayed(1, time);
            }
        }

        /**
         * 这个方法会在屏幕滚动过程中不断被调用。
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        /*这个方法有一个参数position，代表哪个页面被选中。当用手指滑动翻页的时候，如果翻动成功了
        （滑动的距离够长），
        手指抬起来就会立即执行这个方法，position就是当前滑动到的页面。
        如果直接setCurrentItem翻页
        ，那position就和setCurrentItem的参数一致，
        这种情况在onPageScrolled执行方法前就会立即执行。*/
        @Override
        public void onPageSelected(int pos) {
            setIndicator(pos);
        }

    }

    class DepthPageTransformer2 implements PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            if (position < -1) {
                position = -1;
            } else if (position > 1) {
                position = 1;
            }

            float tempScale = position < 0 ? 1 + position : 1 - position;

            float slope = (1.0f - 0.9f) / 1;
            //一个公式
            float scaleValue = 0.9f + tempScale * slope;
            page.setScaleX(1.0f);
            page.setScaleY(scaleValue);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                page.getParent().requestLayout();
            }
        }
    }

}
