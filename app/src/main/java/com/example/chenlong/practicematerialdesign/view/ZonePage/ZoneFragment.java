package com.example.chenlong.practicematerialdesign.view.ZonePage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenlong.practicematerialdesign.R;

/**
 * Created by chenlong on 2016/12/11.
 */

public class ZoneFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = View.inflate(container.getContext(), R.layout.fragment_zone, null);
        return view;
    }
}
