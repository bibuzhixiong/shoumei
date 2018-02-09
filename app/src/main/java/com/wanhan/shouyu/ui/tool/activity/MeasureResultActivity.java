package com.wanhan.shouyu.ui.tool.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.kitnew.ble.QNData;
import com.kitnew.ble.QNItemData;
import com.wanhan.shouyu.AppConstant;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.adapter.MeasureResultAdapter;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.ui.IndicatorAdapter;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;

/**
 * Created by Administrator on 2018/2/2.
 */

public class MeasureResultActivity extends BaseActivity {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.img_back)
    ImageView img_back;
    private IndicatorAdapter indicatorAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_measure_result;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
        String nickname= SharedPreferencesUtil.getValue(MeasureResultActivity.this,"NICKNAME","")+"";
        tvNickname.setText(nickname);
        String headpath= SharedPreferencesUtil.getValue(MeasureResultActivity.this,"HEADPATH","")+"";
        if(!headpath.equals("")){
            Glide.with(MeasureResultActivity.this).load(headpath).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imgHead);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        indicatorAdapter = new IndicatorAdapter(this);
        recyclerView.setAdapter(indicatorAdapter);



        Bundle bundle=getIntent().getExtras();
        String type="";
        if(bundle!=null){
             type=bundle.getString("type");
        }
        if(type.equals("new")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            tvTime.setText(simpleDateFormat.format(date));
            indicatorAdapter.setQnData(AppConstant.qnData);
            Log.e("TTT","HHH:"+AppConstant.qnData.toString());
        }else{

            if(!SharedPreferencesUtil.getValue(MeasureResultActivity.this,"LAST_RESULT","").equals("")){
                String time=SharedPreferencesUtil.getValue(MeasureResultActivity.this,"LAST_RESULT_TIME","")+"";

                String  s=(SharedPreferencesUtil.getValue(MeasureResultActivity.this,"LAST_RESULT",""))+"";
                QNData qnData1=new QNData();
                String str[]=s.split(",");
                for(int i=0;i<str.length;i++){
                    if(i==0){
                        QNItemData qnItemData=new QNItemData(2,Float.parseFloat(str[i]));
                        qnData1.addItemData(qnItemData);
                    }else if(i==1){
                        QNItemData qnItemData=new QNItemData(3,Float.parseFloat(str[i]));
                        qnData1.addItemData(qnItemData);
                    }else if(i==2){
                        QNItemData qnItemData=new QNItemData(4,Float.parseFloat(str[i]));
                        qnData1.addItemData(qnItemData);
                    }else if(i==3){
                        QNItemData qnItemData=new QNItemData(7,Float.parseFloat(str[i]));
                        qnData1.addItemData(qnItemData);
                    }else if(i==4){
                        QNItemData qnItemData=new QNItemData(12,Float.parseFloat(str[i]));
                        qnData1.addItemData(qnItemData);
                    }else if(i==5){
                        QNItemData qnItemData=new QNItemData(5,Float.parseFloat(str[i]));
                        qnData1.addItemData(qnItemData);
                    }else if(i==6){
                        QNItemData qnItemData=new QNItemData(6,Float.parseFloat(str[i]));
                        qnData1.addItemData(qnItemData);
                    }else if(i==7){
                        QNItemData qnItemData=new QNItemData(9,Float.parseFloat(str[i]));
                        qnData1.addItemData(qnItemData);
                    }else if(i==8){
                        QNItemData qnItemData=new QNItemData(10,Float.parseFloat(str[i]));
                        qnData1.addItemData(qnItemData);
                    }else if(i==9){
                        QNItemData qnItemData=new QNItemData(11,Float.parseFloat(str[i]));
                        qnData1.addItemData(qnItemData);
                    }
                }
                indicatorAdapter.setQnData(qnData1);
                tvTime.setText(time);
            }
        }


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(MeasureResultActivity.this);
            }
        });
    }
}
