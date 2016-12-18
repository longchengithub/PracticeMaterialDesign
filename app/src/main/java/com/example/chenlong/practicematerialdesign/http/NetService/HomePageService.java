package com.example.chenlong.practicematerialdesign.http.NetService;

import com.example.chenlong.practicematerialdesign.view.HomePage.RecommendEntity;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by chenlong on 2016/12/12.
 */

public interface HomePageService
{
    @GET("x/show/old?platform=android&device=&build=412001")
    Observable<RecommendEntity> getRecommendInfo();
}
