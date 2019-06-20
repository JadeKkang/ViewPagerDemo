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
public class RollView2 extends ViewPager {
    private int currentItem = 0;/* 当前播放页数 */
    private boolean isAutoPlay = false;
    private boolean isChexk = true;/* 判断是否滑动从第一章到最后一张 */
    private Context context;
    private int[] resImageIds;/* 图片数组 */
    private List<ImageView> dots; /* 点的所位置不固定，以需要让调用者传入 */
    private List<Drawable> imgDocDrawable; /* 点的所位置不固定，以需要让调用者传入 */
    private OnPagerClickCallback onPagerClickCallback;
    private boolean isClick = false;
    private Timer timer = null;//计时器
    private TimerTask timerTask = null;
    private boolean isLoop = true;

    public RollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(this.getContext(),
                    new AccelerateInterpolator());
            field.set(this, scroller);
            scroller.setmDuration(800);
        } catch (Exception e) {
        }
    }

    public RollView2(Context context) {
        this(context, null);
        this.context = context;
    }


    public void setChexk(boolean isChexk) {
        this.isChexk = isChexk;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    private boolean hasSetAdapter = false;

    public void setAadpter() {
        if (!hasSetAdapter) {
            hasSetAdapter = true;
            this.setOnPageChangeListener(new MyPageChangeListener());
            this.setAdapter(new ViewPagerAdapter());

        }
    }

    public void setCallOnclick(OnPagerClickCallback onPagerClickCallback) {
        this.onPagerClickCallback = onPagerClickCallback;
    }

    public void setResImageIds(int[] resImageIds) {
        this.resImageIds = resImageIds;
    }

    public void setDot(List<ImageView> dots) {
        this.dots = dots;
    }

    public void setImgDocDrawable(List<Drawable> imgDocDrawable) {
        this.imgDocDrawable = imgDocDrawable;
    }

    class ViewPagerAdapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(resImageIds[position]);
//            Glide.with(context).load(resImageIds[position]).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            if (isClick) {
                imageView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        onPagerClickCallback.onPagerClick(position);
                    }
                });
            }
            try {
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
            return resImageIds.length;
//            return Integer.MAX_VALUE;
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
                handler2.sendEmptyMessageDelayed(1, 3000);
            }
            switch (state) {
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                case 0:
                    if (isChexk) {
                        int i = getCurrentItem() + 1;
                        if (getCurrentItem() == getAdapter().getCount() - 1
                                && !isAutoPlay) {
                            setCurrentItem(1, false);
//                            setCurrentItem((((Integer.MAX_VALUE / 2) / resImageIds.length) * resImageIds.length), false);
                        } else if (i == 1 && !isAutoPlay) {
                            setCurrentItem(getAdapter().getCount() - 1, false);
                        }
                    }

                    break;
            }
        }

        /**
         * 这个方法会在屏幕滚动过程中不断被调用。
         */
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        /*这个方法有一个参数position，代表哪个页面被选中。当用手指滑动翻页的时候，如果翻动成功了
        （滑动的距离够长），
        手指抬起来就会立即执行这个方法，position就是当前滑动到的页面。
        如果直接setCurrentItem翻页
        ，那position就和setCurrentItem的参数一致，
        这种情况在onPageScrolled执行方法前就会立即执行。*/
        @Override
        public void onPageSelected(int pos) {
            currentItem = pos;
            if (dots != null && dots.size() > 0) {
                for (int i = 0; i < dots.size(); i++) {
                    if (i == pos) {
                        dots.get(i).setImageDrawable(imgDocDrawable.get(0));

                    } else {
                        dots.get(i).setImageDrawable(imgDocDrawable.get(1));
                    }
                }
            }
            if (pos == 0) {
                setCurrentItem(getAdapter().getCount() - 2, false);
            } else if (pos == resImageIds.length - 1) {
                setCurrentItem(1, false);
            }

        }

    }

    public void setPageTf() {
        this.setPageTransformer(true, new DepthPageTransformer2());
    }


    /**
     * 开始轮播图切换
     */
    public void startPlay() {
        startTime();
    }

    private void startTime() {
        if (timer == null) {
            timer = new Timer();
        }
        timerTask = new TimerTask() {

            @Override
            public void run() {
                currentItem = (currentItem + 1) % resImageIds.length;
                if (isLoop) {
                    handler2.obtainMessage().sendToTarget();
                }
            }
        };
        timer.schedule(timerTask, 3000);//1000ms执行一次
    }

    private Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            int currentItem = getCurrentItem();
            int nextItem = currentItem + 1;
            if (nextItem > getAdapter().getCount() - 1) {
                nextItem = 1;
            }
            setCurrentItem(nextItem);
            handler2.removeMessages(1);
            handler2.sendEmptyMessageDelayed(1, 3000);
        }
    };


    /**
     * 处理page点击的回调接口
     */
    public interface OnPagerClickCallback {
        public abstract void onPagerClick(int position);
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
