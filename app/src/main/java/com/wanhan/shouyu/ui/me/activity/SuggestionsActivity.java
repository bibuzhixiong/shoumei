package com.wanhan.shouyu.ui.me.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.dialog.DialogInterface;
import com.wanhan.shouyu.dialog.NormalAlertDialog;
import com.wanhan.shouyu.ui.me.contract.SuggestionsContract;
import com.wanhan.shouyu.ui.me.presenter.SuggestionsPresenter;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/1.
 */

public class SuggestionsActivity extends BaseActivity<SuggestionsPresenter> implements SuggestionsContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.bt_send)
    Button btSend;
    @Bind(R.id.tv_num)
    TextView tvNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_suggestions;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(SuggestionsActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
etContent.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        tvNum.setText("("+etContent.getText().toString().length()+""+"/500字)");
    }
});
    }


    @OnClick({R.id.bt_send})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch ((v.getId())) {
            case R.id.bt_send:
                String content=etContent.getText().toString().trim();
                if(content.length()<5){
                    ToastUtil.showShortToast("至少5个字");
                    return;
                }
                Map map=new HashMap<>();
                map.put("userId", SharedPreferencesUtil.getValue(SuggestionsActivity.this,"USERID",""));
                map.put("content",content);
                mPresenter.suggestive(MapUtil.getMap(map));

                break;
        }
    }

    @Override
    public void suggestiveSuccess(CodeBean info) {
        etContent.setText("");
        new NormalAlertDialog.Builder(this)
                .setBoolTitle(false)
                .setContentText("感谢您的反馈！")
                .setContentTextColor(R.color.blue)
                .setSingleModel(true)
                .setSingleText("确认")
                .setWidth(0.75f)
                .setHeight(0.33f)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        finish_Activity(SuggestionsActivity.this);
                    }
                }).build().show();
    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.showShortToast(msg);
    }


}
