package com.wanhan.shouyu.ui.tool.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kitnew.ble.QNData;
import com.kitnew.ble.QNItemData;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.adapter.HistoryAdapter;
import com.wanhan.shouyu.adapter.RecommendInformationAdapter1;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.HistoryRecordBean;
import com.wanhan.shouyu.dialog.DialogInterface;
import com.wanhan.shouyu.dialog.NormalAlertDialog;
import com.wanhan.shouyu.ui.me.activity.SettingActivity;
import com.wanhan.shouyu.ui.tool.contract.HistoryContract;
import com.wanhan.shouyu.ui.tool.presenter.HistoryPresenter;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;
import com.wanhan.shouyu.widget.CustomLoadMoreView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/9.
 */

public class HistoryActivity extends BaseActivity<HistoryPresenter> implements HistoryContract.View,BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tv_manage)
    TextView tvManage;

    HistoryAdapter adapter;
    List<HistoryRecordBean> data=new ArrayList<>();
    private boolean isDelete=false;

    private int position1;
    private int page=1;  //页数
    private int num;
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
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnLoadMoreListener(this, recyclerView);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                QNData qnData=new QNData();
                qnData.addItemData(new QNItemData(2,adapter.getData().get(position).getWeight()));
                qnData.addItemData(new QNItemData(3,adapter.getData().get(position).getBMI()));
                qnData.addItemData(new QNItemData(4,adapter.getData().get(position).getBodyFatRate()));
                qnData.addItemData(new QNItemData(7,adapter.getData().get(position).getMoisture()));
                qnData.addItemData(new QNItemData(12,adapter.getData().get(position).getMetabolism()));
                qnData.addItemData(new QNItemData(5,adapter.getData().get(position).getVisceralFat()));
                qnData.addItemData(new QNItemData(9,adapter.getData().get(position).getSkeletalMuscle()));
                qnData.addItemData(new QNItemData(10,adapter.getData().get(position).getBone()));
                qnData.addItemData(new QNItemData(11,adapter.getData().get(position).getProtein()));


            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter1, View view, final int position) {
                switch (view.getId()){
                    case R.id.img_delete:
                        if(isDelete==false){
                            return;
                        }
                        new NormalAlertDialog.Builder(HistoryActivity.this)
                                .setBoolTitle(true)
                               .setTitleText("提示")
                                .setContentText("健康记录删除后不可恢复，是否确认删除该条健康记录?")
                                .setContentTextColor(R.color.blue)
                                .setLeftText("取消")
                                .setLeftTextColor(R.color.blue)
                                .setRightText("删除")
                                .setRightTextColor(R.color.blue)
                                .setWidth(0.75f)
                                .setHeight(0.33f)
                                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                                    @Override
                                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                                        dialog.dismiss();
                                    }
                                    @Override
                                    public void clickRightButton(NormalAlertDialog dialog, View view) {

                                        Map<String,String> map=new HashMap<>();
                                        map.put("historicalRecordId",adapter.getData().get(position).getHistoricalRecordId());
                                        position1=position;
                                        mPresenter.deleteHistoryRecord(MapUtil.getMap(map));
                                        dialog.dismiss();

                                    }
                                }).build().show();

                        break;
                }

            }
        });
        loadData(false);


    }

    @Override
    public void findHistoryRecordSuccess(List<HistoryRecordBean> info) {
        Log.e("TTT", "健康数据：" + info.toString());
        num=info.size();
        Log.e("TTT", "健康数据进来"+num);
//        adapter.setNewData(info);
        if(info.size()==0){
            Log.e("TTT", "健康数据进来了1");
            adapter.loadMoreEnd();
            adapter.loadMoreComplete();
            return;
        }

        adapter.addData(info);
        adapter.notifyDataSetChanged();
        if(num<10){
            adapter.loadMoreComplete();
            adapter.loadMoreEnd(false);
        }

    }

    @Override
    public void deleteHistoryRecordSuccess(CodeBean info) {
        if(info.getCode().equals("0")){
            adapter.remove(position1);
            ToastUtil.showShortToast("删除成功");
        }else{
            ToastUtil.showShortToast("删除失败");
        }
    }

    @Override
    public void loadFail(String msg) {

    }
    @OnClick({R.id.tv_manage})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_manage:
                adapter.change();
                if(isDelete){
                    tvManage.setText("管理");
                    tvManage.setBackgroundResource(R.drawable.shape_redbtn_selector5);
                    isDelete=false;
                }else{
                    tvManage.setText("完成");
                    tvManage.setBackgroundResource(R.drawable.shape_redbtn_selector6);
                    isDelete=true;
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if(num<10){
            Log.e("TTT", "健康数据进来了2");
            adapter.loadMoreEnd(false);
            adapter.loadMoreComplete();
            return;
        }
        page++;
        loadData(false);

    }
    //swifload判断是否是下拉加载
    private void loadData(boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtil.getValue(HistoryActivity.this, "USERID", "") + "");
        map.put("page", page+"");
        map.put("limit","10");
        mPresenter.findHistoryRecord(MapUtil.getMap(map));
    }


    }
