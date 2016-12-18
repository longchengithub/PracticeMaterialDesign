package com.example.chenlong.practicematerialdesign.view.Menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenlong.practicematerialdesign.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenlong on 2016/12/1.
 */
public class LoginView extends AppCompatActivity {
    @BindView(R.id.login_back)
    ImageView mLoginBack;
    @BindView(R.id.login_forget_psd)
    TextView mLoginForgetPsd;
    @BindView(R.id.image_22)
    ImageView mImage22;
    @BindView(R.id.image_33)
    ImageView mImage33;
    @BindView(R.id.edit_name)
    AppCompatEditText mLoginAccount;
    @BindView(R.id.alter_name)
    ImageView MAlterName;
    @BindView(R.id.edit_psd)
    AppCompatEditText mLoginPsd;
    @BindView(R.id.alter_psd)
    ImageView mAlterPsd;
    @BindView(R.id.login_register)
    AppCompatButton loginRegister;
    @BindView(R.id.login_commit)
    AppCompatButton loginCommit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initAnimation();
    }

    private void initAnimation() {

        //密码获取焦点时 改变图片背景
        mLoginPsd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focusable) {
                if (focusable) {
                    mImage22.setImageResource(R.drawable.ic_22_hide);
                    mImage33.setImageResource(R.drawable.ic_33_hide);
                } else {
                    mImage22.setImageResource(R.drawable.ic_22);
                    mImage33.setImageResource(R.drawable.ic_33);
                }
            }
        });
    }


    @OnClick({R.id.login_back, R.id.login_forget_psd, R.id.image_22, R.id.image_33, R.id.alter_name, R.id.alter_psd, R.id.login_register, R.id.login_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                break;
            case R.id.login_forget_psd:
                break;
            case R.id.image_22:
                break;
            case R.id.image_33:
                break;
            case R.id.alter_name:
                break;
            case R.id.alter_psd:
                break;
            case R.id.login_register:
                break;
            case R.id.login_commit:
                break;
        }
    }
}
