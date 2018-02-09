package com.wanhan.shouyu.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanhan.shouyu.AppConstant;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.bean.json.HistoryRecordBean;
import com.wanhan.shouyu.bean.json.RecommendInformationBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */

public class HistoryAdapter extends BaseQuickAdapter<HistoryRecordBean ,BaseViewHolder> {
    public HistoryAdapter(@Nullable List<HistoryRecordBean> data) {
        super(R.layout.item_history,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, HistoryRecordBean item) {
//        Log.e("GGG","jinlail");
        helper.setText(R.id.tv_time,item.getRecordTime().substring(0,item.getRecordTime().length()-3));
        helper.setText(R.id.tv_weight,item.getWeight());
        helper.setText(R.id.tv_tizhilv,item.getBodyFatRate());
        helper.setText(R.id.tv_fat,item.getFat());

    }
}
