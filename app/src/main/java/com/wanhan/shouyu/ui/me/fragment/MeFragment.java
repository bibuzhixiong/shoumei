package com.wanhan.shouyu.ui.me.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseFragment;
import com.wanhan.shouyu.ui.me.activity.InviteFriendsActivity;
import com.wanhan.shouyu.ui.me.activity.SettingActivity;
import com.wanhan.shouyu.ui.me.activity.SuggestionsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by Administrator on 2018/1/27.
 */

public class MeFragment extends BaseFragment {
    @Bind(R.id.ll_edit_personal)
    LinearLayout llEditPersonal;
    @Bind(R.id.ll_collections)
    LinearLayout llCollections;
    @Bind(R.id.ll_invite_friends)
    LinearLayout llInviteFriends;
    @Bind(R.id.ll_message)
    LinearLayout llMessage;
    @Bind(R.id.ll_suggestion)
    LinearLayout llSuggestion;
    @Bind(R.id.ll_secret)
    LinearLayout llSecret;
    @Bind(R.id.ll_help)
    LinearLayout llHelp;
    @Bind(R.id.ll_setting)
    LinearLayout llSetting;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            ;
            StatusBarCompat.translucentStatusBar(getActivity(), true);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.ll_collections,R.id.ll_message,R.id.ll_secret,R.id.ll_setting,R.id.ll_suggestion,R.id.ll_invite_friends,R.id.ll_help})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.ll_suggestion:
                startActivity(SuggestionsActivity.class);
                break;
            case R.id.ll_invite_friends:
                startActivity(InviteFriendsActivity.class);
                break;
        }
    }
}
