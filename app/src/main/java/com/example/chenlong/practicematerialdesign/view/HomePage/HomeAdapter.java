package com.example.chenlong.practicematerialdesign.view.HomePage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenlong.practicematerialdesign.R;
import com.example.chenlong.practicematerialdesign.view.widget.Banner;
import com.example.chenlong.practicematerialdesign.view.widget.Card;
import com.example.chenlong.practicematerialdesign.view.widget.Grid;

import java.util.List;

/**
 * Created by chenlong on 2016/12/6.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<BannerEntity.BannerData> mBannerData;
    private List<RecommendEntity.ResultBean> mResult;
    private Context mContext;

    public HomeAdapter(Context context)
    {
        this.mContext = context;
    }

    public void setmBannerData(List<BannerEntity.BannerData> mBannerData)
    {
        this.mBannerData = mBannerData;
    }

    public void setmResult(List<RecommendEntity.ResultBean> mResult)
    {
        this.mResult = mResult;
    }

    /**
     * 话题
     */
    private static final String TYPE_WEBLINK = "weblink";
    /**
     * 活动
     */
    private static final String TYPE_ACTIVITY = "activity";

    public enum ITEM_TYPE
    {
        TYPE_BANNER,    //轮播图
        TYPE_TOPIC,     //话题
        TYPE_ACTIVITY,  //活动
        TYPE_GRID       //其余的列表图
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == ITEM_TYPE.TYPE_BANNER.ordinal())
        {
            viewHolder = new bannerViewHolder(View.inflate(parent.getContext(), R.layout.item_homepage_adapter_banner, null));
        } else if (viewType == ITEM_TYPE.TYPE_TOPIC.ordinal())
        {
            viewHolder = new topicViewHolder(View.inflate(parent.getContext(), R.layout.item_homepage_adapter_topic, null));
        } else if (viewType == ITEM_TYPE.TYPE_ACTIVITY.ordinal())
        {
            viewHolder = new activityViewHolder(View.inflate(parent.getContext(), R.layout.item_homepage_adapter_activity, null));
        } else if (viewType == ITEM_TYPE.TYPE_GRID.ordinal())
        {
            viewHolder = new gridViewHolder(View.inflate(parent.getContext(), R.layout.item_homepage_adapter_grid, null));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof bannerViewHolder) //Banner的BindView
        {
            ((bannerViewHolder) holder).banner.setImages(getImageUrls());
        } else if (holder instanceof topicViewHolder)
        {
            Glide.with(mContext)
                    .load(mResult.get(position - 1).getBody().get(0).getCover())
                    .placeholder(R.drawable.img_tips_error_banner_2233)
                    .centerCrop()
                    .error(R.drawable.img_tips_error_banner_tv)
                    .into(((topicViewHolder) holder).mTopicImageView);
        } else if (holder instanceof gridViewHolder)
        {
            ((gridViewHolder) holder).mHeaderDes.setText(mResult.get(position - 1).getHead().getTitle());
            ((gridViewHolder) holder).grid1.setDatas(mResult.get(position - 1).getBody().get(0));
            ((gridViewHolder) holder).grid2.setDatas(mResult.get(position - 1).getBody().get(1));
            ((gridViewHolder) holder).grid3.setDatas(mResult.get(position - 1).getBody().get(2));
            ((gridViewHolder) holder).grid4.setDatas(mResult.get(position - 1).getBody().get(3));
        } else if (holder instanceof activityViewHolder)
        {
            ((activityViewHolder) holder).title.setText(mResult.get(position - 1).getHead().getTitle());
            ((activityViewHolder) holder).c1.setDatas(mResult.get(position - 1).getBody().get(0));
            ((activityViewHolder) holder).c2.setDatas(mResult.get(position - 1).getBody().get(1));
            ((activityViewHolder) holder).c3.setDatas(mResult.get(position - 1).getBody().get(2));
            ((activityViewHolder) holder).c4.setDatas(mResult.get(position - 1).getBody().get(3));
        }
    }

    /**
     * 加载banner的图片
     *
     * @return
     */
    private String[] getImageUrls()
    {
        String mImages[] = new String[mBannerData.size()];
        for (int i = 0; i < mBannerData.size(); i++)
        {
            mImages[i] = mBannerData.get(i).getImage();
        }
        return mImages;
    }

    @Override
    public int getItemCount()
    {
        return 1 + mResult.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0)
            return ITEM_TYPE.TYPE_BANNER.ordinal();
        if (TYPE_WEBLINK.equals(mResult.get(position - 1).getType()))
            return ITEM_TYPE.TYPE_TOPIC.ordinal();
        if (TYPE_ACTIVITY.equals(mResult.get(position - 1).getType()))
            return ITEM_TYPE.TYPE_ACTIVITY.ordinal();
        return ITEM_TYPE.TYPE_GRID.ordinal();
    }


    /**
     * 轮播图的ViewHolder
     */
    public static class bannerViewHolder extends RecyclerView.ViewHolder
    {
        Banner banner;

        public bannerViewHolder(View itemView)
        {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }
    }

    /**
     * 话题viewHolder
     */
    public static class topicViewHolder extends RecyclerView.ViewHolder
    {

        ImageView mTopicImageView;

        public topicViewHolder(View itemView)
        {
            super(itemView);
            mTopicImageView = ((ImageView) itemView.findViewById(R.id.topic_view));
        }
    }

    /**
     * 活动viewHolder
     */
    public static class activityViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        Card c1;
        Card c2;
        Card c3;
        Card c4;

        public activityViewHolder(View itemView)
        {
            super(itemView);
            title = ((TextView) itemView.findViewById(R.id.header_des));
            c1 = ((Card) itemView.findViewById(R.id.card1));
            c2 = ((Card) itemView.findViewById(R.id.card2));
            c3 = ((Card) itemView.findViewById(R.id.card3));
            c4 = ((Card) itemView.findViewById(R.id.card4));
        }
    }

    /**
     * 其余网格类型viewHolder
     */
    public static class gridViewHolder extends RecyclerView.ViewHolder
    {

        ImageView mHeaderIcon;
        TextView mHeaderDes;
        TextView mHeaderLook;
        Grid grid1;
        Grid grid2;
        Grid grid3;
        Grid grid4;

        public gridViewHolder(View itemView)
        {
            super(itemView);
            mHeaderIcon = ((ImageView) itemView.findViewById(R.id.header_icon));
            mHeaderDes = ((TextView) itemView.findViewById(R.id.header_des));
            mHeaderLook = ((TextView) itemView.findViewById(R.id.header_look));
            grid1 = ((Grid) itemView.findViewById(R.id.grid1));
            grid2 = ((Grid) itemView.findViewById(R.id.grid2));
            grid3 = ((Grid) itemView.findViewById(R.id.grid3));
            grid4 = ((Grid) itemView.findViewById(R.id.grid4));
        }
    }
}



