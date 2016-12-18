package com.example.chenlong.practicematerialdesign.view.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenlong.practicematerialdesign.R;
import com.example.chenlong.practicematerialdesign.view.HomePage.RecommendEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenlong on 2016/12/15.
 */

public class Card extends CardView
{
    @BindView(R.id.iv_activity)
    ImageView mImageView;
    @BindView(R.id.tv_des)
    TextView mDes;
    private Context mContext;

    public Card(Context context)
    {
        this(context, null);
    }

    public Card(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View.inflate(mContext, R.layout.item_cardview, this);
        ButterKnife.bind(this);
    }

    public void setDatas(RecommendEntity.ResultBean.BodyBean bean)
    {
        Glide.with(mContext).load(bean.getCover())
                .placeholder(R.drawable.img_tips_error_load_error)
                .centerCrop()
                .error(R.drawable.img_tips_error_load_error)
                .into(mImageView);
        mDes.setText(bean.getTitle());
    }
}
