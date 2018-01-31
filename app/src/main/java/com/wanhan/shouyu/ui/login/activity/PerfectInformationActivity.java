package com.wanhan.shouyu.ui.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.ui.MainActivity;
import com.wanhan.shouyu.ui.login.contract.PerfectInformationContract;
import com.wanhan.shouyu.ui.login.presenter.PerfectInformationPresenter;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.RescourseUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/30.
 */

public class PerfectInformationActivity extends BaseActivity<PerfectInformationPresenter> implements PerfectInformationContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.img_woman)
    ImageView imgWoman;
    @Bind(R.id.img_select_woman)
    ImageView imgSelectWoman;
    @Bind(R.id.img_man)
    ImageView imgMan;
    @Bind(R.id.img_select_man)
    ImageView imgSelectMan;
    @Bind(R.id.tv_woman)
    TextView tvWoman;
    @Bind(R.id.tv_man)
    TextView tvMan;
    @Bind(R.id.tv_birthday)
    TextView tvBirthday;
    @Bind(R.id.ll_addres)
    LinearLayout llAddres;
    @Bind(R.id.et_height)
    EditText etHeight;
    @Bind(R.id.et_weight)
    EditText etWeight;
    @Bind(R.id.et_nickname)
    EditText etNickname;
    @Bind(R.id.bt_finish)
    Button btFinish;

    private boolean iswoman=true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_perfect_information;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initView() {

    }
    @OnClick({R.id.ll_addres,R.id.bt_finish,R.id.img_man,R.id.img_woman})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_addres:
                TimePickerView timePickerView=new TimePickerView(PerfectInformationActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
                Calendar calendar = Calendar.getInstance();
                timePickerView.setRange(1900,calendar.get(Calendar.YEAR)-10);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if(tvBirthday.getText().toString().trim().equals("请选择")){
                        timePickerView.setTime(sdf.parse("1987-01-01"));
                    }else{
                        timePickerView.setTime(sdf.parse(tvBirthday.getText().toString().trim()));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        tvBirthday.setText(sdf.format(date));
                    }

                });
                timePickerView.show();
                break;
            case R.id.img_woman:
                if(iswoman==true){
                    return;
                }
                iswoman=true;
                imgWoman.setImageResource(R.drawable.head_woman);
                imgSelectWoman.setVisibility(View.VISIBLE);
                imgMan.setImageResource(R.drawable.head_man_gray);
                imgSelectMan.setVisibility(View.GONE);
                tvMan.setTextColor(RescourseUtil.getColor(R.color.gray));
                tvWoman.setTextColor(RescourseUtil.getColor(R.color.green));
                break;
            case R.id.img_man:
                if(iswoman==false){
                    return;
                }
                iswoman=false;
                imgWoman.setImageResource(R.drawable.hean_woman_gray);
                imgSelectWoman.setVisibility(View.GONE);
                imgMan.setImageResource(R.drawable.head_man);
                imgSelectMan.setVisibility(View.VISIBLE);
                tvMan.setTextColor(RescourseUtil.getColor(R.color.green));
                tvWoman.setTextColor(RescourseUtil.getColor(R.color.gray));

                break;
            case R.id.bt_finish:
                String height=etHeight.getText().toString().trim();
                if(height.equals("")||height==null){
                    ToastUtil.showShortToast("请填写你的身高");
                    return;
                }
                String birthday=tvBirthday.getText().toString().trim();
                if(height.equals("请选择")){
                    ToastUtil.showShortToast("请选择你的生日");
                    return;
                }
                String nickname=tvBirthday.getText().toString().trim();
                String manOrWoman="2";
                if(iswoman==false){
                    manOrWoman="1";
                }
                Map map=new HashMap();
                map.put("userId", SharedPreferencesUtil.getValue(PerfectInformationActivity.this,"USERID",""));
                map.put("birthday",birthday);
                map.put("height",height);
                map.put("niceName",nickname);
                map.put("sex",manOrWoman);
                mPresenter.updateUser(MapUtil.getMap(map));
                break;
        }


    }

    @Override
    public void updateUserSuccess(CodeBean info) {
        String manOrWoman="女";
        if(iswoman==false){
            manOrWoman="男";
        }
        SharedPreferencesUtil.putValue(PerfectInformationActivity.this,"BIRTHDAY",tvBirthday.getText().toString().trim());
        SharedPreferencesUtil.putValue(PerfectInformationActivity.this,"SEX",manOrWoman);
        SharedPreferencesUtil.putValue(PerfectInformationActivity.this,"HEIGHT",etHeight.getText().toString().trim());
        SharedPreferencesUtil.putValue(PerfectInformationActivity.this,"niceNameniceName",etNickname.getText().toString().trim());
        startActivity(MainActivity.class);
    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.showShortToast(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
