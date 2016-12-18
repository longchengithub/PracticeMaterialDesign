package com.example.chenlong.practicematerialdesign.http.NetService;

import com.example.chenlong.practicematerialdesign.view.HomePage.BannerEntity;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by chenlong on 2016/12/9.
 */

public interface BannerServices
{
    @GET("x/banner?plat=4&build=411007&channel=bilih5")
    Observable<BannerEntity> getBanner();

}
