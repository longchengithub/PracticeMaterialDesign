package com.example.chenlong.practicematerialdesign.view.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.chenlong.practicematerialdesign.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chenlong on 2016/12/12.
 */

public class Banner extends RelativeLayout implements ViewPager.OnPageChangeListener
{

    private ViewPager mViewPager;
    private LinearLayout mPoints;
    private ImageView[] mImages;

    public Banner(Context context)
    {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.home_banner, this);
        mViewPager = ((ViewPager) findViewById(R.id.vp));
        mPoints = ((LinearLayout) findViewById(R.id.points));
    }

    /**
     * 根据传入的String类型的图片地址 生成对应的view图片
     *
     * @param path
     */
    public void setImages(String[] path)
    {
        if (path==null)
            return;
        mImages = new ImageView[path.length + 2];
        for (int i = 0; i < mImages.length; i++)
        {
            ImageView image = new ImageView(getContext());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            if (i == 0)
            {
                Glide.with(getContext()).load(path[mImages.length - 3]).into(image);
            } else if (i == mImages.length - 1)
            {
                Glide.with(getContext()).load(path[0]).into(image);

            } else
            {
                Glide.with(getContext()).load(path[i - 1]).into(image);
                addPoints(i - 1);
                Log.d("Banner", "我创建了" + i + "次");
            }
            mImages[i] = image;
        }

        mViewPager.setAdapter(new MyAdapter());
        mViewPager.setCurrentItem(1);
        mPoints.getChildAt(0).setEnabled(false);
        mViewPager.addOnPageChangeListener(this);
        startScroll();
    }

    /**
     * 生成对应数量的小圆点
     */
    private void addPoints(int index)
    {
        ImageView point = new ImageView(getContext());
        point.setImageResource(R.drawable.shape_oval);

        mPoints.addView(point);
        point.setTag(index);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) point.getLayoutParams();
        params.leftMargin = 10;
        point.setLayoutParams(params);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    private int lastIndex;

    //滑动到下个页面静止前 被选中状态
    @Override
    public void onPageSelected(int position)
    {
        position = (position - 1) % mPoints.getChildCount();
        //先改变上一次的点为白色  然后变换这次的点为红的
        mPoints.getChildAt(lastIndex).setEnabled(true);
        mPoints.getChildAt(position).setEnabled(false);
        lastIndex = position;
    }

    //延时时间
    private int delayTime = 5;

    private CompositeSubscription compositeSubscription;

    private boolean isStopScroll;

    /**
     * RxJava timer操作符实现轮播
     * CompositeSubscription 可以添加或移除一个订阅者 实现开启或关闭轮播
     */
    public void startScroll()
    {
        compositeSubscription = new CompositeSubscription();
        Subscription subscribe = Observable.timer(delayTime, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>()
                {
                    @Override
                    public void call(Long aLong)
                    {
                        if (isStopScroll)
                            return;

                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                        isStopScroll = true;
                    }
                });
        compositeSubscription.add(subscribe);
    }

    /**
     * 页面彻底滑动到为 静止
     *
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state)
    {
        if (state == ViewPager.SCROLL_STATE_IDLE)
        {
            if (mViewPager.getCurrentItem() == 0)
            {
                mViewPager.setCurrentItem(mImages.length - 2, false);    //false表示滑动无动画
            } else if (mViewPager.getCurrentItem() == mImages.length - 1)
            {
                mViewPager.setCurrentItem(1, false);
            }

            isStopScroll = false;
            startScroll();
        }

        if (state == ViewPager.SCROLL_STATE_DRAGGING)
        {
            isStopScroll = true;
            compositeSubscription.unsubscribe();
        }
    }

    private class MyAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            container.addView(mImages[position]);
            return mImages[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            mViewPager.removeView(mImages[position]);
        }
    }
}
