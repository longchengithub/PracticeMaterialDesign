package com.example.chenlong.practicematerialdesign.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenlong.practicematerialdesign.R;
import com.example.chenlong.practicematerialdesign.view.FanPage.FanFragment;
import com.example.chenlong.practicematerialdesign.view.FindPage.FindFragment;
import com.example.chenlong.practicematerialdesign.view.FollowPage.FollowFragment;
import com.example.chenlong.practicematerialdesign.view.HomePage.HomeFragment;
import com.example.chenlong.practicematerialdesign.view.LivePage.LiveFragment;
import com.example.chenlong.practicematerialdesign.view.Menu.LoginView;
import com.example.chenlong.practicematerialdesign.view.ZonePage.ZoneFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    @BindView(R.id.main_nav_view)
    NavigationView mNavView;
    @BindView(R.id.main_drawer)
    DrawerLayout mMainDrawer;

    @BindView(R.id.main_login_drawer)
    ImageView mLoginDrawer;
    @BindView(R.id.main_login_face)
    ImageView mLoginFace;
    @BindView(R.id.main_login_text)
    TextView mLoginText;
    @BindView(R.id.main_game_menu)
    ImageView mGameMenu;
    @BindView(R.id.main_download_menu)
    ImageView mDownloadMenu;
    @BindView(R.id.main_search_menu)
    ImageView mSearchMenu;

    @BindView(R.id.tab_menu)
    TabLayout mTabMenu;
    @BindView(R.id.vp)
    ViewPager mViewPager;


    private ActionBarDrawerToggle mToggle;
    private Fragment[] mFragments;
    private FragmentManager mFragmentManager;
    private FragmentViewPagerAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToggle();

        initNavigationView();

        initTabLayout();

        initFragment();

        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager()
    {

        mFragmentAdapter = new FragmentViewPagerAdapter(mFragmentManager);
        mFragmentAdapter.setmFragments(mFragments);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(1);
    }

    /**
     * 初始化所有Fragment
     */
    private void initFragment()
    {
        LiveFragment live = new LiveFragment();
        HomeFragment home = new HomeFragment();
        FanFragment fan = new FanFragment();
        ZoneFragment zone = new ZoneFragment();
        FollowFragment follow = new FollowFragment();
        FindFragment find = new FindFragment();

        mFragments = new Fragment[]{live, home, fan, zone, follow, find};
        mFragmentManager = getSupportFragmentManager();

    }

    /**
     * 初始化选项卡
     */
    private void initTabLayout()
    {
        mTabMenu.setupWithViewPager(mViewPager);
    }

    /**
     * 初始化导航栏
     */
    private void initNavigationView()
    {
        //给导航栏的item指定颜色
        ColorStateList csl = getResources().getColorStateList(R.color.nav_item_selector);
        mNavView.setItemIconTintList(csl);
        mNavView.setItemTextColor(csl);

        //点击登录按钮
        mNavView.getHeaderView(0).findViewById(R.id.nav_main_header_login).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, LoginView.class));
                mMainDrawer.closeDrawer(mNavView);
            }
        });

        //TODO 导航栏功能
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Toast.makeText(MainActivity.this, "我点了菜单按钮", Toast.LENGTH_SHORT).show();
                mMainDrawer.closeDrawer(mNavView);
                return true;
            }
        });
    }

    /**
     * 初始化抽屉
     */
    private void initToggle()
    {
        mToggle = new ActionBarDrawerToggle(this, mMainDrawer, 0, 0);
        mToggle.syncState();
        mMainDrawer.addDrawerListener(mToggle);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        mToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    /**
     * 导航栏点击事件
     *
     * @param view
     */
    @OnClick({R.id.main_login_drawer, R.id.main_login_face, R.id.main_login_text, R.id.main_game_menu, R.id.main_download_menu, R.id.main_search_menu})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.main_login_drawer:
                //TODO 判断是否已登录
                break;
            case R.id.main_login_face:
                mMainDrawer.openDrawer(mNavView);
                break;
            case R.id.main_login_text:
                break;
            case R.id.main_game_menu:
                //TODO
                break;
            case R.id.main_download_menu:
                //TODO
                break;
            case R.id.main_search_menu:
                //TODO
                break;
        }
    }

}
