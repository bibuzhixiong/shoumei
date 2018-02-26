package com.wanhan.shouyu.ui.me.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.adapter.RecommendInformationAdapter1;
import com.wanhan.shouyu.base.BaseFragment;
import com.wanhan.shouyu.base.rx.RxBus;
import com.wanhan.shouyu.bean.json.RecommendInformationBean;
import com.wanhan.shouyu.event.NickNameEvent;
import com.wanhan.shouyu.event.UserHeadEvent;
import com.wanhan.shouyu.ui.me.activity.EditPersonalActivity;
import com.wanhan.shouyu.ui.me.activity.InviteFriendsActivity;
import com.wanhan.shouyu.ui.me.activity.RecommendActivity;
import com.wanhan.shouyu.ui.me.activity.SettingActivity;
import com.wanhan.shouyu.ui.me.activity.SuggestionsActivity;
import com.wanhan.shouyu.ui.me.contract.MeContract;
import com.wanhan.shouyu.ui.me.presenter.MePresenter;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
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
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private Subscription subscription;

    RecommendInformationAdapter1 adapter;
    List<RecommendInformationBean> data1=new ArrayList<>();

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

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
         /*   RequestOptions options = new RequestOptions();
            options.centerCrop()
                    .placeholder(R.drawable.head_man)
                    .error(R.drawable.head_man)
                    .fallback(R.drawable.head_man)
                    .bitmapTransform(new CircleCrop());*/

//            Glide.with(getActivity()).load(headpath).placeholder();
            Glide.with(getActivity()).load(headpath).apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(R.drawable.head_man) .error(R.drawable.head_man)
                    .fallback(R.drawable.head_man)).into(imgHead);
        }
        event();
        data1.add(new RecommendInformationBean());
//        adapter=new RecommendInformationAdapter(data1);
        adapter=new RecommendInformationAdapter1(data1);
        //设置横向布局
        RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
//
        recyclerView.setLayoutManager(linearLayoutManager);

//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                Bundle bundle=new Bundle();
                Log.e("GGG","id:"+adapter.getData().get(position).getInformationId());
                bundle.putString("id",adapter.getData().get(position).getInformationId());
                bundle.putString("title",adapter.getData().get(position).getTitle());
                bundle.putString("type","tuijian");
                startActivity(RecommendActivity.class,bundle);
            }
        });
//        recyclerView.addItemDecoration(new RecyclerViewDriverLine(mContext, LinearLayoutManager.VERTICAL));
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
//                          Log.e("TTT",userHeadEvent.getAvatar());

                          Glide.with(getActivity()).load(userHeadEvent.getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(R.drawable.head_man) .error(R.drawable.head_man)
                                  .fallback(R.drawable.head_man)).into(imgHead);
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
            case R.id.ll_help:
                Bundle bundle=new Bundle();
//                Log.e("GGG","id:"+adapter.getData().get(position).getInformationId());
                bundle.putString("id",SharedPreferencesUtil.getValue(getActivity(),"USERID","")+"");
                bundle.putString("title","使用帮助");
                bundle.putString("type","help");
                startActivity(RecommendActivity.class,bundle);
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
    public void recommentInformationdSuccess(List<RecommendInformationBean> info) {
        Log.e("GGG",info.toString());
//        adapter=new RecommendInformationAdapter1(info);
        adapter.setNewData(info);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(String msg) {

    }


}
