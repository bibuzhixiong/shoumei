package com.wanhan.shouyu.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kitnew.ble.QNData;
import com.kitnew.ble.QNItemData;
import com.kitnew.ble.utils.QNLog;
import com.wanhan.shouyu.AppConstant;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.bean.json.RecommendInformationBean;
import com.wanhan.shouyu.ui.IndicatorAdapter;
import com.wanhan.shouyu.utils.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */

public class MeasureResultAdapter extends RecyclerView.Adapter<MeasureResultAdapter.MeasureResultViewHolder> {

    QNData qnData;
    LayoutInflater inflater;

    public MeasureResultAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setQnData(QNData qnData) {
        this.qnData = qnData;
        notifyDataSetChanged();
    }

    @Override
    public MeasureResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_measrue_result, parent, false);
        return new MeasureResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MeasureResultAdapter.MeasureResultViewHolder holder, int position) {
        holder.init(qnData.getAll().get(position));

    }

    @Override
    public int getItemCount() {
        return qnData == null ? 0 : qnData.size();
    }

    class MeasureResultViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView valueTv;
        LinearLayout ll;

        public MeasureResultViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.nameTv);
            valueTv = (TextView) itemView.findViewById(R.id.valueTv);
            ll=(LinearLayout) itemView.findViewById(R.id.ll);

        }

        void init(QNItemData itemData) {
            nameTv.setText(itemData.name);
            QNLog.log("he", itemData.name + "   " + itemData.value);
            if (itemData.type == QNData.TYPE_BODY_SHAPE) {
                valueTv.setText(itemData.valueStr);
            } else {
                valueTv.setText(itemData.value + "");
            }

        }
    }
    }