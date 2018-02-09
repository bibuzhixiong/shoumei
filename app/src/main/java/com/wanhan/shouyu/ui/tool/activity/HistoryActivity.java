package com.wanhan.shouyu.ui.tool.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.adapter.HistoryAdapter;
import com.wanhan.shouyu.adapter.RecommendInformationAdapter1;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;
import com.wanhan.shouyu.bean.json.HistoryRecordBean;
import com.wanhan.shouyu.ui.me.activity.SettingActivity;
import com.wanhan.shouyu.ui.tool.contract.HistoryContract;
import com.wanhan.shouyu.ui.tool.presenter.HistoryPresenter;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/9.
 */

public class HistoryActivity extends BaseActivity<HistoryPresenter> implements HistoryContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    HistoryAdapter adapter;
    List<HistoryRecordBean> data=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(HistoryActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        adapter=new HistoryAdapter(data);
        //设置横向布局
//        RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
//
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.setAdapter(adapter);

        Map<String, String> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtil.getValue(HistoryActivity.this, "USERID", "") + "");
        map.put("page", "1");
        map.put("limit", "1100");
        mPresenter.findHistoryRecord(MapUtil.getMap(map));
    }

    @Override
    public void findHistoryRecordSuccess(List<HistoryRecordBean> info) {
        Log.e("TTT", "健康数据：" + info.toString());
        adapter.setNewData(info);
    }

    @Override
    public void loadFail(String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
