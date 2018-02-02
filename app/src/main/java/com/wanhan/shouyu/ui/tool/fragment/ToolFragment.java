package com.wanhan.shouyu.ui.tool.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseFragment;
import com.wanhan.shouyu.ui.tool.activity.MeasureResultActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by Administrator on 2018/1/27.
 */

public class ToolFragment extends BaseFragment {


    @Bind(R.id.rl1)
    RelativeLayout rl1;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            StatusBarCompat.translucentStatusBar(getActivity(), true);
//            StatusBarCompat.setStatusBarColor(activity, Color.WHITE,125);

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tool;
    }

    @Override
    protected void initView() {
        StatusBarCompat.translucentStatusBar(getActivity(), true);

    }
    @OnClick({R.id.rl1})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.rl1:
                startActivity(MeasureResultActivity.class);
                break;
        }
    }
}
