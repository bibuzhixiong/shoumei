package com.wanhan.shouyu.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanhan.shouyu.AppConstant;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.bean.json.RecommendInformationBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */

public class RecommendInformationAdapter1 extends BaseQuickAdapter<RecommendInformationBean ,BaseViewHolder> {
    public RecommendInformationAdapter1(@Nullable List<RecommendInformationBean> data) {
        super(R.layout.item_recommend_information,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, RecommendInformationBean item) {
        Log.e("GGG","jinlail");
        helper.setText(R.id.tv_title,item.getTitle());
        ImageView img=helper.getView(R.id.img);
        Glide.with(mContext).load(AppConstant.BASE_URL+item.getCoverPic()).into(img);

    }
}
