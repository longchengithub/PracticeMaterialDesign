package com.example.chenlong.practicematerialdesign.view.HomePage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.chenlong.practicematerialdesign.R;
import com.example.chenlong.practicematerialdesign.http.RetrofitHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by chenlong on 2016/12/6.
 */

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.SwipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.emptyView)
    ImageView emptyView;

    private HomeAdapter mAdapter;
    private List<BannerEntity.BannerData> mBannerData;
    private List<RecommendEntity.ResultBean> mResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        initSwipeRefresh();
        return view;
    }

    private void initSwipeRefresh()
    {
        //设置刷新时动画的颜色，可以设置4个
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.white);
        mSwipeRefresh.setOnRefreshListener(this);
    }

    /**
     * 联网操作RxJava 请求2条json数据填充到adapter类后 绑定适配器
     */
    private void initData()
    {
        RetrofitHelper.getBannerService().getBanner()
                .flatMap(new Func1<BannerEntity, Observable<RecommendEntity>>()
                {
                    @Override
                    public Observable<RecommendEntity> call(BannerEntity bannerEntity)
                    {
                        mBannerData = bannerEntity.getData();
                        return RetrofitHelper.getHomePageService().getRecommendInfo();
                    }
                })
                .map(new Func1<RecommendEntity, List<RecommendEntity.ResultBean>>()
                {
                    @Override
                    public List<RecommendEntity.ResultBean> call(RecommendEntity recommendEntity)
                    {
                        return recommendEntity.getResult();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<RecommendEntity.ResultBean>>()
                {
                    @Override
                    public void call(List<RecommendEntity.ResultBean> resultBeen)
                    {
                        mResult = resultBeen;
                        mAdapter.setmBannerData(mBannerData);
                        mAdapter.setmResult(mResult);

                        Log.i("HomeFragment", "轮播图的数据有" + mBannerData.size() + "条 ,其余的有" + mResult.size());
                        mRecyclerView.setAdapter(mAdapter);
                        if (isRefresh)
                        {
                            mAdapter.notifyDataSetChanged();
                            mSwipeRefresh.setRefreshing(false);
                            isRefresh = false;
                            mRecyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                        }
                    }
                }, new Action1<Throwable>()
                {
                    @Override
                    public void call(Throwable throwable)
                    {
                        initEmptyView();
                    }
                });
    }

    private void initEmptyView()
    {
        mSwipeRefresh.setRefreshing(false);
        mRecyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setImageResource(R.drawable.ic_empty_cute_girl_box);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HomeAdapter(getContext());
        initData();
    }

    private boolean isRefresh;

    @Override
    public void onRefresh()
    {
        isRefresh = true;
        clearData();
        initData();

    }

    private void clearData()
    {
        if (mBannerData != null)
            mBannerData.clear();
        if (mResult != null)
            mResult.clear();
    }
}
