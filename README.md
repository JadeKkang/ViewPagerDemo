# 效果展示
![](https://github.com/JadeKkang/ViewPagerDemo/blob/master/images/viewpager.gif)
# 功能介绍
    1.GuideViewPager 简单的app引导页  具有点击回调
    
    2.RollViewPager  可做app首页banner图
    
    3.RollViewPager 可以设置时候自动滚动，可以设置是否显示小圆点，可以设置小圆点大小、显示位置、选中和没有选中颜色、小圆点之间的间隙、距离底部的距离、装小圆点LinearLayout的背景颜色、可以设置自动播放间隔时间
# 使用
    1.在项目gradle中添加
        allprojects{
           repositories {
                    ...
              maven { 
	        url 'https://jitpack.io' 
	      }
          }
       }
       
    2.添加依赖
    
    {implementation 'com.github.JadeKkang:ViewPagerDemo:v1.0.3'}
    3.xml中使用
    <--引导页-->
    <com.example.mlibrary.GuideViewPager
      android:id="@+id/guide_pager"
      android:layout_width="match_parent"
      android:layout_height="0dp"
      android:layout_weight="1">
    </com.example.mlibrary.GuideViewPager>
      <--banner-->
     <com.example.mlibrary.RollViewPager
        android:id="@+id/roll"
        android:layout_width="match_parent"
        android:layout_height="220dp"
        app:color_doc_true="@color/colorDotTrue"
        app:color_doc_false="@color/colorDotFalse"
        app:gravity_doc="center"
        >
    </com.example.mlibrary.RollViewPager>
    
    <com.example.mlibrary.RollViewPager
        android:id="@+id/roll2"
        android:layout_width="match_parent"
        android:layout_height="220dp"
        app:color_doc_true="@color/colorDotTrue"
        app:color_doc_false="@color/colorDotFalse"
        app:gravity_doc="left"
        app:doc_leftMargin="10dp"
        app:isStart="false"
        >
    </com.example.mlibrary.RollViewPager>
    
    <com.example.mlibrary.RollViewPager
        android:id="@+id/roll3"
        android:layout_width="match_parent"
        android:layout_height="220dp"
        app:color_doc_true="@color/colorDotTrue"
        app:color_doc_false="@color/colorDotFalse"
        app:gravity_doc="right"
        app:doc_rightMargin="10dp"
        app:ll_color_Doc="#edb4b9"
        app:isStart="false"
        >
    </com.example.mlibrary.RollViewPager>
    <com.example.mlibrary.RollViewPager2
        android:id="@+id/roll4"
        android:layout_width="match_parent"
        android:layout_height="220dp"
        app:color_doc_true="@color/colorDotTrue"
        app:color_doc_false="@color/colorDotFalse"
        app:gravity_doc="right"
        app:doc_rightMargin="10dp"
        app:ll_color_Doc="#edb4b9"
        app:isStart="true"
        app:isDoc="false"
        >
    </com.example.mlibrary.RollViewPager2>
    
# 自定义属性
| 属性 | 值 | 描述 | 
| ------------- |:-------------:| -----:| 
| color_doc_true |#ffffff| 小圆点选中颜色值 | 
| color_doc_false | #bfbbbb | 小圆点未选中颜色值 | 
| color_size | 20 | 小圆点大小 | 
| isStart | true | 是否自动播放 | 
| isDoc |  true | 是否显示小圆点 | 
| ll_color_Doc | #00ffffff | 装小圆点LinearLayout的背景颜色 | 
| doc_leftMargin | 10dp | 当设置小圆点左侧显示时  距离左边的距离 | 
| doc_rightMargin |10dp | 当设置小圆点右侧显示时  距离右边的距离  | 
| doc_bottomMargin | 10dp | 小圆点距离底部的距离 | 
| doc_leftPadding | 5dp | 小圆点距离左边间隙 | 
| doc_rightPadding | 5dp| 小圆点距离右边间隙 | 
| time | 3000 | 自动播放间隔时间 | 
| time2 | 3000 | 图片切换时间 | 
| pagemargins | 10 | 图片之间距离 | 
| gravity_doc |center、left、right | 小圆点显示位置 左、中、右 | 
# 预留方法

	1.setImg(imgList: List<Int>) GuideViewPager设置资源图片

	2.setItemClick(click: ItemClick) GuideViewPager设置点击回调监听

	3.setImg(imgList: List<String>) RollViewPager设置资源图片
	
	4.stopPlay() RollViewPager停止播放
	
	5.setPageTransformer(reverseDrawingOrder: Boolean, transformer: ViewPager.PageTransformer?) 设置Viewpager  PageTransformer属性
	
	6.setItemClick(click: ItemClick) RollViewPager设置点击回调监听
	
	7.setImg(imgList: List<Int>) RollViewPager2设置资源图片

	
# 注意
       
       1.已经使用过 glide 防止依赖重合 implementation 'com.github.bumptech.glide:glide:3.7.0'
       
       2.注意添加权限  <uses-permission android:name="android.permission.INTERNET"></uses-permission>
