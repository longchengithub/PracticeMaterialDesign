package com.example.chenlong.practicematerialdesign.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenlong.practicematerialdesign.R;
import com.example.chenlong.practicematerialdesign.view.HomePage.RecommendEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenlong on 2016/12/14.
 */

public class Grid extends LinearLayout
{
    @BindView(R.id.iv_grid)
    ImageView mGridView;
    @BindView(R.id.tv_tip)
    TextView mTipText;
    @BindView(R.id.tv_views)
    TextView mPlayCount;
    @BindView(R.id.tv_danmakus)
    TextView mDanmakus;

    private Context mContext;

    public Grid(Context context)
    {
        this(context, null);
    }

    public Grid(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public Grid(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View.inflate(mContext, R.layout.item_grid, this);
        ButterKnife.bind(this);
    }

    public void setDatas(RecommendEntity.ResultBean.BodyBean bean)
    {

        Glide.with(mContext).load(bean.getCover())
                .placeholder(R.drawable.img_tips_error_load_error)
                .centerCrop()
                .error(R.drawable.img_tips_error_load_error)
                .into(mGridView);

        mDanmakus.setText(bean.getDanmaku());
        mTipText.setText(bean.getTitle());
        mPlayCount.setText(bean.getPlay());
    }

}
