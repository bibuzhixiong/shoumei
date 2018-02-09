package com.wanhan.shouyu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kitnew.ble.QNData;
import com.kitnew.ble.QNItemData;
import com.kitnew.ble.utils.QNLog;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.bean.json.HistoryRecordBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */

public class MeasureReasultAdapter1 extends BaseQuickAdapter<QNItemData,BaseViewHolder> {
    public MeasureReasultAdapter1(@Nullable List<QNItemData> data) {
        super(R.layout.item_measrue_result,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, QNItemData item) {
//        Log.e("GGG","jinlail");
//        nameTv.setText(itemData.name);
        QNLog.log("he", item.name + "   " + item.value);
        if (item.type == QNData.TYPE_BODY_SHAPE) {
            helper.setText(R.id.valueTv,item.valueStr);
        } else {
            helper.setText(R.id.valueTv,item.value+"");
        }
        helper.setText(R.id.nameTv,item.name);


    }
}
