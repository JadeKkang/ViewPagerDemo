# 效果展示
![](https://github.com/JadeKkang/like_view/blob/master/images/likeView.gif)
# 功能介绍
  1.GuideViewPager 简单的app引导页  具有点击回调
  2.RollViewPager  可做app首页banner图
  3.RollViewPager 可以设置时候自动滚动，可以设置是否显示小圆点，可以设置小圆点大小、显示位置、选中和没有选中颜色、小圆点之间的间隙、距离底部的距离、
  装小圆点LinearLayout的背景颜色
# 使用
1.在项目gradle中添加<br>  
allprojects {<br> 
repositories {<br> 
...<br> 
maven { url 'https://jitpack.io' }<br> 
}<br> 
}<br> 
2.添加依赖<br> 
 {implementation 'com.github.JadeKkang:like_view:v1.0'}<br> 
3.xml中使用<br>  
<com.example.library.LikeView<br>
android:layout_width="wrap_content"<br> 
android:layout_height="wrap_content"<br> 
app:circleColor="@color/colorAccent"<br> 
app:bitmap="@mipmap/heart"/><br> 
# 自定义属性
| 属性 | 值 | 描述 | 
| ------------- |:-------------:| -----:| 
| circleColor |#FF4081| 点击之后出现圆形的颜色 | 
| bitmap | @mipmap/heart | 显示的图片（如 心形图片） | 
# 预留方法

	1.setIvResore(int ivResore)设置图片资源

	2.setCircleColor(int circleColor)设置点击之后出现圆形的颜色

	3.setDotNum(int dotNum,int[] dotColors)设置周边爆炸效果圆点数量和颜色值
