package com.wanhan.shouyu.ui.me.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseFragment;
import com.wanhan.shouyu.base.rx.RxBus;
import com.wanhan.shouyu.event.NickNameEvent;
import com.wanhan.shouyu.event.UserHeadEvent;
import com.wanhan.shouyu.ui.me.activity.EditPersonalActivity;
import com.wanhan.shouyu.ui.me.activity.InviteFriendsActivity;
import com.wanhan.shouyu.ui.me.activity.SettingActivity;
import com.wanhan.shouyu.ui.me.activity.SuggestionsActivity;
import com.wanhan.shouyu.ui.me.contract.MeContract;
import com.wanhan.shouyu.ui.me.presenter.MePresenter;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2018/1/27.
 */

public class MeFragment extends BaseFragment<MePresenter> implements MeContract.View {
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
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_id)
    TextView tvId;
    private Subscription subscription;
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
        String id= SharedPreferencesUtil.getValue(getActivity(),"USERID","")+"";
        String nickname= SharedPreferencesUtil.getValue(getActivity(),"NICKNAME","")+"";
        tvNickname.setText(nickname);
        tvId.setText("瘦鱼ID："+id);
        String headpath= SharedPreferencesUtil.getValue(getActivity(),"HEADPATH","")+"";
        if(!headpath.equals("")){
            Glide.with(getActivity()).load(headpath).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imgHead);
        }
        event();
        Map<String,String> map=new HashMap<>();
        map.put("isRecomm","0");
        map.put("page","1");
        map.put("limit","10");
        mPresenter.recommentInformation(MapUtil.getMap(map));
    }
    private void event(){
        subscription= RxBus.$().toObservable(Object.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                      if(event instanceof UserHeadEvent){
                            UserHeadEvent userHeadEvent= (UserHeadEvent) event;
                          Log.e("TTT",userHeadEvent.getAvatar());
                          Glide.with(getActivity()).load(userHeadEvent.getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imgHead);
                        }else if(event instanceof NickNameEvent){
                          NickNameEvent userHeadEvent= (NickNameEvent) event;
                            tvNickname.setText(userHeadEvent.getNickname());
                        }
                    }
                });
    }

    @OnClick({R.id.ll_collections,R.id.ll_message,R.id.ll_secret,R.id.ll_setting,R.id.ll_suggestion,R.id.ll_invite_friends,R.id.ll_help,R.id.ll_edit_personal})
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
            case R.id.ll_edit_personal:
                startActivity(EditPersonalActivity.class);
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null&&!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void recommentInformationdSuccess(String info) {

    }

    @Override
    public void loadFail(String msg) {

    }
}
